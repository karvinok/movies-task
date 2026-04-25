#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

usage() {
  cat <<'EOF'
Usage: ./scripts/run-mobile.sh [android|ios|all] [target]

Examples:
  ./scripts/run-mobile.sh android
  ./scripts/run-mobile.sh ios
  ./scripts/run-mobile.sh ios "iPhone 16"
  ./scripts/run-mobile.sh all

For `android`, the optional target is a device serial.
For `ios`, the optional target is a simulator name or UDID.
EOF
}

PLATFORM="${1:-all}"
TARGET="${2:-}"

case "$PLATFORM" in
  android)
    exec "$ROOT_DIR/scripts/run-android.sh" ${TARGET:+"$TARGET"}
    ;;
  ios)
    exec "$ROOT_DIR/scripts/run-ios.sh" ${TARGET:+"$TARGET"}
    ;;
  all)
    "$ROOT_DIR/scripts/run-android.sh"
    "$ROOT_DIR/scripts/run-ios.sh" ${TARGET:+"$TARGET"}
    ;;
  -h|--help)
    usage
    ;;
  *)
    echo "Unknown platform: $PLATFORM" >&2
    usage
    exit 1
    ;;
esac
