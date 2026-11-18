package com.example.proyectoapp.domain.usecase

import android.util.Patterns

class ValidateEmailUseCase {
    operator fun invoke(email: String): String? {
        if (email.isBlank()) return "El correo es obligatorio"
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Formato de correo inválido"
        }
        return null
    }
}