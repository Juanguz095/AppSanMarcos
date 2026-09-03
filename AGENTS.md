# AGENTS.md - Catálogo de Admisión UNMSM

## Project Overview
Aplicación Android para el proceso de admisión de la Universidad Nacional Mayor de San Marcos (UNMSM). Permite a postulantes explorar modalidades de ingreso, carreras profesionales, cronogramas, sedes e inscribirse al proceso de admisión.

## Tech Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Architecture:** MVVM (Model - View - ViewModel)
- **State Management:** StateFlow + Coroutines
- **Navigation:** Navigation Compose
- **Build:** Gradle KTS with Version Catalog (libs.versions.toml)
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 37

## Project Structure
```
app/src/main/java/com/UniversidadSanMarcos/catalogo/
├── MainActivity.kt                    # Entry point
├── data/
│   ├── model/                         # Data classes
│   │   ├── BannerItem.kt
│   │   ├── NewsItem.kt
│   │   ├── Product.kt
│   │   └── SocialNetwork.kt
│   └── repository/                    # Repository pattern
│       ├── CatalogoRepository.kt      # Interface + data classes
│       └── CatalogoRepositoryImpl.kt  # Concrete implementation
├── ui/
│   ├── navigation/
│   │   ├── NavRoutes.kt              # Screen sealed class + items list
│   │   └── AppNavigation.kt          # NavHost + Drawer
│   ├── screens/                       # All Composable screens
│   │   ├── InicioScreen.kt
│   │   ├── ServiciosScreen.kt
│   │   ├── RegistroScreen.kt
│   │   ├── CronogramaScreen.kt
│   │   ├── LocalizarScreen.kt
│   │   ├── NoticiasScreen.kt
│   │   ├── NoticiaDetailScreen.kt
│   │   ├── RedesSocialesScreen.kt
│   │   ├── CentralTelefonicaScreen.kt
│   │   ├── ConfiguracionScreen.kt
│   │   ├── FaqScreen.kt
│   │   ├── QuienesSomosScreen.kt
│   │   ├── CarreraDetailScreen.kt
│   │   └── PlaceholderScreen.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── viewmodel/                         # All ViewModels
│   ├── InicioViewModel.kt
│   ├── ServiciosViewModel.kt
│   ├── RegistroViewModel.kt
│   ├── ThemeViewModel.kt
│   ├── CronogramaViewModel.kt
│   ├── LocalizarViewModel.kt
│   ├── NoticiasViewModel.kt
│   ├── RedesSocialesViewModel.kt
│   ├── CentralTelefonicaViewModel.kt
│   ├── QuienesSomosViewModel.kt
│   └── FaqViewModel.kt
└── util/
    └── SafeIntentLauncher.kt          # Intent helpers
```

## Code Conventions

### Naming
- **Screens:** `XxxScreen.kt` composable functions (e.g., `InicioScreen`, `ServiciosScreen`)
- **ViewModels:** `XxxViewModel.kt` classes (e.g., `InicioViewModel`, `ServiciosViewModel`)
- **Data models:** Plain data classes in `data/model/`
- **Routes:** Defined in `NavRoutes.kt` as `Screen` sealed class objects

### Architecture Pattern
Each feature follows this pattern:
1. **Screen** (Composable) → observes ViewModel via `collectAsState()`
2. **ViewModel** → exposes `StateFlow<UiState>` and handles user actions
3. **Repository** → provides data (hardcoded or from API)
4. **UiState** → sealed class with `Loading`, `Success`, `Empty`, `Error` states

Example:
```kotlin
// Repository
class XxxRepository {
    fun getData(): Flow<List<Data>> = flow { emit(dataList) }
}

// ViewModel
class XxxViewModel(
    private val repository: XxxRepository = XxxRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<XxxUiState>(XxxUiState.Loading)
    val uiState: StateFlow<XxxUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            repository.getData().collect { data ->
                _uiState.value = XxxUiState.Success(data)
            }
        }
    }
}

// Screen
@Composable
fun XxxScreen(viewModel: XxxViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    when (val state = uiState) {
        is XxxUiState.Loading -> LoadingState()
        is XxxUiState.Success -> XxxContent(...)
        is XxxUiState.Empty -> EmptyState()
        is XxxUiState.Error -> ErrorState(...)
    }
}
```

### Strings
- Use `stringResource(R.string.xxx)` for all user-visible text
- Spanish strings in `res/values/strings.xml`
- English strings in `res/values-en/strings.xml`
- Never hardcode strings in Composables

### Imports
- Use wildcard imports for Material3: `import androidx.compose.material3.*`
- Use wildcard imports for Compose Foundation: `import androidx.compose.foundation.*`
- Import specific icons from `Icons.Default.*` or `Icons.AutoMirrored.Filled.*`

## Commands

### Build
```bash
./gradlew build
```

### Unit Tests
```bash
./gradlew test
```

### Instrumentation Tests
```bash
./gradlew connectedAndroidTest
```

### Clean Build
```bash
./gradlew clean build
```

## Testing Conventions
- Tests are in `app/src/test/` (unit) and `app/src/androidTest/` (instrumentation)
- ViewModel tests use `StandardTestDispatcher` and `runTest { }`
- Always set up `Dispatchers.setMain(testDispatcher)` in `@Before`
- Always reset in `@After` with `Dispatchers.resetMain()`
- Use `testDispatcher.scheduler.advanceTimeBy()` for debounce/async flows
- Use `testDispatcher.scheduler.advanceUntilIdle()` to complete all coroutines

## Key Patterns

### Navigation
- Routes defined in `NavRoutes.kt` as `Screen` sealed class
- Drawer items list: `val items = listOf(Screen.Inicio, ...)`
- Detail routes use `{id}` placeholders: `"carrera_detail/{id}"`
- Detail screens hide the TopAppBar via `isDetailRoute` check

### Data Flow
- ViewModels load data in `init {}` block
- Data comes from `CatalogoRepository` (interface) / `CatalogoRepositoryImpl` (implementation)
- Search uses `debounce(300L)` + `combine` for filtering
- Products are hardcoded in Repository (no network calls yet)

### Theme
- `CatalogoTheme` wraps the entire app in `MainActivity.kt`
- Dark/Light mode controlled by `ThemeViewModel`
- Colors defined in `Color.kt` with UNMSM institutional palette

## Adding a New Screen

1. Create `XxxScreen.kt` in `ui/screens/`
2. Create `XxxViewModel.kt` in `viewmodel/`
3. Add route in `NavRoutes.kt`: `object Xxx : Screen("xxx", R.string.menu_xxx, Icons.Default.Xxx)`
4. Add to `items` list in `NavRoutes.kt`
5. Add composable in `AppNavigation.kt` NavHost
6. Add strings in `strings.xml` and `strings.xml` (EN)
7. Create test in `app/src/test/.../viewmodel/XxxViewModelTest.kt`

## Important Notes
- No network/API calls yet - all data is hardcoded in ViewModels
- No dependency injection (no Hilt/Dagger) - ViewModels created directly
- No Room database - no local persistence
- No images loaded from URLs - uses vector icons and gradients
- Internationalization (ES/EN) via Android string resources
