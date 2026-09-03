package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UniversidadSanMarcos.catalogo.data.model.SocialNetwork
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepository
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RedesSocialesViewModel(
    private val repository: CatalogoRepository = CatalogoRepositoryImpl()
) : ViewModel() {
    private val _networks = MutableStateFlow<List<SocialNetwork>>(emptyList())
    val networks: StateFlow<List<SocialNetwork>> = _networks.asStateFlow()

    init {
        loadNetworks()
    }

    private fun loadNetworks() {
        viewModelScope.launch {
            repository.getSocialNetworks().collect { networksList ->
                _networks.value = networksList
            }
        }
    }
}
