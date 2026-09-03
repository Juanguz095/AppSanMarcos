package com.UniversidadSanMarcos.catalogo.data.repository

import com.UniversidadSanMarcos.catalogo.data.model.BannerItem
import com.UniversidadSanMarcos.catalogo.data.model.NewsItem
import com.UniversidadSanMarcos.catalogo.data.model.Product
import com.UniversidadSanMarcos.catalogo.data.model.SocialNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CatalogoRepositoryImpl : CatalogoRepository {

    private val banners = listOf(
        BannerItem(
            id = 1,
            title = "Proceso de Admisión 2027-I",
            description = "Inscripciones abiertas. Consulta las fechas por letra de apellido y rinde tu examen en la Ciudad Universitaria."
        ),
        BannerItem(
            id = 2,
            title = "Simulacro Presencial Descentralizado",
            description = "Mide tus conocimientos con preguntas tipo DECO en Lima, Huaral y sedes regionales de la UNMSM."
        ),
        BannerItem(
            id = 3,
            title = "Centro Preuniversitario CEPRE",
            description = "Ciclo Ordinario con ingreso directo a las carreras con mayor demanda nacional."
        ),
        BannerItem(
            id = 4,
            title = "Cuadro de Vacantes Oficial",
            description = "Revisa la distribución de vacantes por áreas: Ciencias de la Salud, Ingenierías, Letras y Negocios."
        )
    )

    private val products = listOf(
        // MODALIDADES DE ADMISIÓN
        Product(
            id = 1,
            name = "Examen Ordinario",
            description = "Proceso general descentralizado dirigido a egresados de educación secundaria.",
            price = 350.0,
            category = "Modalidad",
            profile = "Postulante con secundaria completa que busca ingresar a una de nuestras 66 carreras profesionales mediante prueba de destrezas cognitivas (DECO)."
        ),
        Product(
            id = 2,
            name = "Centro Preuniversitario",
            description = "Ingreso directo a la UNMSM mediante evaluación continua en el Centro Pre (CEPRE).",
            price = 1200.0,
            category = "Modalidad",
            profile = "Estudiante con preparación de alto rigor académico en el CEPREUNMSM que alcanza vacante por cuadro de mérito en exámenes periódicos."
        ),
        Product(
            id = 3,
            name = "Primeros Puestos",
            description = "Modalidad especial exclusiva para los dos primeros puestos del nivel secundario.",
            price = 350.0,
            category = "Modalidad",
            profile = "Alumnos de excelencia académica acreditados que culminaron sus estudios escolares en el primer o segundo puesto de su promoción."
        ),

        // CARRERAS PROFESIONALES
        Product(
            id = 4,
            name = "Medicina Humana",
            description = "Facultad de Medicina San Fernando. Formación médica de élite con investigación clínica.",
            price = 0.0,
            category = "Carrera",
            duration = "7 años",
            profile = "Profesional médico con sólida formación científica, humanística y ética, capacitado para la prevención, diagnóstico, tratamiento y rehabilitación en salud.",
            curriculumUrl = "https://medicina.unmsm.edu.pe"
        ),
        Product(
            id = 5,
            name = "Ingeniería de Sistemas",
            description = "Facultad de Ingeniería de Sistemas e Informática. Arquitectura de software, datos e IA.",
            price = 0.0,
            category = "Carrera",
            duration = "5 años",
            profile = "Especialista en transformación digital, diseño de soluciones algorítmicas avanzadas, ciberseguridad y gestión estratégica de tecnologías de información.",
            curriculumUrl = "https://sistemas.unmsm.edu.pe"
        ),
        Product(
            id = 6,
            name = "Derecho",
            description = "Facultad de Derecho y Ciencia Política. Defensa jurídica, litigio y magistratura.",
            price = 0.0,
            category = "Carrera",
            duration = "6 años",
            profile = "Jurista con capacidad argumentativa, dominio del ordenamiento legal constitucional e internacional y liderazgo en la administración de justicia.",
            curriculumUrl = "https://derecho2.unmsm.edu.pe"
        ),
        Product(
            id = 7,
            name = "Administración",
            description = "Facultad de Ciencias Administrativas. Gestión empresarial global y emprendimiento.",
            price = 0.0,
            category = "Carrera",
            duration = "5 años",
            profile = "Líder en dirección organizacional, finanzas corporativas, formulación de planes estratégicos e innovación en modelos de negocios sustentables.",
            curriculumUrl = "https://administracion.unmsm.edu.pe"
        ),
        Product(
            id = 8,
            name = "Contabilidad",
            description = "Facultad de Ciencias Contables. Auditoría, peritaje, finanzas y tributación.",
            price = 0.0,
            category = "Carrera",
            duration = "5 años",
            profile = "Contador público competente en auditoría financiera, asesoría tributaria, gestión de costos y análisis de estados contables para la toma de decisiones.",
            curriculumUrl = "https://contabilidad.unmsm.edu.pe"
        ),
        Product(
            id = 9,
            name = "Psicología",
            description = "Facultad de Psicología. Evaluación psicológica, intervención clínica y social.",
            price = 0.0,
            category = "Carrera",
            duration = "5 años",
            profile = "Profesional de la salud mental especializado en procesos cognitivos, evaluación psicométrica, psicoterapia e investigación del comportamiento.",
            curriculumUrl = "https://psicologia.unmsm.edu.pe"
        ),
        Product(
            id = 10,
            name = "Economía",
            description = "Facultad de Ciencias Económicas. Modelado macroeconómico, finanzas y políticas públicas.",
            price = 0.0,
            category = "Carrera",
            duration = "5 años",
            profile = "Analista de mercados, proyecciones econométricas, formulación de políticas socioeconómicas y optimización de recursos en el sector público y privado.",
            curriculumUrl = "https://economia.unmsm.edu.pe"
        )
    )

    private val news = listOf(
        NewsItem(1, "Publicación de Resultados 2027-I", "Ya se encuentran disponibles los resultados por facultad.", "27/08/2026"),
        NewsItem(2, "Ampliación de Vacantes", "El Consejo Universitario aprobó nuevas vacantes para Ingeniería.", "25/08/2026"),
        NewsItem(3, "Charla de Orientación Vocacional", "Evento gratuito este sábado vía Zoom para postulantes.", "20/08/2026"),
        NewsItem(4, "Entrega de Carnés de Postulante", "Inicia la entrega de fotochecks en la oficina central.", "15/08/2026")
    )

    private val socialNetworks = listOf(
        SocialNetwork("Facebook", "https://facebook.com/unmsm", "Facebook"),
        SocialNetwork("Instagram", "https://instagram.com/unmsm", "Instagram"),
        SocialNetwork("Twitter", "https://twitter.com/unmsm", "Twitter"),
        SocialNetwork("YouTube", "https://youtube.com/unmsm", "YouTube"),
        SocialNetwork("LinkedIn", "https://linkedin.com/company/unmsm", "LinkedIn")
    )

    private val timeline = listOf(
        TimelineItemData("Inicio de Inscripciones", "01 de Agosto, 2026", "Apertura del portal de admisión para todas las modalidades.", true),
        TimelineItemData("Simulacro Presencial", "15 de Septiembre, 2026", "Ensayo general en la Ciudad Universitaria.", false),
        TimelineItemData("Cierre de Inscripciones", "30 de Octubre, 2026", "Fecha límite para registro de pagos y datos.", false),
        TimelineItemData("Examen de Admisión", "15 de Noviembre, 2026", "Jornada única de evaluación presencial.", false),
        TimelineItemData("Publicación de Resultados", "16 de Noviembre, 2026", "Listado oficial de ingresantes por facultad.", false)
    )

    private val locations = listOf(
        LocationData(
            "Sede Principal Universidad San Marcos",
            "Av. Universitaria / Av. Germán Amézaga s/n, Lima",
            -12.0560,
            -77.0844
        ),
        LocationData(
            "Facultad de Medicina - San Fernando",
            "Av. Grimaldo del Solar 372, Cercado de Lima",
            -12.0460,
            -77.0300
        ),
        LocationData(
            "Facultad de Ingeniería de Sistemas",
            "Av. Universitaria s/n, Cercado de Lima",
            -12.0550,
            -77.0850
        ),
        LocationData(
            "Facultad de Derecho",
            "Av. Universitaria s/n, Cercado de Lima",
            -12.0540,
            -77.0830
        )
    )

    private val faq = listOf(
        FaqItemData(
            "¿Cuáles son los requisitos para postular?",
            "Debes contar con secundaria completa, DNI vigente, y realizar el pago de la tasa de examen según la modalidad elegida."
        ),
        FaqItemData(
            "¿Cuánto cuesta el examen de admisión?",
            "El examen ordinario cuesta S/ 350. El Centro Preuniversitario tiene un costo de S/ 1,200 por ciclo."
        ),
        FaqItemData(
            "¿Dónde rindo el examen?",
            "El examen se realiza en la Ciudad Universitaria de San Marcos y en sedes descentralizadas a nivel nacional."
        ),
        FaqItemData(
            "¿Cómo puedo saber si aprobé?",
            "Los resultados se publican en el portal oficial de admisión y en las instalaciones de la universidad."
        ),
        FaqItemData(
            "¿Puedo cambiar de carrera después de inscribirme?",
            "Sí, puedes modificar tu elección antes del cierre de inscripciones a través del portal."
        )
    )

    private val phoneDirectory = listOf(
        PhoneDirectoryItem("Central Telefónica", "01 619-7000", "informes@unmsm.edu.pe"),
        PhoneDirectoryItem("Oficina de Admisión", "01 619-7001", "admision@unmsm.edu.pe"),
        PhoneDirectoryItem("Registro General", "01 619-7002", "registro@unmsm.edu.pe"),
        PhoneDirectoryItem("Biblioteca Central", "01 619-7003", "biblioteca@unmsm.edu.pe"),
        PhoneDirectoryItem("Defensoría del Estudiante", "01 619-7004", "defensoria@unmsm.edu.pe")
    )

    override fun getBanners(): Flow<List<BannerItem>> = flow {
        emit(banners)
    }

    override fun getProducts(): Flow<List<Product>> = flow {
        emit(products)
    }

    override fun getProductsByCategory(category: String): Flow<List<Product>> = flow {
        emit(products.filter { it.category.equals(category, ignoreCase = true) })
    }

    override fun searchProducts(query: String): Flow<List<Product>> = flow {
        if (query.isEmpty()) {
            emit(products)
        } else {
            emit(products.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            })
        }
    }

    override fun getNews(): Flow<List<NewsItem>> = flow {
        emit(news)
    }

    override fun getNewsById(id: Int): NewsItem? {
        return news.find { it.id == id }
    }

    override fun getSocialNetworks(): Flow<List<SocialNetwork>> = flow {
        emit(socialNetworks)
    }

    override fun getTimeline(): Flow<List<TimelineItemData>> = flow {
        emit(timeline)
    }

    override fun getLocations(): Flow<List<LocationData>> = flow {
        emit(locations)
    }

    override fun getFaq(): Flow<List<FaqItemData>> = flow {
        emit(faq)
    }

    override fun getPhoneDirectory(): Flow<List<PhoneDirectoryItem>> = flow {
        emit(phoneDirectory)
    }
}