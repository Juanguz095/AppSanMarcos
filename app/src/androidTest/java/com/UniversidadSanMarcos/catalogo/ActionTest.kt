package com.UniversidadSanMarcos.catalogo

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ActionTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun call_button_triggers_dial_intent() {
        // Wait for Menu to be available
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithContentDescription("Menu").fetchSemanticsNodes().isNotEmpty()
        }

        // Go to Central Telefonica
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        composeTestRule.onNodeWithText("Central telefónica").performClick()
        
        // Click first call button
        composeTestRule.onAllNodesWithContentDescription("Llamar")[0].performClick()
        
        // Verify intent
        intended(allOf(
            hasAction(Intent.ACTION_DIAL),
            hasData("tel:+5116197000")
        ))
    }

    @Test
    fun inscription_button_triggers_view_intent() {
        // Wait for hierarchy
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Inscribirse Ahora").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Click button
        composeTestRule.onNodeWithText("Inscribirse Ahora").performClick()
        
        // Verify intent
        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://admision.unmsm.edu.pe")
        ))
    }
}
