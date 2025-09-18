# Movies App - TMDB Client

## Application is built in Compose Multiplatform.

## Setup Instructions

### 0. Use AndroidStudio 2025.1.3 
This step is required to use latest CMP libraries where kotlin 2.2.0 and AGP 8.13 needed to compile. 

### 1. Clone the Repository
```bash
git clone <repository-url>
cd Movies
```

### 2. Obtain TMDB API Key
1. Visit [TMDB Website](https://www.themoviedb.org)
2. Create an account and verify your email
3. Go to Settings → API → Create API Key (v3)
4. Copy your API key

### 3. Configure API Key
Change 3 parameters in `local.properties` and `iosApp/Info.plist` files:
```local.properties
BASE_URL=https://api.themoviedb.org/3/
TMDB_API_KEY=your_api_key_here
TMDB_TOKEN=your_token_here

```
```Info.plist
	<key>BASE_URL</key>
	<string>https://api.themoviedb.org/3/</string>
	<key>TMDB_API_KEY</key>
	<string>your_api_key_here</string>
    <key>TMDB_TOKEN</key>
    <string>your_token_here</string>
```

### 4. Build and Run

#### Android
```bash
# Build debug APK
./gradlew :app:assembleDebug

# Install and run on connected device/emulator
./gradlew :app:installDebug

# Build release APK
./gradlew :app:assembleRelease

# Clean build (if needed)
./gradlew clean build
```

#### iOS
Option 1: Open the `/iosApp` directory in Xcode and build/run from there
Option 2: use the IDE's run configuration `iosApp`

## Project Structure

```
app/src/
├── commonMain/kotlin/           # Shared Kotlin code
│   ├── core/
│   │   ├── base/               # Base classes (ViewModel, State, etc.)
│   │   ├── database/           # Room database configuration
│   │   ├── design/             # Design system and themes
│   │   ├── di/                 # Dependency injection modules
│   │   └── network/            # Network configuration
│   ├── data/                   # Data layer implementation
│   │   └── movies/
│   │       ├── api/            # API service definitions
│   │       ├── db/             # Database entities and DAOs
│   │       └── repository/     # Repository implementations
│   ├── domain/                 # Business logic layer
│   │   └── movies/
│   │       ├── model/          # Domain models
│   │       └── usecase/        # Use cases
│   └── presentation/           # UI layer
│       ├── movies/             # Movies list screen
│       └── moviedetails/       # Movie details screen
│
├── androidMain/kotlin/          # Android-specific code
└── iosMain/kotlin/             # iOS-specific code
```

## Architecture

The app follows **Clean Architecture** principles with clear separation of concerns:

### Layers
- **Presentation Layer**: ViewModels, UI state management, Compose screens
- **Domain Layer**: Use cases, business logic, domain models
- **Data Layer**: Repositories, API services, database entities

### Key Patterns
- **MVI**: with reactive state management
- **Repository Pattern**: Abstraction over data sources
- **Use Case Pattern**: Encapsulated business logic operations

## Technologies Used

- **Kotlin Multiplatform**: 2.2.10
- **Compose Multiplatform**: 1.8.2
- **Room Database with Paging3**: Local data persistence
- **Koin**: Dependency injection
- **Ktorfit + Ktor**: HTTP client for API calls
- **Kotlinx Serialization**: JSON parsing

## TODO: 

### 1 Modularization
1 Modularization is not implemented due to time constraints, but common separation of concerns is archived, so it wouldn't be a problem.
As a next step, I'd separate each core/domain/data/presentation folder into a module with extendable "convention gradle plugin" https://docs.gradle.org/current/userguide/custom_plugins.html
to obtain extendability and configurability of each module:
KotlinLibraryPlugin (only kmp deps) ->
ComposeLibraryPlugin (only cmp deps) ->
FeatureLibraryPlugin (project deps)

So code duplication and price of setting up a new feature is minimized:

```kotlin
import DependenciesPlugin.Config

plugins {
    id(Plugins.featureLibraryPlugin)
}

android {
    namespace = Config.namespace + ".presentation.movies"
}

commonDependencies {
    implementation(project(":domain:movies"))
    implementation(project(":core:design"))
}
```

### 2 React to configurations changes (rotation and screen size)

### 3 Unit tests to cover pagination logic

## License

This project is created for demonstration purposes as part of a coding assessment.