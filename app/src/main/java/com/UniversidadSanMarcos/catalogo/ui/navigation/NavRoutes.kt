package com.UniversidadSanMarcos.catalogo.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector
import com.UniversidadSanMarcos.catalogo.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Inicio : Screen("inicio", R.string.menu_inicio, Icons.Default.Home)
    object QuienesSomos : Screen("quienes_somos", R.string.menu_quienes_somos, Icons.Default.AccountBalance)
    object Servicios : Screen("servicios", R.string.menu_servicios, Icons.Default.School)
    object Localizar : Screen("localizar", R.string.menu_localizar, Icons.Default.LocationOn)
    object Noticias : Screen("noticias", R.string.menu_noticias, Icons.Default.Newspaper)
    object RedesSociales : Screen("redes_sociales", R.string.menu_redes_sociales, Icons.Default.Share)
    object CentralTelefonica : Screen("central_telefonica", R.string.menu_central_telefonica, Icons.Default.Call)
    object Configuracion : Screen("configuracion", R.string.menu_configuracion, Icons.Default.Settings)
    object Faq : Screen("faq", R.string.menu_faq, Icons.AutoMirrored.Filled.HelpCenter)
    object Cronograma : Screen("cronograma", R.string.menu_cronograma, Icons.Default.CalendarMonth)
    object Registro : Screen("registro", R.string.menu_registro, Icons.Default.AppRegistration)

    // Details (not in drawer)
    object CarreraDetail : Screen("carrera_detail/{id}", 0, Icons.Default.Home)
    object NoticiaDetail : Screen("noticia_detail/{id}", 0, Icons.Default.Home)
}

// Items visibles en el Drawer lateral (se retira Registro para integrarlo directamente en las modalidades/carreras)
val items = listOf(
    Screen.Inicio,
    Screen.QuienesSomos,
    Screen.Servicios,
    Screen.Cronograma,
    Screen.Localizar,
    Screen.Noticias,
    Screen.RedesSociales,
    Screen.CentralTelefonica,
    Screen.Configuracion,
    Screen.Faq
)
