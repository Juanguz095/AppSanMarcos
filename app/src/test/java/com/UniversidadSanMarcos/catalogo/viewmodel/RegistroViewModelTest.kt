package com.UniversidadSanMarcos.catalogo.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegistroViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Idle`() {
        val viewModel = RegistroViewModel()
        assertTrue(viewModel.uiState.value is RegistroUiState.Idle)
    }

    @Test
    fun `empty fields trigger Error state`() {
        val viewModel = RegistroViewModel()
        viewModel.enviarRegistro()
        assertTrue(viewModel.uiState.value is RegistroUiState.Error)
    }

    @Test
    fun `valid fields trigger Success state after delay`() = runTest {
        val viewModel = RegistroViewModel()
        viewModel.onNombreChange("Juan Perez")
        viewModel.onDniChange("12345678")
        viewModel.onEmailChange("juan@perez.com")
        viewModel.onCarreraChange("Derecho")
        
        viewModel.enviarRegistro()
        
        // Check loading state
        assertTrue(viewModel.uiState.value is RegistroUiState.Loading)
        
        // Advance time to bypass delay(2000)
        testDispatcher.scheduler.advanceTimeBy(2100)
        
        assertTrue(viewModel.uiState.value is RegistroUiState.Success)
    }

    @Test
    fun `resetState restores initial values`() {
        val viewModel = RegistroViewModel()
        viewModel.onNombreChange("Juan")
        viewModel.resetState()
        
        assertEquals("", viewModel.nombre.value)
        assertTrue(viewModel.uiState.value is RegistroUiState.Idle)
    }
}
