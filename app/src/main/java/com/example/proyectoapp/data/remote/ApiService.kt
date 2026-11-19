package com.example.proyectoapp.data.remote

import com.example.proyectoapp.data.remote.dto.ExternalPostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Modelos de transferencia de datos (DTOs) para la autenticación
data class LoginRequest(
    val username: String,
    val password: String
)

data class JwtResponse(
    val token: String
)

// Modelos de transferencia de datos (DTOs) para la comunicación con el backend
data class SolicitudColaboracionDto(
    val nombre: String,
    val correo: String,
    val telefono: String,
    val tipoSolicitud: String,
    val mensaje: String,
    val aceptaPrivacidad: Boolean
)

data class ProductoServicioDto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precioReferencial: Double,
    val imagenUrl: String?
)

interface ApiService {

    // Endpoint de autenticación para el inicio de sesión
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<JwtResponse>

    // Obtener catálogo de productos o servicios desde el microservicio correspondiente
    @GET("api/productos")
    suspend fun getProductos(): Response<List<ProductoServicioDto>>

    // Enviar formulario de solicitud de colaboración / contacto al backend
    @POST("api/solicitudes")
    suspend fun enviarSolicitud(@Body solicitud: SolicitudColaboracionDto): Response<Void>

    // Consumo de API externa (URL completa para no alterar la baseUrl del API Gateway)
    @GET("https://jsonplaceholder.typicode.com/posts?_limit=5")
    suspend fun getNovedadesExternas(): Response<List<ExternalPostDto>>
}