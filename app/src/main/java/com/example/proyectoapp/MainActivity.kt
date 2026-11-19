package com.example.proyectoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.proyectoapp.data.local.AppDatabase
import com.example.proyectoapp.data.repository.ContactRepository
import com.example.proyectoapp.ui.navigation.AppNavigation
import com.example.proyectoapp.ui.screens.contact.ContactViewModel
import com.example.proyectoapp.ui.screens.login.LoginViewModel
import com.example.proyectoapp.ui.theme.ProyectoappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(applicationContext)
        val repository = ContactRepository(db.contactDao())
        val contactViewModel = ContactViewModel(repository)
        val loginViewModel = LoginViewModel()

        setContent {
            ProyectoappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Si tu AppNavigation recibe los ViewModels o maneja rutas,
                    // puedes pasarlos aquí según tu estructura de navegación.
                    AppNavigation(
                        contactViewModel = contactViewModel
                        // loginViewModel = loginViewModel // Descomenta si AppNavigation lo requiere
                    )
                }
            }
        }
    }
}