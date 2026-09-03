package com.UniversidadSanMarcos.catalogo.data.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val duration: String = "",
    val profile: String = "",
    val curriculumUrl: String = ""
)
