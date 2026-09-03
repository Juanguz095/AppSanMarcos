package com.UniversidadSanMarcos.catalogo.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InicioViewModelTest {

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
    fun `loadBanners updates state to Success`() = runTest {
        val viewModel = InicioViewModel()
        
        // Initially Loading
        assertTrue(viewModel.uiState.value is InicioUiState.Loading)

        // Advance time to bypass delay(1000)
        testDispatcher.scheduler.advanceTimeBy(1100)
        
        val state = viewModel.uiState.value
        assertTrue(state is InicioUiState.Success)
        if (state is InicioUiState.Success) {
            assertTrue(state.banners.isNotEmpty())
        }
    }
}
