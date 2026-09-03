package com.UniversidadSanMarcos.catalogo.data.repository

import com.UniversidadSanMarcos.catalogo.data.model.BannerItem
import com.UniversidadSanMarcos.catalogo.data.model.NewsItem
import com.UniversidadSanMarcos.catalogo.data.model.Product
import com.UniversidadSanMarcos.catalogo.data.model.SocialNetwork
import kotlinx.coroutines.flow.Flow

interface CatalogoRepository {
    // Banners
    fun getBanners(): Flow<List<BannerItem>>

    // Products (Modalidades + Carreras)
    fun getProducts(): Flow<List<Product>>
    fun getProductsByCategory(category: String): Flow<List<Product>>
    fun searchProducts(query: String): Flow<List<Product>>

    // News
    fun getNews(): Flow<List<NewsItem>>
    fun getNewsById(id: Int): NewsItem?

    // Social Networks
    fun getSocialNetworks(): Flow<List<SocialNetwork>>

    // Timeline
    fun getTimeline(): Flow<List<TimelineItemData>>

    // Locations
    fun getLocations(): Flow<List<LocationData>>

    // FAQ
    fun getFaq(): Flow<List<FaqItemData>>

    // Phone Directory
    fun getPhoneDirectory(): Flow<List<PhoneDirectoryItem>>
}

data class TimelineItemData(
    val title: String,
    val date: String,
    val description: String,
    val isCompleted: Boolean = false
)

data class LocationData(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)

data class FaqItemData(
    val question: String,
    val answer: String
)

data class PhoneDirectoryItem(
    val name: String,
    val number: String,
    val email: String? = null
)