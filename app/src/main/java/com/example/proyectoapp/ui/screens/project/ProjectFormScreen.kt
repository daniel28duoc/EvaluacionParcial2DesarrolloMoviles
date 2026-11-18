package com.example.proyectoapp.ui.screens.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectFormScreen(onNavigateBack: () -> Unit) {
    var projectName by remember { mutableStateOf("") }
    var budgetText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    // Validaciones en lógica separada
    val projectError = if (projectName.isBlank()) "El nombre del proyecto es obligatorio" else null
    val budgetError = when {
        budgetText.isBlank() -> "Ingresa un presupuesto estimado"
        budgetText.toIntOrNull() == null || budgetText.toInt() <= 0 -> "Debe ser un monto mayor a 0"
        else -> null
    }
    val descError = if (description.length < 10) "Mínimo 10 caracteres" else null
    val isFormValid = projectError == null && budgetError == null && descError == null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("2. Propuesta de Proyecto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Detalles del Desafío Tecnológico",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = projectName,
                onValueChange = { projectName = it; isSubmitted = false },
                label = { Text("Nombre del Proyecto") },
                isError = projectError != null,
                trailingIcon = {
                    if (projectError != null) Icon(Icons.Default.Warning, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = { projectError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = budgetText,
                onValueChange = { budgetText = it.filter { c -> c.isDigit() }; isSubmitted = false },
                label = { Text("Presupuesto Estimado (CLP)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = budgetError != null,
                trailingIcon = {
                    if (budgetError != null) Icon(Icons.Default.Warning, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = { budgetError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it; isSubmitted = false },
                label = { Text("Descripción del Problema") },
                isError = descError != null,
                supportingText = { descError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { isSubmitted = true },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Propuesta")
            }

            AnimatedVisibility(visible = isSubmitted) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Propuesta '$projectName' registrada correctamente.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}