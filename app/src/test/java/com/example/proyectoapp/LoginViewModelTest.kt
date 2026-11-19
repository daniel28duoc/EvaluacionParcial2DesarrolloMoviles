package com.example.proyectoapp

import com.example.proyectoapp.ui.screens.login.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LoginViewModelTest {

    @Test
    fun login_conCamposVacios_muestraMensajeError() {
        val viewModel = LoginViewModel()

        viewModel.login()

        assertEquals("Ingresa usuario y contraseña", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun onUsernameChanged_limpiaErrorPrevio() {
        val viewModel = LoginViewModel()
        viewModel.login() // genera error inicial
        assertEquals("Ingresa usuario y contraseña", viewModel.uiState.value.errorMessage)

        viewModel.onUsernameChanged("admin")

        assertEquals("admin", viewModel.uiState.value.username)
        assertNull(viewModel.uiState.value.errorMessage)
    }

    @Test
    fun onPasswordChanged_limpiaErrorPrevio() {
        val viewModel = LoginViewModel()
        viewModel.login() // genera error inicial
        assertEquals("Ingresa usuario y contraseña", viewModel.uiState.value.errorMessage)

        viewModel.onPasswordChanged("1234")

        assertEquals("1234", viewModel.uiState.value.password)
        assertNull(viewModel.uiState.value.errorMessage)
    }
}