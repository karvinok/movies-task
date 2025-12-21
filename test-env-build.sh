#!/bin/bash

# Test building with environment variables (without local.properties)

# Backup local.properties if it exists
if [ -f "local.properties" ]; then
    mv local.properties local.properties.bak
    echo "✓ Backed up local.properties"
fi

# Export environment variables
export BASE_URL="https://api.themoviedb.org/3/"
export TMDB_API_KEY="your_api_key_here"
export TMDB_TOKEN="your_token_here"

echo "✓ Environment variables set"
echo "Building APK with environment variables..."

# Build the APK
./gradlew assembleRelease --no-daemon

# Restore local.properties
if [ -f "local.properties.bak" ]; then
    mv local.properties.bak local.properties
    echo "✓ Restored local.properties"
fi

echo ""
echo "Build complete! Check app/build/outputs/apk/release/"
