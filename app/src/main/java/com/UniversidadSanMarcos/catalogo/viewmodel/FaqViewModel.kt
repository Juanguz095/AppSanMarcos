package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepository
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepositoryImpl
import com.UniversidadSanMarcos.catalogo.data.repository.FaqItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FaqItem(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)

class FaqViewModel(
    private val repository: CatalogoRepository = CatalogoRepositoryImpl()
) : ViewModel() {
    private val _faqs = MutableStateFlow<List<FaqItem>>(emptyList())
    val faqs: StateFlow<List<FaqItem>> = _faqs.asStateFlow()

    init {
        loadFaqs()
    }

    private fun loadFaqs() {
        viewModelScope.launch {
            repository.getFaq().collect { faqList ->
                _faqs.value = faqList.map { FaqItem(it.question, it.answer) }
            }
        }
    }

    fun toggleFaq(index: Int) {
        val currentList = _faqs.value.toMutableList()
        val item = currentList[index]
        currentList[index] = item.copy(isExpanded = !item.isExpanded)
        _faqs.value = currentList
    }
}
