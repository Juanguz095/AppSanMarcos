package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CompanyInfo(
    val mission: String,
    val vision: String,
    val history: String
)

class QuienesSomosViewModel : ViewModel() {
    private val _companyInfo = MutableStateFlow<CompanyInfo?>(null)
    val companyInfo: StateFlow<CompanyInfo?> = _companyInfo.asStateFlow()

    init {
        loadInfo()
    }

    private fun loadInfo() {
        _companyInfo.value = CompanyInfo(
            mission = "Formar profesionales líderes con sólidos valores y excelencia académica para contribuir al desarrollo del país.",
            vision = "Ser referente internacional en investigación y formación académica, manteniendo nuestro prestigio como la Decana de América.",
            history = "La UNMSM fue fundada el 12 de mayo de 1551, siendo la universidad más antigua de América con más de 470 años de historia ininterrumpida."
        )
    }
}
