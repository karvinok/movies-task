# Movies App - TMDB Client

Compose Multiplatform mobile application
that displays popular movies and search functionality using The Movie Database (TMDB) API.
Built with Jetpack Compose for shared UI and following clean architecture principles.

## Setup Instructions

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

## Future Improvements

- **Unit Tests**: Comprehensive test coverage for business logic
- **Advanced Filtering**: Genre, year, rating filters
- **Modularization** Separate all core/domain/data/presentation folders into modules with extendable "convention gradle plugin" https://docs.gradle.org/current/userguide/custom_plugins.html

## License

This project is created for demonstration purposes as part of a coding assessment.