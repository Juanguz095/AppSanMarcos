package com.UniversidadSanMarcos.catalogo

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test

class SimpleTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testRoot() {
        composeTestRule.onRoot().printToLog("SimpleTest")
    }
}
