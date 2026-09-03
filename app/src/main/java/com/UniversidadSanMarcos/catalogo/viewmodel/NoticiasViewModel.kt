package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UniversidadSanMarcos.catalogo.data.model.NewsItem
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepository
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoticiasViewModel(
    private val repository: CatalogoRepository = CatalogoRepositoryImpl()
) : ViewModel() {
    private val _news = MutableStateFlow<List<NewsItem>>(emptyList())
    val news: StateFlow<List<NewsItem>> = _news.asStateFlow()

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            repository.getNews().collect { newsList ->
                _news.value = newsList
            }
        }
    }

    fun getNewsById(id: Int): NewsItem? {
        return repository.getNewsById(id)
    }
}
