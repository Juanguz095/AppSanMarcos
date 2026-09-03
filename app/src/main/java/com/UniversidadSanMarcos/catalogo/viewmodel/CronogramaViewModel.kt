package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepository
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepositoryImpl
import com.UniversidadSanMarcos.catalogo.data.repository.TimelineItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CronogramaViewModel(
    private val repository: CatalogoRepository = CatalogoRepositoryImpl()
) : ViewModel() {
    private val _timeline = MutableStateFlow<List<TimelineItemData>>(emptyList())
    val timeline: StateFlow<List<TimelineItemData>> = _timeline.asStateFlow()

    init {
        loadTimeline()
    }

    private fun loadTimeline() {
        viewModelScope.launch {
            repository.getTimeline().collect { timelineList ->
                _timeline.value = timelineList
            }
        }
    }
}
