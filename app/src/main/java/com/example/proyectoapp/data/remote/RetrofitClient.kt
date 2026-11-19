package com.example.proyectoapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // URL base para el emulador de Android apuntando a tu API Gateway (puerto 9090)
    private const val BASE_URL = "http://10.0.2.2:9090/"

    // Variable global temporal para almacenar el token JWT tras un inicio de sesión exitoso
    var tokenJwt: String = ""

    // Cliente OkHttp configurado con un interceptor para inyectar el token Bearer
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()

            // Si hay un token guardado, lo adjuntamos en la cabecera Authorization
            if (tokenJwt.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $tokenJwt")
            }

            chain.proceed(requestBuilder.build())
        }
        .build()

    // Instancia de Retrofit configurada con la URL base y el cliente OkHttp
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia directa de ApiService para llamarla cómodamente desde tus ViewModels
    val instance: ApiService by lazy {
        retrofitInstance.create(ApiService::class.java)
    }
}