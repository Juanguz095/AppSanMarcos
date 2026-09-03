package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepository
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepositoryImpl
import com.UniversidadSanMarcos.catalogo.data.repository.LocationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocalizarViewModel(
    private val repository: CatalogoRepository = CatalogoRepositoryImpl()
) : ViewModel() {
    private val _locations = MutableStateFlow<List<LocationData>>(emptyList())
    val locations: StateFlow<List<LocationData>> = _locations.asStateFlow()

    init {
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            repository.getLocations().collect { locationList ->
                _locations.value = locationList
            }
        }
    }
}
