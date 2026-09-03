package com.UniversidadSanMarcos.catalogo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UniversidadSanMarcos.catalogo.ui.navigation.AppNavigation
import com.UniversidadSanMarcos.catalogo.ui.theme.CatalogoTheme
import com.UniversidadSanMarcos.catalogo.viewmodel.ThemeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkThemePref by themeViewModel.isDarkTheme.collectAsState()
            val isDark = isDarkThemePref ?: isSystemInDarkTheme()

            CatalogoTheme(darkTheme = isDark) {
                AppNavigation(themeViewModel = themeViewModel)
            }
        }
    }
}
