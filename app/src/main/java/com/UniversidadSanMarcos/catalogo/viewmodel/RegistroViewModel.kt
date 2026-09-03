package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RegistroUiState {
    object Idle : RegistroUiState()
    object Loading : RegistroUiState()
    object Success : RegistroUiState()
    data class Error(val message: String) : RegistroUiState()
}

class RegistroViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<RegistroUiState>(RegistroUiState.Idle)
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _dni = MutableStateFlow("")
    val dni: StateFlow<String> = _dni.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _modalidad = MutableStateFlow("Examen Ordinario")
    val modalidad: StateFlow<String> = _modalidad.asStateFlow()

    private val _carreraSeleccionada = MutableStateFlow("")
    val carreraSeleccionada: StateFlow<String> = _carreraSeleccionada.asStateFlow()

    val modalidadesDisponibles = listOf(
        "Examen Ordinario",
        "Centro Preuniversitario",
        "Primeros Puestos"
    )

    val carrerasDisponibles = listOf(
        "Medicina Humana",
        "Ingeniería de Sistemas",
        "Derecho",
        "Administración",
        "Contabilidad",
        "Psicología",
        "Economía"
    )

    fun inicializarModalidad(nombreModalidad: String) {
        if (nombreModalidad.isNotBlank()) {
            _modalidad.value = nombreModalidad
        }
    }

    fun onNombreChange(value: String) {
        _nombre.value = value
    }

    fun onDniChange(value: String) {
        _dni.value = value
    }

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onModalidadChange(value: String) {
        _modalidad.value = value
    }

    fun onCarreraChange(value: String) {
        _carreraSeleccionada.value = value
    }

    fun enviarRegistro() {
        if (_nombre.value.isBlank() || _dni.value.isBlank() || _email.value.isBlank() || _carreraSeleccionada.value.isBlank()) {
            _uiState.value = RegistroUiState.Error("Por favor completa todos los campos requeridos")
            return
        }

        _uiState.value = RegistroUiState.Loading
        viewModelScope.launch {
            delay(1500L)
            _uiState.value = RegistroUiState.Success
        }
    }

    fun resetState() {
        _nombre.value = ""
        _dni.value = ""
        _email.value = ""
        _carreraSeleccionada.value = ""
        _uiState.value = RegistroUiState.Idle
    }
}
