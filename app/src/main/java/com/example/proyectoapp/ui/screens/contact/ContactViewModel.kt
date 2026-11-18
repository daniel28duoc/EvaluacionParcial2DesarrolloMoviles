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
    val phone: String = "",
    val phoneError: String? = null,
    val company: String = "",
    val companyError: String? = null,
    val preferredDate: String = "",
    val dateError: String? = null,
    val isInPersonMeeting: Boolean = false,
    val termsAccepted: Boolean = false,
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
        val error = if (name.isBlank()) "El nombre es obligatorio" else null
        _uiState.update { it.copy(name = name, nameError = error) }
        validateForm()
    }

    fun onEmailChanged(email: String) {
        val error = validateEmail(email)
        _uiState.update { it.copy(email = email, emailError = error) }
        validateForm()
    }

    fun onPhoneChanged(phone: String) {
        // Solo permite dígitos y exige al menos 8 números (Tipo Int/Numérico)
        val filtered = phone.filter { it.isDigit() }
        val error = when {
            filtered.isBlank() -> "El teléfono es obligatorio"
            filtered.length < 8 -> "Debe tener al menos 8 dígitos"
            else -> null
        }
        _uiState.update { it.copy(phone = filtered, phoneError = error) }
        validateForm()
    }

    fun onCompanyChanged(company: String) {
        val error = if (company.isBlank()) "La empresa es obligatoria" else null
        _uiState.update { it.copy(company = company, companyError = error) }
        validateForm()
    }

    fun onDateChanged(date: String) {
        val error = if (date.isBlank()) "Selecciona una fecha estimada" else null
        _uiState.update { it.copy(preferredDate = date, dateError = error) }
        validateForm()
    }

    fun onInPersonMeetingChanged(isChecked: Boolean) {
        _uiState.update { it.copy(isInPersonMeeting = isChecked) }
    }

    fun onTermsChanged(accepted: Boolean) {
        _uiState.update { it.copy(termsAccepted = accepted) }
        validateForm()
    }

    fun onMessageChanged(message: String) {
        _uiState.update { it.copy(message = message) }
    }

    private fun validateForm() {
        val s = _uiState.value
        val isValid = s.name.isNotBlank() && s.nameError == null &&
                s.email.isNotBlank() && s.emailError == null &&
                s.phone.isNotBlank() && s.phoneError == null &&
                s.company.isNotBlank() && s.companyError == null &&
                s.preferredDate.isNotBlank() && s.dateError == null &&
                s.termsAccepted
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
                        phone = s.phone,
                        company = s.company,
                        preferredDate = s.preferredDate,
                        isInPersonMeeting = s.isInPersonMeeting,
                        message = s.message
                    )
                )
                _uiState.update { it.copy(isSavedSuccess = true) }
            }
        }
    }
}