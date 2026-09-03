package com.UniversidadSanMarcos.catalogo.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ServiciosViewModelTest {

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
    fun `searchQuery filters products`() = runTest {
        val viewModel = ServiciosViewModel()
        
        // Initial state
        testDispatcher.scheduler.advanceUntilIdle()
        val initialCount = viewModel.products.value.size
        
        // Change search query
        viewModel.onSearchQueryChange("Medicina")
        
        // Advance time for debounce(300)
        testDispatcher.scheduler.advanceTimeBy(400)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val filteredProducts = viewModel.products.value
        assertTrue(filteredProducts.all { it.name.contains("Medicina", ignoreCase = true) })
    }
    
    private fun assertTrue(condition: Boolean) {
        assert(condition)
    }
}
