package com.UniversidadSanMarcos.catalogo.data.model

data class BannerItem(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String? = null
)
