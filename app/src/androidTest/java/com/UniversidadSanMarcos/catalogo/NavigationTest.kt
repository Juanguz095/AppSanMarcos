package com.UniversidadSanMarcos.catalogo

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.UniversidadSanMarcos.catalogo.ui.navigation.AppNavigation
import com.UniversidadSanMarcos.catalogo.ui.theme.CatalogoTheme
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_starts_at_inicio() {
        // Wait for loading to finish
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Admisión UNMSM").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Admisión UNMSM").assertIsDisplayed()
    }

    @Test
    fun drawer_opens_and_navigates_to_quienes_somos() {
        // Wait for loading to finish
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithContentDescription("Menu").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Open drawer
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        
        // Find and click "Quiénes somos" in drawer
        composeTestRule.onNodeWithText("Quiénes somos").performClick()
        
        // Verify we are on "Quiénes somos" screen
        composeTestRule.onNodeWithText("Nuestra Identidad").assertIsDisplayed()
    }

    @Test
    fun search_in_servicios_works() {
        // Wait for loading to finish
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithContentDescription("Menu").fetchSemanticsNodes().isNotEmpty()
        }

        // Go to Servicios
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        composeTestRule.onNodeWithText("Carreras y Modalidades").performClick()
        
        // Find search field and type
        composeTestRule.onNodeWithText("Buscar carrera o modalidad…").performTextInput("Medicina")
        
        // Wait for debounce and search result
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Medicina Humana").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify results
        composeTestRule.onNodeWithText("Medicina Humana").assertIsDisplayed()
    }

    @Test
    fun theme_switch_works() {
        // Wait for hierarchy
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithContentDescription("Menu").fetchSemanticsNodes().isNotEmpty()
        }

        // Go to Configuración
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        composeTestRule.onNodeWithText("Configuración").performClick()
        
        // Find Switch row and toggle
        composeTestRule.onNodeWithText("Modo Oscuro").assertIsDisplayed().performClick()
    }
}
