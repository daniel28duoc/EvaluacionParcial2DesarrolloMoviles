package com.example.proyectoapp.repository

import com.example.proyectoapp.data.remote.ApiService
import com.example.proyectoapp.data.remote.ProductoServicioDto
import com.example.proyectoapp.data.remote.SolicitudColaboracionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ColaboracionRepository(private val apiService: ApiService) {

    // Obtener productos desde el backend usando corrutinas en hilo de red (IO)
    suspend fun obtenerProductos(): Result<List<ProductoServicioDto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProductos()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error al obtener productos: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Enviar el formulario de solicitud al backend
    suspend fun enviarSolicitud(solicitud: SolicitudColaboracionDto): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.enviarSolicitud(solicitud)
                if (response.isSuccessful) {
                    Result.success(true)
                } else {
                    Result.failure(Exception("Error al enviar solicitud: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}