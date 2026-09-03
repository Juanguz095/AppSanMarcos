# Plan de Implementación - Catálogo Multimodular (Fase 1)

Este plan establece la base tecnológica del proyecto, migrando de una estructura XML tradicional a una arquitectura moderna basada en Jetpack Compose, Material 3 y MVVM, cumpliendo con los requisitos de internacionalización y diseño profesional.

## Cambios Propuestos

### Configuración y Dependencias
Se actualizarán los archivos de construcción para habilitar Jetpack Compose y las librerías necesarias.

#### [MODIFY] [libs.versions.toml](file:///C:/Users/Estudiante/AndroidStudioProjects/Catalogo/gradle/libs.versions.toml)
- Agregar versiones para Compose BOM, Material 3, Navigation y Lifecycle.
- Definir los bundles de librerías para simplificar el build.gradle.

#### [MODIFY] [build.gradle.kts (Proyecto)](file:///C:/Users/Estudiante/AndroidStudioProjects/Catalogo/build.gradle.kts)
- Asegurar que el plugin de Kotlin esté correctamente configurado.

#### [MODIFY] [build.gradle.kts (App)](file:///C:/Users/Estudiante/AndroidStudioProjects/Catalogo/app/build.gradle.kts)
- Habilitar `buildFeatures { compose = true }`.
- Configurar `composeOptions`.
- Implementar las nuevas dependencias de Compose y Material 3.

---

### Recursos e Internacionalización (ES/EN)
Se prepararán los strings para los 8 módulos requeridos.

#### [MODIFY] [strings.xml (es)](file:///C:/Users/Estudiante/AndroidStudioProjects/Catalogo/app/src/main/res/values/strings.xml)
- Definir nombres de módulos: Inicio, Quiénes somos, Servicios/Productos, Localizar, Noticias, Redes sociales, Central telefónica, Configuración.

#### [NEW] [strings.xml (en)](file:///C:/Users/Estudiante/AndroidStudioProjects/Catalogo/app/src/main/res/values-en/strings.xml)
- Traducciones: Home, About Us, Services/Products, Locate, News, Social Media, Phone Center, Settings.

---

### UI Base y Tematización
Creación del sistema de diseño Material 3 (Colores, Tipografía y Tema).

#### [NEW] [Color.kt, Type.kt, Theme.kt](file:///C:/Users/Estudiante/AndroidStudioProjects/Catalogo/app/src/main/java/com/UniversidadSanMarcos/catalogo/ui/theme/)
- Implementación de soporte para modo claro/oscuro.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/Estudiante/AndroidStudioProjects/Catalogo/app/src/main/java/com/UniversidadSanMarcos/catalogo/MainActivity.kt)
- Migración de `setContentView(R.layout...)` a `setContent { CatalogoTheme { ... } }`.

---

## Plan de Verificación

### Pruebas Automatizadas
- Ejecución de `gradle build` para asegurar que las nuevas dependencias no generen conflictos.

### Verificación Manual
- Comprobar que la App inicie correctamente con una pantalla base de Compose.
- Verificar el cambio de idioma (ES/EN) en el sistema y que los textos se actualicen.
- Validar el cambio entre modo claro y oscuro.
