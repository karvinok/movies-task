#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
APP_ID="com.karvinok.moviesapp"
MAIN_ACTIVITY="com.karvinok.moviesapp.MainActivity"

usage() {
  cat <<'EOF'
Usage: ./scripts/run-android.sh [device-serial]

Builds the debug APK, installs it on the selected Android device/emulator,
and launches the app.

If no device serial is passed, the script uses a connected device.
If no device is connected, it tries to boot the first available AVD.
EOF
}

read_local_property() {
  local key="$1"
  local file="$ROOT_DIR/local.properties"

  [[ -f "$file" ]] || return 1
  awk -F'=' -v lookup="$key" '$1 == lookup {print substr($0, index($0, "=") + 1)}' "$file"
}

resolve_android_sdk_root() {
  if [[ -n "${ANDROID_SDK_ROOT:-}" ]]; then
    printf '%s\n' "$ANDROID_SDK_ROOT"
    return 0
  fi

  if [[ -n "${ANDROID_HOME:-}" ]]; then
    printf '%s\n' "$ANDROID_HOME"
    return 0
  fi

  local sdk_dir
  sdk_dir="$(read_local_property "sdk.dir" 2>/dev/null || true)"
  if [[ -n "$sdk_dir" ]]; then
    sdk_dir="${sdk_dir//\\:/:}"
    sdk_dir="${sdk_dir//\\//}"
    printf '%s\n' "$sdk_dir"
    return 0
  fi

  return 1
}

resolve_sdk_tool() {
  local tool_name="$1"
  local sdk_root="$2"
  local tool_path=""

  case "$tool_name" in
    adb)
      tool_path="$sdk_root/platform-tools/adb"
      ;;
    emulator)
      tool_path="$sdk_root/emulator/emulator"
      ;;
  esac

  if [[ -n "$tool_path" && -x "$tool_path" ]]; then
    printf '%s\n' "$tool_path"
    return 0
  fi

  command -v "$tool_name"
}

find_connected_device() {
  local adb_bin="$1"
  "$adb_bin" devices | awk 'NR > 1 && $2 == "device" { print $1; exit }'
}

wait_for_boot_complete() {
  local adb_bin="$1"
  local device="$2"

  "$adb_bin" -s "$device" wait-for-device >/dev/null
  until [[ "$("$adb_bin" -s "$device" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" == "1" ]]; do
    sleep 2
  done
}

boot_first_available_emulator() {
  local emulator_bin="$1"
  local adb_bin="$2"

  local avd_name
  avd_name="$("$emulator_bin" -list-avds | head -n 1)"

  if [[ -z "$avd_name" ]]; then
    echo "No Android device connected and no AVD found." >&2
    echo "Create/start an emulator in Android Studio, then rerun the script." >&2
    exit 1
  fi

  echo "Starting Android emulator: $avd_name"
  nohup "$emulator_bin" -avd "$avd_name" >/tmp/movies-android-emulator.log 2>&1 &

  local device=""
  for _ in {1..90}; do
    device="$(find_connected_device "$adb_bin" || true)"
    if [[ -n "$device" ]]; then
      wait_for_boot_complete "$adb_bin" "$device"
      printf '%s\n' "$device"
      return 0
    fi
    sleep 2
  done

  echo "Android emulator did not become ready in time." >&2
  exit 1
}

if [[ "${1:-}" == "--help" || "${1:-}" == "-h" ]]; then
  usage
  exit 0
fi

SDK_ROOT="$(resolve_android_sdk_root)"
ADB_BIN="$(resolve_sdk_tool adb "$SDK_ROOT")"
EMULATOR_BIN="$(resolve_sdk_tool emulator "$SDK_ROOT")"
DEVICE_SERIAL="${1:-}"

if [[ -z "$DEVICE_SERIAL" ]]; then
  DEVICE_SERIAL="$(find_connected_device "$ADB_BIN" || true)"
fi

if [[ -z "$DEVICE_SERIAL" ]]; then
  DEVICE_SERIAL="$(boot_first_available_emulator "$EMULATOR_BIN" "$ADB_BIN")"
fi

echo "Building Android debug APK..."
"$ROOT_DIR/gradlew" :app:assembleDebug

APK_PATH="$ROOT_DIR/app/build/outputs/apk/debug/app-debug.apk"
if [[ ! -f "$APK_PATH" ]]; then
  echo "APK not found at $APK_PATH" >&2
  exit 1
fi

echo "Installing APK on $DEVICE_SERIAL..."
"$ADB_BIN" -s "$DEVICE_SERIAL" install -r "$APK_PATH"

echo "Launching Android app..."
"$ADB_BIN" -s "$DEVICE_SERIAL" shell am start -n "$APP_ID/$MAIN_ACTIVITY" >/dev/null

echo "Android app is installed and launched on $DEVICE_SERIAL."
