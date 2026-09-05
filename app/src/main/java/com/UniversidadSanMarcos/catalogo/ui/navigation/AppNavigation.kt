package com.UniversidadSanMarcos.catalogo.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.UniversidadSanMarcos.catalogo.R
import com.UniversidadSanMarcos.catalogo.ui.screens.*
import com.UniversidadSanMarcos.catalogo.viewmodel.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(themeViewModel: ThemeViewModel = viewModel()) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isDetailRoute = currentRoute?.startsWith("carrera_detail") == true ||
            currentRoute?.startsWith("noticia_detail") == true ||
            currentRoute?.startsWith("registro") == true

    val currentScreen = remember(currentRoute) {
        items.find { it.route == currentRoute } ?: Screen.Inicio
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = !isDetailRoute,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(Screen.Inicio.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (!isDetailRoute) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(stringResource(id = currentScreen.resourceId))
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = stringResource(id = R.string.content_desc_menu))
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Inicio.route,
                modifier = Modifier.padding(if (!isDetailRoute) innerPadding else androidx.compose.foundation.layout.PaddingValues())
            ) {
                composable(Screen.Inicio.route) {
                    InicioScreen(onNavigate = { route -> navController.navigate(route) })
                }
                composable(Screen.QuienesSomos.route) { QuienesSomosScreen() }
                composable(Screen.Servicios.route) {
                    ServiciosScreen(
                        onCarreraClick = { id -> navController.navigate("carrera_detail/$id") },
                        onModalidadInscribirClick = { modalidad -> navController.navigate("registro/$modalidad") }
                    )
                }
                composable(Screen.Cronograma.route) { CronogramaScreen() }
                composable(Screen.Localizar.route) { LocalizarScreen() }
                composable(Screen.Noticias.route) {
                    NoticiasScreen(onNoticiaClick = { id -> navController.navigate("noticia_detail/$id") })
                }
                composable(Screen.RedesSociales.route) { RedesSocialesScreen() }
                composable(Screen.CentralTelefonica.route) { CentralTelefonicaScreen() }
                composable(Screen.Configuracion.route) { ConfiguracionScreen(themeViewModel = themeViewModel) }
                composable(Screen.Faq.route) { FaqScreen() }

                composable("registro") {
                    RegistroScreen(onBackClick = { navController.popBackStack() })
                }
                composable("registro/{modalidad}") { backStackEntry ->
                    val modalidad = backStackEntry.arguments?.getString("modalidad") ?: ""
                    RegistroScreen(
                        modalidadInicial = modalidad,
                        onBackClick = { navController.popBackStack() }
                    )
                }

                composable("carrera_detail/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                    CarreraDetailScreen(carreraId = id, navController = navController)
                }
                composable("noticia_detail/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                    NoticiaDetailScreen(noticiaId = id, navController = navController)
                }
            }
        }
    }
}

@Composable
fun AppDrawerContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    ModalDrawerSheet {
        items.forEach { screen ->
            NavigationDrawerItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen.route) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
