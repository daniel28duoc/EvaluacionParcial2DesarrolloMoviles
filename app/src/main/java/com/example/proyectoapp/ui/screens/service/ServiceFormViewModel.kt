
package com.example.proyectoapp.ui.screens.service
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ServiceFormViewModel : ViewModel() {

    // Estados de los campos del formulario
    private val _clientName = MutableStateFlow("")
    val clientName: StateFlow<String> = _clientName.asStateFlow()

    private val _quantity = MutableStateFlow("")
    val quantity: StateFlow<String> = _quantity.asStateFlow()

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    private val _isExpressService = MutableStateFlow(false)
    val isExpressService: StateFlow<Boolean> = _isExpressService.asStateFlow()

    private val _acceptTerms = MutableStateFlow(false)
    val acceptTerms: StateFlow<Boolean> = _acceptTerms.asStateFlow()

    // Estados de error para validación visual
    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    private val _quantityError = MutableStateFlow<String?>(null)
    val quantityError: StateFlow<String?> = _quantityError.asStateFlow()

    private val _formValid = MutableStateFlow(false)
    val formValid: StateFlow<Boolean> = _formValid.asStateFlow()

    fun onNameChanged(newName: String) {
        _clientName.value = newName
        _nameError.value = if (newName.isBlank()) "El nombre del cliente no puede estar vacío" else null
        validateForm()
    }

    fun onQuantityChanged(newQty: String) {
        _quantity.value = newQty
        val intVal = newQty.toIntOrNull()
        _quantityError.value = when {
            newQty.isBlank() -> "La cantidad es obligatoria"
            intVal == null || intVal <= 0 -> "Debe ser un número entero mayor a 0"
            else -> null
        }
        validateForm()
    }

    fun onDateChanged(newDate: String) {
        _selectedDate.value = newDate
        validateForm()
    }

    fun onExpressChanged(isExpress: Boolean) {
        _isExpressService.value = isExpress
    }

    fun onTermsChanged(accepted: Boolean) {
        _acceptTerms.value = accepted
        validateForm()
    }

    private fun validateForm() {
        _formValid.value = _clientName.value.isNotBlank() &&
                _nameError.value == null &&
                _quantity.value.toIntOrNull() != null &&
                _quantityError.value == null &&
                _selectedDate.value.isNotBlank() &&
                _acceptTerms.value
    }
}