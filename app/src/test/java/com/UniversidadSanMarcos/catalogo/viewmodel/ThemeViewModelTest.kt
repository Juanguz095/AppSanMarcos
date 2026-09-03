package com.UniversidadSanMarcos.catalogo.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ThemeViewModelTest {

    @Test
    fun `initial state is null`() {
        val viewModel = ThemeViewModel()
        assertNull(viewModel.isDarkTheme.value)
    }

    @Test
    fun `setDarkTheme updates state`() {
        val viewModel = ThemeViewModel()
        viewModel.setDarkTheme(true)
        assertEquals(true, viewModel.isDarkTheme.value)
        
        viewModel.setDarkTheme(false)
        assertEquals(false, viewModel.isDarkTheme.value)
    }
}
