# Guia de Explicacion del Proyecto: App Catalogo UNMSM

Esta guia contiene la explicacion clara, sencilla y directa del proyecto para una presentacion o documento academico.

---

## 1. Que es el proyecto? (Resumen en 30 segundos)

Es una **aplicacion movil Android moderna** disenada para orientar y acompanar a los postulantes y estudiantes de la **Universidad Nacional Mayor de San Marcos (UNMSM)**. 

La app funciona como un **catalogo integral de admision**, permitiendo a los usuarios conocer las modalidades de ingreso, explorar las carreras profesionales, revisar cronogramas, ubicaciones de sedes y registrarse directamente al proceso de admision.

---

## 2. Que problema soluciona?

* **Informacion dispersa:** Centraliza fechas, costos, requisitos y planes de estudio en una sola aplicacion.
* **Proceso de inscripcion claro:** Separa de forma intuitiva como postular (modalidades de examen) de lo que se desea estudiar (carreras).
* **Experiencia de usuario accesible:** Ofrece soporte para modo oscuro automatico y cambio de idioma dinamico (Espanol / Ingles).

---

## 3. Modulos y Funcionalidades Principales

### A. Pantalla de Inicio (InicioScreen)
* **Cabecera de bienvenida:** Identidad institucional y diseno corporativo de la UNMSM.
* **Acceso rapido destacado:** Boton directo para iniciar la postulacion.
* **Cuadricula simetrica 2x2:** Cronograma, Catalogo de Carreras, Sedes y Preguntas Frecuentes.
* **Comunicados y Novedades:** Banners interactivos con avisos oficiales.

### B. Medios de Admision y Carreras (ServiciosScreen)
Dividido en dos pestanas organizadas:
1. **Modalidades de Admision:** Presenta las vias de ingreso (Examen Ordinario, Centro Pre, Primeros Puestos), sus costos y el boton directo para **Inscribirse**.
2. **Carreras Profesionales:** Catalogo informativo con buscador en tiempo real, duracion, perfil del egresado y enlace al plan de estudios oficial.

### C. Inscripcion del Postulante (RegistroScreen)
* Formulario reactivo y seguro con validacion de datos (Nombre, DNI, Correo).
* Permite seleccionar la modalidad de ingreso y la carrera deseada con confirmacion instantanea.

### D. Informacion y Soporte
* **Cronograma:** Linea de tiempo con fechas de pago y dias de examen.
* **Sedes:** Direcciones oficiales con integracion a Google Maps.
* **FAQ:** Preguntas y respuestas desplegables con animacion.
* **Central Telefonica y Redes:** Enlaces directos a llamadas telefonicas, correos y redes sociales oficiales.

### E. Configuracion (ConfiguracionScreen)
* **Modo Oscuro / Claro:** Sincronizado con el sistema operativo y conmutador manual.
* **Selector de Idioma:** Cambio instantaneo entre Espanol e Ingles mediante AppCompatDelegate.

---

## 4. Tecnologias y Arquitectura Utilizada

| Elemento | Tecnologia | Por que se utilizo? |
|---|---|---|
| **Lenguaje** | **Kotlin** | Lenguaje oficial de Android, moderno y seguro. |
| **Interfaz de Usuario** | **Jetpack Compose + Material 3** | Creacion de interfaces declarativas, fluidas y con diseno moderno. |
| **Patron de Arquitectura** | **MVVM (Model - View - ViewModel)** | Separa la logica de negocio de la interfaz, permitiendo un codigo limpio y escalable. |
| **Gestion de Estados** | **StateFlow y Coroutines** | Actualizacion reactiva de la pantalla en tiempo real ante eventos del usuario. |
| **Navegacion** | **Navigation Compose** | Control eficiente de rutas, argumentos dinamicos y navegacion con retroceso. |

---

## 5. Guion Sugerido para Exponerlo (Paso a Paso)

> **1. Introduccion:**  
> "Buenas tardes. Hoy les presento la aplicacion movil del Catalogo de Admision de la Universidad Nacional Mayor de San Marcos, desarrollada en Android nativo con Jetpack Compose y arquitectura MVVM."

> **2. Demostracion de Funcionalidades:**  
> * "Al abrir la app, la pantalla de **Inicio** ofrece una vision general con accesos rapidos a cronogramas, sedes y comunicados clave."  
> * "En la seccion **Carreras y Modalidades**, separamos la informacion: una pestana para elegir el medio de admision y otra para conocer los perfiles academicos."  
> * "Al presionar **Inscribirse** en una modalidad, la app abre el formulario preseleccionado, validando los datos del postulante."  
> * "En **Configuracion**, el usuario puede alternar entre modo oscuro/claro y cambiar el idioma entre espanol e ingles de forma inmediata."

> **3. Conclusion:**  
> "Esta aplicacion optimiza la experiencia del postulante, brindando un canal moderno, ordenado y accesible para el proceso de admision universitaria."
