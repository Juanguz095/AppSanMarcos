package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UniversidadSanMarcos.catalogo.data.model.BannerItem
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepository
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class InicioUiState {
    object Loading : InicioUiState()
    data class Success(val banners: List<BannerItem>) : InicioUiState()
    object Empty : InicioUiState()
    data class Error(val message: String) : InicioUiState()
}

class InicioViewModel(
    private val repository: CatalogoRepository = CatalogoRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow<InicioUiState>(InicioUiState.Loading)
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    init {
        loadBanners()
    }

    fun loadBanners() {
        viewModelScope.launch {
            _uiState.value = InicioUiState.Loading
            try {
                repository.getBanners().collect { banners ->
                    if (banners.isEmpty()) {
                        _uiState.value = InicioUiState.Empty
                    } else {
                        _uiState.value = InicioUiState.Success(banners)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = InicioUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
