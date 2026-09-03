package com.UniversidadSanMarcos.catalogo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UniversidadSanMarcos.catalogo.R
import com.UniversidadSanMarcos.catalogo.viewmodel.RegistroUiState
import com.UniversidadSanMarcos.catalogo.viewmodel.RegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    modalidadInicial: String = "",
    onBackClick: (() -> Unit)? = null,
    viewModel: RegistroViewModel = viewModel()
) {
    LaunchedEffect(modalidadInicial) {
        if (modalidadInicial.isNotEmpty()) {
            viewModel.inicializarModalidad(modalidadInicial)
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val dni by viewModel.dni.collectAsState()
    val email by viewModel.email.collectAsState()
    val modalidad by viewModel.modalidad.collectAsState()
    val carreraSeleccionada by viewModel.carreraSeleccionada.collectAsState()

    var expandedModalidad by remember { mutableStateOf(false) }
    var expandedCarrera by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (onBackClick != null) {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.registro_title)) },
                    navigationIcon = {
                        IconButton(onClick = { onBackClick() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (onBackClick == null) {
                Text(
                    text = stringResource(id = R.string.registro_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text(stringResource(id = R.string.lbl_nombre)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = dni,
                onValueChange = { viewModel.onDniChange(it) },
                label = { Text(stringResource(id = R.string.lbl_dni)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text(stringResource(id = R.string.lbl_email)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            // Selector de Modalidad de Admisión
            ExposedDropdownMenuBox(
                expanded = expandedModalidad,
                onExpandedChange = { expandedModalidad = !expandedModalidad },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = modalidad,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Modalidad de Admisión") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedModalidad) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedModalidad,
                    onDismissRequest = { expandedModalidad = false }
                ) {
                    viewModel.modalidadesDisponibles.forEach { mod ->
                        DropdownMenuItem(
                            text = { Text(text = mod) },
                            onClick = {
                                viewModel.onModalidadChange(mod)
                                expandedModalidad = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            // Selector de Carrera a la que postula
            ExposedDropdownMenuBox(
                expanded = expandedCarrera,
                onExpandedChange = { expandedCarrera = !expandedCarrera },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = carreraSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Carrera a Postular") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCarrera) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedCarrera,
                    onDismissRequest = { expandedCarrera = false }
                ) {
                    viewModel.carrerasDisponibles.forEach { carrera ->
                        DropdownMenuItem(
                            text = { Text(text = carrera) },
                            onClick = {
                                viewModel.onCarreraChange(carrera)
                                expandedCarrera = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState is RegistroUiState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { viewModel.enviarRegistro() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.HowToReg, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(id = R.string.btn_enviar_registro))
                }
            }

            when (val state = uiState) {
                is RegistroUiState.Success -> {
                    AlertDialog(
                        onDismissRequest = {
                            viewModel.resetState()
                            onBackClick?.invoke()
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.resetState()
                                onBackClick?.invoke()
                            }) {
                                Text("OK")
                            }
                        },
                        title = { Text("¡Inscripción Exitosa!") },
                        text = { Text(stringResource(id = R.string.msg_registro_exitoso)) }
                    )
                }

                is RegistroUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                else -> {}
            }
        }
    }
}
