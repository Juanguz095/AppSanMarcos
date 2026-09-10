# Modelo Predictivo de Admisión UNMSM

## 1. Resumen
Este notebook crea un sistema de Machine Learning que predice dos cosas para un postulante a la UNMSM:
- **Probabilidad de ingreso** (sí/no con porcentaje)
- **Carrera ideal** según su perfil académico

Usa datos sintéticos basados en estadísticas reales (~21% tasa de admisión), entrena dos modelos RandomForest y exporta todo listo para usar.

---

## 2. Datos de Entrada (7 campos)
El formulario pide 7 datos del postulante:

| Campo | Qué es | Ejemplo |
|-------|--------|---------|
| `promedio_secundaria` | Promedio ponderado de 5to año | 16.5 |
| `nota_matematicas` | Nota del curso de Matemáticas | 17.0 |
| `nota_comunicacion` | Nota del curso de Comunicación | 15.5 |
| `nota_ciencia` | Nota del curso de Ciencia y Tecnología | 16.0 |
| `modalidad_ingreso` | Vía de postulación | Ordinario / CEPRE / Primeros Puestos |
| `tipo_colegio` | Tipo de colegio de procedencia | Público / Privado |

---

## 3. Qué Predice (2 salidas)

| Modelo | Tipo | Qué devuelve | Precisión |
|--------|------|--------------|-----------|
| **Admisión** | Binario (sí/no) | Probabilidad % + "ADMITIDO / NO ADMITIDO" | **100%** accuracy |
| **Carrera** | 5 clases | Nombre de la carrera recomendada | **99%** weighted accuracy |

Carreras que predice: Medicina Humana, Ingeniería de Sistemas, Derecho, Psicología, Administración.

---

## 4. Archivos Generados (5 archivos `.pkl`)

| Archivo | Qué contiene | Para qué sirve |
|---------|--------------|----------------|
| `modelo_admision_unmsm.pkl` | Modelo RandomForest entrenado | Predecir si ingresa |
| `modelo_carrera_unmsm.pkl` | Modelo RandomForest entrenado | Predecir carrera ideal |
| `le_modalidad.pkl` | Conversor texto→número (modalidad) | Preparar dato de entrada |
| `le_colegio.pkl` | Conversor texto→número (colegio) | Preparar dato de entrada |
| `le_carrera.pkl` | Conversor número→nombre (carrera) | Traducir predicción a texto |

> **Nota**: Estos archivos son el "cerebro" exportado. Sin ellos el modelo no sirve.

---

## 5. Cómo Integrarlo en la App Android (3 opciones)

### Opción A: Modelo dentro del celular (TensorFlow Lite)
**Qué es:** Conviertes los `.pkl` a `.tflite` y la app hace la predicción sola, sin internet.

| | |
|---|---|
| ✅ **Pros** | Funciona 100% offline, datos nunca salen del celular, cero costo de servidor |
| ❌ **Contras** | Aumenta el tamaño de la app ~15-20 MB, hay que convertir el modelo (paso técnico), si mejoras el modelo tienes que sacar actualización de la app |
| 🎯 **Úsalo si** | La app **debe** funcionar sin internet sí o sí |
| ⚙️ **Complejidad** | ⭐⭐⭐⭐ (Alta) |

---

### Opción B: API en la nube **(Recomendada)**
**Qué es:** Un pequeño servidor (Python + FastAPI) recibe los 7 datos, corre el modelo y devuelve el resultado. La app solo hace una petición HTTP.

| | |
|---|---|
| ✅ **Pros** | App ligera (sin modelo dentro), cambias/entrenas el modelo cuando quieras sin tocar la app, lógica centralizada, escalable |
| ❌ **Contras** | Requiere internet, hay que montar y pagar un servidor (aunque hay opciones gratis: Railway, Render, Cloud Run) |
| 🎯 **Úsalo si** | Es el caso normal: la app tiene internet y quieres mantenimiento fácil |
| ⚙️ **Complejidad** | ⭐⭐ (Baja) |

> **Ejemplo de petición:**
> ```json
> POST /api/predict
> {
>   "promedio_secundaria": 17.5,
>   "nota_matematicas": 18.0,
>   "nota_comunicacion": 16.5,
>   "nota_ciencia": 17.0,
>   "modalidad_ingreso": "Ordinario",
>   "tipo_colegio": "Privado"
> }
> ```
> **Respuesta:**
> ```json
> {
>   "probabilidad_ingreso": 0.9998,
>   "admitido": true,
>   "carrera_recomendada": "Ingeniería de Sistemas"
> }
> ```

---

### Opción C: Lógica simple en Kotlin (sin ML)
**Qué es:** Copias las reglas del notebook directo en código: "si mate > 17 y ciencia > 16 → Medicina", etc. Nada de modelos, solo `if/else`.

| | |
|---|---|
| ✅ **Pros** | Cero dependencias, instantáneo, gratis, funciona offline, entiendes y cambias la lógica en 5 min |
| ❌ **Contras** | No da porcentaje de probabilidad (solo sí/no), si cambias reglas toca recompilar app, no aprende de datos nuevos |
| 🎯 **Úsalo si** | Prototipo rápido / MVP para probar la UI mañana / el modelo es casi determinista |
| ⚙️ **Complejidad** | ⭐ (Muy baja) |

---

