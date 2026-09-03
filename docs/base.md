# Archivos Creados, Principales Componentes y Dependencias

## Modelos de Datos (`datamodel`)

| Archivo Creado | Principales Componentes Implementados | Dependencias / Librerías Incorporadas |
|---|---|---|
| `BannerItem.kt` | **BannerItem**: Data Class con id, título, descripción y URL de imagen | Kotlin Standard Library |
| `NewsItem.kt` | **NewsItem**: Data Class con id, título, contenido y fecha | Kotlin Standard Library |
| `Product.kt` | **Product**: Data Class para carreras y modalidades con precio, duración, perfil y URL curricular | Kotlin Standard Library |
| `SocialNetwork.kt` | **SocialNetwork**: Data Class con nombre, URL y tipo de plataforma | Kotlin Standard Library |

---

## Navegación

| Archivo Creado | Principales Componentes Implementados | Dependencias / Librerías Incorporadas |
|---|---|---|
| `NavRoutes.kt` | Sealed Class `Screen`, definición de rutas estáticas y dinámicas (`{id}`), lista `items` para el menú | `androidx.compose.material:material-icons-extended`, `androidx.annotation` |
| `AppNavigation.kt` | `AppNavigation`, `AppDrawerContent`, `ModalNavigationDrawer`, `NavHost`, `CenterAlignedTopAppBar` con preservación de estado | `androidx.navigation:navigation-compose`, `androidx.compose.material3`, `kotlinx.coroutines` |

---

## Pantallas

| Archivo Creado | Principales Componentes Implementados | Dependencias / Librerías Incorporadas |
|---|---|---|
| `InicioScreen.kt` | Carrusel `BannerCard`, accesos rápidos, manejo de estados (`CircularProgressIndicator`) | `androidx.compose.material3`, `androidx.lifecycle:lifecycle-viewmodel-compose` |
| `ServiciosScreen.kt` | `OutlinedTextField` de búsqueda, `LazyColumn`, `ProductCard`, tags de categorías | `androidx.compose.material3`, `androidx.lifecycle:lifecycle-viewmodel-compose` |
| `CarreraDetailScreen.kt` | Vista detallada de carrera/modalidad con botón de enlace curricular externo | `androidx.compose.material3`, `androidx.navigation:navigation-compose`, `SafeIntentLauncher` |
| `RegistroScreen.kt` | Formulario reactivo con campos de texto, mensajes de error y botón de confirmación | `androidx.compose.material3`, `androidx.lifecycle:lifecycle-viewmodel-compose` |
| `CronogramaScreen.kt` | Línea de tiempo visual (`TimelineItem`, `ElevatedCard`) de etapas de admisión | `androidx.compose.material3`, `androidx.lifecycle:lifecycle-viewmodel-compose` |
| `LocalizarScreen.kt` | Tarjetas de sedes y facultades con botón de redirección hacia Google Maps | `androidx.compose.material3`, `SafeIntentLauncher` |
| `NoticiasScreen.kt` | Listado de comunicados con fechas y navegación directa al detalle | `androidx.compose.material3`, `androidx.lifecycle:lifecycle-viewmodel-compose` |
| `NoticiaDetailScreen.kt` | Lectura completa del artículo seleccionado con botón de navegación hacia atrás | `androidx.compose.material3`, `androidx.navigation:navigation-compose` |
| `RedesSocialesScreen.kt` | Tarjetas de canales oficiales con lanzamiento de navegador / app externa | `androidx.compose.material3`, `SafeIntentLauncher` |
| `CentralTelefonicaScreen.kt` | Directorio telefónico con botones de marcado rápido (`ACTION_DIAL`) y correo (`ACTION_SENDTO`) | `androidx.compose.material3`, `SafeIntentLauncher` |
| `ConfiguracionScreen.kt` | `Switch` reactivo para alternar entre modo claro y modo oscuro | `androidx.compose.material3`, `androidx.lifecycle:lifecycle-viewmodel-compose` |
| `FaqScreen.kt` | `FaqCard` expandible con animación suave al desplegar respuestas | `androidx.compose.material3`, `androidx.compose.animation` |
| `QuienesSomosScreen.kt` | Secciones informativas de Misión, Visión, Historia y Valores institucionales | `androidx.compose.material3` |
| `PlaceholderScreen.kt` | Pantalla genérica de reserva | `androidx.compose.material3` |

---

## Theme

| Archivo Creado | Principales Componentes Implementados | Dependencias / Librerías Incorporadas |
|---|---|---|
| `Theme.kt` | Composable `CatalogoTheme`, esquemas `LightColorScheme` y `DarkColorScheme` | `androidx.compose.material3`, `androidx.compose.foundation` |
| `Color.kt` | Paleta de colores institucionales (Azul UNMSM, Dorado, Fondos, Superficies) | `androidx.compose.ui:ui-graphics` |
| `Type.kt` | Definición de estilos tipográficos `Typography` (Display, Title, Body, Label) | `androidx.compose.material3` |

---

## ViewModels

| Archivo Creado | Principales Componentes Implementados | Dependencias / Librerías Incorporadas |
|---|---|---|
| `InicioViewModel.kt` | `InicioViewModel`, `Sealed Class InicioUiState` (`Loading`, `Success`, `Empty`, `Error`), `StateFlow` | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines` |
| `ServiciosViewModel.kt` | `ServiciosViewModel`, búsqueda con operador `debounce(300L)`, `combine` y emisión `stateIn` | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `RegistroViewModel.kt` | `RegistroViewModel`, validación de campos de entrada y control de estado de envío | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `ThemeViewModel.kt` | `ThemeViewModel`, control y persistencia en memoria del modo oscuro global | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `CronogramaViewModel.kt` | `CronogramaViewModel`, flujo de eventos y fechas clave de admisión | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `LocalizarViewModel.kt` | `LocalizarViewModel`, lista reactiva de sedes y coordenadas | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `NoticiasViewModel.kt` | `NoticiasViewModel`, provisión reactiva de notas de prensa | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `RedesSocialesViewModel.kt` | `RedesSocialesViewModel`, catálogo de canales oficiales | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `CentralTelefonicaViewModel.kt` | `CentralTelefonicaViewModel`, directorio de contactos institucionales | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `QuienesSomosViewModel.kt` | `QuienesSomosViewModel`, provisión de información institucional | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |
| `FaqViewModel.kt` | `FaqViewModel`, listado reactivo de preguntas frecuentes | `androidx.lifecycle:lifecycle-viewmodel-ktx`, `kotlinx.coroutines.flow` |

---

## Utilidades

| Archivo Creado | Principales Componentes Implementados | Dependencias / Librerías Incorporadas |
|---|---|---|
| `SafeIntentLauncher.kt` | Singleton `SafeIntentLauncher` (`launchBrowser`, `launchDialer`, `launchEmail`, `launchMaps`) con protección `resolveActivity` | Android Framework Core (`Intent`, `Uri`, `Context`, `Toast`) |
