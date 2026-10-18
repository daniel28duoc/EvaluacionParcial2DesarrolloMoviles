package com.example.proyectoapp.ui.screens.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoapp.data.local.ContactEntity
import com.example.proyectoapp.data.repository.ContactRepository
import com.example.proyectoapp.domain.usecase.ValidateEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ContactUiState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val company: String = "",
    val message: String = "",
    val isFormValid: Boolean = false,
    val isSavedSuccess: Boolean = false
)

class ContactViewModel(
    private val repository: ContactRepository,
    private val validateEmail: ValidateEmailUseCase = ValidateEmailUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactUiState())
    val uiState: StateFlow<ContactUiState> = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        val error = if (name.isBlank()) "El nombre no puede estar vacío" else null
        _uiState.update { it.copy(name = name, nameError = error) }
        validateForm()
    }

    fun onEmailChanged(email: String) {
        val error = validateEmail(email)
        _uiState.update { it.copy(email = email, emailError = error) }
        validateForm()
    }

    fun onCompanyChanged(company: String) {
        _uiState.update { it.copy(company = company) }
    }

    fun onMessageChanged(message: String) {
        _uiState.update { it.copy(message = message) }
    }

    private fun validateForm() {
        val s = _uiState.value
        val isValid = s.name.isNotBlank() && s.nameError == null &&
                s.email.isNotBlank() && s.emailError == null
        _uiState.update { it.copy(isFormValid = isValid) }
    }

    fun submitForm() {
        if (_uiState.value.isFormValid) {
            viewModelScope.launch {
                val s = _uiState.value
                repository.saveContact(
                    ContactEntity(
                        name = s.name,
                        email = s.email,
                        company = s.company,
                        message = s.message
                    )
                )
                _uiState.update { it.copy(isSavedSuccess = true) }
            }
        }
    }
}