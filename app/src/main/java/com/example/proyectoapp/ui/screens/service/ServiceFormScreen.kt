package com.example.proyectoapp.ui.screens.service

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceFormScreen(
    viewModel: ServiceFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val name by viewModel.clientName.collectAsState()
    val quantity by viewModel.quantity.collectAsState()
    val date by viewModel.selectedDate.collectAsState()
    val isExpress by viewModel.isExpressService.collectAsState()
    val terms by viewModel.acceptTerms.collectAsState()

    val nameError by viewModel.nameError.collectAsState()
    val quantityError by viewModel.quantityError.collectAsState()
    val isFormValid by viewModel.formValid.collectAsState()

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.onDateChanged("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Servicio y Pedido") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = viewModel::onNameChanged,
                label = { Text("Nombre del Cliente") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = nameError != null,
                trailingIcon = {
                    if (nameError != null) {
                        Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (nameError != null) {
                Text(text = nameError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            OutlinedTextField(
                value = quantity,
                onValueChange = viewModel::onQuantityChanged,
                label = { Text("Cantidad de Unidades") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = quantityError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (quantityError != null) {
                Text(text = quantityError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            OutlinedButton(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = if (date.isEmpty()) "Seleccionar Fecha de Entrega" else "Fecha: $date")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿Servicio Express / Prioritario?")
                Switch(checked = isExpress, onCheckedChange = viewModel::onExpressChanged)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = terms, onCheckedChange = viewModel::onTermsChanged)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Acepto las condiciones y términos del servicio")
            }

            Button(
                onClick = { onNavigateBack() },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar y Registrar Pedido")
            }
        }
    }
}