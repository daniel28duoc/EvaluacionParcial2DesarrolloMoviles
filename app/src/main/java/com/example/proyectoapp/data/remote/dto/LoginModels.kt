package com.example.proyectoapp.data.remote.dto

data class LoginRequest(
    val username: String, // o email, según tu backend
    val password: String
)

data class JwtResponse(
    val token: String
)