package com.example.proyectoapp.ui.screens.contact

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContactScreen(viewModel: ContactViewModel) {
    val state by viewModel.uiState.collectAsState()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Vinculación CORPALIV / Alba Lab",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text("Nombre o Representante") },
            isError = state.nameError != null,
            trailingIcon = {
                if (state.nameError != null) {
                    Icon(Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                }
            },
            supportingText = { state.nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Correo Electrónico") },
            isError = state.emailError != null,
            supportingText = { state.emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.company,
            onValueChange = { viewModel.onCompanyChanged(it) },
            label = { Text("Empresa o Institución") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (selectedImageUri == null) "Adjuntar Logo Empresa (Nativo)" else "Imagen Adjuntada ✔")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.submitForm() },
            enabled = state.isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Solicitud de Alianza")
        }

        AnimatedVisibility(visible = state.isSavedSuccess) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "¡Solicitud guardada en base de datos local!",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}