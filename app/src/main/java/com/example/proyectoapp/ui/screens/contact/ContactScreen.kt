package com.example.proyectoapp.ui.screens.contact

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyectoapp.util.makePhoneCall
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    viewModel: ContactViewModel,
    onNavigateToProjectForm: () -> Unit,
    onNavigateToServiceForm: () -> Unit,
    onNavigateToNovedadesExternas: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        viewModel.onDateChanged(formatter.format(Date(millis)))
                    }
                    showDatePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("1. Vinculación CORPALIV / Alba Lab") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al Inicio"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Campo String: Nombre
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { Text("Nombre o Representante") },
                isError = state.nameError != null,
                trailingIcon = {
                    if (state.nameError != null) Icon(Icons.Default.Warning, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = { state.nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Email
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Correo Electrónico") },
                isError = state.emailError != null,
                trailingIcon = {
                    if (state.emailError != null) Icon(Icons.Default.Warning, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = { state.emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Numérico (Int): Teléfono
            OutlinedTextField(
                value = state.phone,
                onValueChange = { viewModel.onPhoneChanged(it) },
                label = { Text("Teléfono de Contacto (Ej: 912345678)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.phoneError != null,
                trailingIcon = {
                    if (state.phoneError != null) Icon(Icons.Default.Warning, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = { state.phoneError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo String: Empresa
            OutlinedTextField(
                value = state.company,
                onValueChange = { viewModel.onCompanyChanged(it) },
                label = { Text("Empresa o Institución") },
                isError = state.companyError != null,
                trailingIcon = {
                    if (state.companyError != null) Icon(Icons.Default.Warning, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = { state.companyError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Date: Fecha preferida de reunión
            OutlinedTextField(
                value = state.preferredDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha Preferida de Reunión") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                    }
                },
                isError = state.dateError != null,
                supportingText = { state.dateError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            // Control Switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Requiere reunión presencial en Alba Lab",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = state.isInPersonMeeting,
                    onCheckedChange = { viewModel.onInPersonMeetingChanged(it) }
                )
            }

            // Recurso Nativo 1: Galería
            OutlinedButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedImageUri == null) "Adjuntar Logo Empresa (Nativo)" else "Imagen Adjuntada ✔")
            }

            // Control Checkbox obligatorio
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = state.termsAccepted,
                    onCheckedChange = { viewModel.onTermsChanged(it) }
                )
                Text(
                    text = "Acepto los términos y políticas de vinculación",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.submitForm() },
                enabled = state.isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar Solicitud de Alianza")
            }

            // Animación de confirmación
            AnimatedVisibility(
                visible = state.isSavedSuccess,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .animateContentSize()
                ) {
                    Text(
                        text = "¡Solicitud guardada localmente con éxito!",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para navegar al Formulario de Proyectos
            FilledTonalButton(
                onClick = onNavigateToProjectForm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Formulario: Registrar Proyecto →")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para navegar al Formulario de Servicio
            OutlinedButton(
                onClick = onNavigateToServiceForm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Formulario de Servicios y Pedidos →")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para navegar a Novedades Externas (API Externa)
            OutlinedButton(
                onClick = onNavigateToNovedadesExternas,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Novedades y Difusión (API Externa) →")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Recurso Nativo 2: Marcador Telefónico (Intent.ACTION_DIAL)
            OutlinedButton(
                onClick = { makePhoneCall(context, "+56221234567") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Llamar a CORPALIV",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Llamar a Central Alba Lab (Nativo)")
            }
        }
    }
}