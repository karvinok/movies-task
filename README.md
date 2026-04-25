# Template App

TMDB client built with Compose Multiplatform.

## Quick Start

```bash
git clone <repository-url>
cd movies-task
```

1. Create a TMDB account at [themoviedb.org](https://www.themoviedb.org).
2. Generate an API key and token.
3. Fill in the credentials in `local.properties` and `iosApp/iosApp/Info.plist`.

Example `local.properties`:

```properties
BASE_URL=https://api.themoviedb.org/3/
TMDB_API_KEY=your_api_key_here
TMDB_TOKEN=your_token_here
```

Example `iosApp/iosApp/Info.plist` values:

```xml
<key>BASE_URL</key>
<string>https://api.themoviedb.org/3/</string>
<key>TMDB_API_KEY</key>
<string>your_api_key_here</string>
<key>TMDB_TOKEN</key>
<string>your_token_here</string>
```

## Requirements

- Android Studio `2025.1.3`
- Xcode with iOS Simulator support
- JDK `21`
- Android SDK and at least one emulator or connected device

Android Studio `2025.1.3` is required because the project uses recent Compose Multiplatform, Kotlin, and AGP versions.

## Run The App

### One-command scripts

```bash
# Shared entry point
./scripts/run-mobile.sh android
./scripts/run-mobile.sh ios
./scripts/run-mobile.sh all
```

You can also pass an explicit target:

```bash
# Android device serial
./scripts/run-android.sh R5CW142SSLF

# iOS simulator name or UDID
./scripts/run-ios.sh "iPhone 17 Pro"
./scripts/run-mobile.sh ios "iPhone 17 Pro"
```

### Manual commands

#### Android

```bash
./gradlew :app:assembleDebug
./gradlew :app:installDebug
./gradlew :app:assembleRelease
./gradlew clean build
```

#### iOS

- Open `iosApp` in Xcode and run the `iosApp` scheme.
- Or use the IDE run configuration named `iosApp`.

## Project Structure

```text
app/src/
├── commonMain/kotlin/          # Shared Kotlin code
│   ├── core/                   # Base, DI, design, db, navigation, network
│   ├── data/                   # Repository and DTO implementations
│   ├── domain/                 # Use cases and domain models
│   └── presentation/           # Feature UI, state, and view models
├── androidMain/kotlin/         # Android-specific code
└── iosMain/kotlin/             # iOS-specific code
```

## Architecture

The app follows Clean Architecture with a shared KMP codebase.

- Presentation: Compose screens, state, intents, events, view models
- Domain: use cases and domain models
- Data: repositories, API layer, persistence

Main patterns used:

- MVI-style state management
- Use-case driven domain layer

## Tech Stack

- Kotlin Multiplatform `2.2.10`
- Compose Multiplatform `1.8.2`
- Room + Paging
- Koin
- Ktorfit + Ktor
- Kotlinx Serialization

## License

This project was created for a coding assessment and as a template for testing new features and lib versions.
