#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PROJECT_PATH="$ROOT_DIR/iosApp/iosApp.xcodeproj"
SCHEME="iosApp"
APP_NAME="MoviesApp.app"
BUNDLE_ID="com.karvinok.moviesapp.MoviesApp"
DERIVED_DATA_PATH="$ROOT_DIR/build/xcode"

usage() {
  cat <<'EOF'
Usage: ./scripts/run-ios.sh [simulator-name-or-udid]

Builds the iOS app for the simulator, installs it on the selected simulator,
and launches the app.

If no simulator is passed, the script uses a booted simulator.
If none is booted, it boots the first available iPhone simulator.
EOF
}

require_command() {
  if ! command -v "$1" >/dev/null 2>&1; then
    echo "Required command not found: $1" >&2
    exit 1
  fi
}

trim() {
  local value="$1"
  value="${value#"${value%%[![:space:]]*}"}"
  value="${value%"${value##*[![:space:]]}"}"
  printf '%s\n' "$value"
}

find_booted_simulator() {
  xcrun simctl list devices available | awk -F '[()]' '/Booted/ { print $2; exit }'
}

find_simulator_by_name() {
  local lookup="$1"
  xcrun simctl list devices available | awk -F '[()]' -v name="$lookup" '
    {
      device_name = $1
      gsub(/^[[:space:]]+|[[:space:]]+$/, "", device_name)
      if (device_name == name) {
        print $2
        exit
      }
    }
  '
}

find_first_available_iphone() {
  xcrun simctl list devices available | awk -F '[()]' '
    /iPhone/ && /Shutdown/ {
      print $2
      exit
    }
  '
}

boot_simulator_if_needed() {
  local device_id="$1"
  open -a Simulator >/dev/null 2>&1 || true
  xcrun simctl boot "$device_id" >/dev/null 2>&1 || true
  xcrun simctl bootstatus "$device_id" -b
}

if [[ "${1:-}" == "--help" || "${1:-}" == "-h" ]]; then
  usage
  exit 0
fi

require_command xcodebuild
require_command xcrun
require_command open

TARGET="${1:-}"
DEVICE_ID=""

if [[ -n "$TARGET" ]]; then
  if [[ "$TARGET" =~ ^[0-9A-F-]{8,}$ ]]; then
    DEVICE_ID="$TARGET"
  else
    DEVICE_ID="$(find_simulator_by_name "$TARGET")"
  fi
else
  DEVICE_ID="$(find_booted_simulator)"
fi

if [[ -z "$DEVICE_ID" ]]; then
  DEVICE_ID="$(find_first_available_iphone)"
fi

if [[ -z "$DEVICE_ID" ]]; then
  echo "No available iPhone simulator found." >&2
  exit 1
fi

echo "Using iOS simulator: $DEVICE_ID"
boot_simulator_if_needed "$DEVICE_ID"

echo "Building iOS app for simulator..."
xcodebuild \
  -project "$PROJECT_PATH" \
  -scheme "$SCHEME" \
  -configuration Debug \
  -sdk iphonesimulator \
  -destination "id=$DEVICE_ID" \
  -derivedDataPath "$DERIVED_DATA_PATH" \
  build

APP_PATH="$DERIVED_DATA_PATH/Build/Products/Debug-iphonesimulator/$APP_NAME"
if [[ ! -d "$APP_PATH" ]]; then
  echo "Built app not found at $APP_PATH" >&2
  exit 1
fi

echo "Installing app on simulator..."
xcrun simctl install "$DEVICE_ID" "$APP_PATH"

echo "Launching iOS app..."
xcrun simctl launch "$DEVICE_ID" "$BUNDLE_ID"

echo "iOS app is installed and launched on simulator $DEVICE_ID."
