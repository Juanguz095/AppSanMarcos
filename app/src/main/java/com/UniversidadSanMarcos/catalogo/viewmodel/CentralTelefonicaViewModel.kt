package com.UniversidadSanMarcos.catalogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepository
import com.UniversidadSanMarcos.catalogo.data.repository.CatalogoRepositoryImpl
import com.UniversidadSanMarcos.catalogo.data.repository.PhoneDirectoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CentralTelefonicaViewModel(
    private val repository: CatalogoRepository = CatalogoRepositoryImpl()
) : ViewModel() {
    private val _contacts = MutableStateFlow<List<PhoneDirectoryItem>>(emptyList())
    val contacts: StateFlow<List<PhoneDirectoryItem>> = _contacts.asStateFlow()

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            repository.getPhoneDirectory().collect { contactsList ->
                _contacts.value = contactsList
            }
        }
    }
}
