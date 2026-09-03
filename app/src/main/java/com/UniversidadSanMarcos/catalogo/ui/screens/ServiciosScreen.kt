package com.UniversidadSanMarcos.catalogo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UniversidadSanMarcos.catalogo.R
import com.UniversidadSanMarcos.catalogo.data.model.Product
import com.UniversidadSanMarcos.catalogo.viewmodel.ServiciosViewModel

@Composable
fun ServiciosScreen(
    onCarreraClick: (Int) -> Unit,
    onModalidadInscribirClick: (String) -> Unit = {},
    viewModel: ServiciosViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Modalidades de Admisión", "Carreras Profesionales")

    val modalidades = remember(products) {
        products.filter { it.category.contains("Modalidad", ignoreCase = true) }
    }

    val carreras = remember(products) {
        products.filter { it.category.contains("Carrera", ignoreCase = true) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == 0) Icons.AutoMirrored.Filled.Assignment else Icons.Default.School,
                            contentDescription = null
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AppRegistration,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Medios de Ingreso a la UNMSM",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Text(
                                        text = "Selecciona la modalidad para ver requisitos e inscribirte al examen.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }

                    items(modalidades, key = { it.id }) { modalidad ->
                        ModalidadCard(
                            modalidad = modalidad,
                            onClick = { onCarreraClick(modalidad.id) },
                            onInscribirClick = { onModalidadInscribirClick(modalidad.name) }
                        )
                    }
                }
            }

            1 -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        placeholder = { Text("Buscar carrera (Medicina, Sistemas, Derecho...)") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(carreras, key = { it.id }) { carrera ->
                            CarreraCard(
                                carrera = carrera,
                                onClick = { onCarreraClick(carrera.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModalidadCard(
    modalidad: Product,
    onClick: () -> Unit,
    onInscribirClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = modalidad.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (modalidad.price > 0) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "S/ ${modalidad.price.toInt()}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = modalidad.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onClick,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Ver detalles",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(14.dp))
                }

                Button(
                    onClick = onInscribirClick,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Inscribirse",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

fun getCareerIcon(careerName: String): ImageVector {
    return when {
        careerName.contains("Medicina", ignoreCase = true) -> Icons.Default.MedicalServices
        careerName.contains("Sistemas", ignoreCase = true) -> Icons.Default.Computer
        careerName.contains("Derecho", ignoreCase = true) -> Icons.Default.Gavel
        careerName.contains("Administración", ignoreCase = true) || careerName.contains(
            "Administracion",
            ignoreCase = true
        ) -> Icons.Default.BusinessCenter

        careerName.contains("Contabilidad", ignoreCase = true) -> Icons.Default.AccountBalance
        careerName.contains("Psicología", ignoreCase = true) || careerName.contains(
            "Psicologia",
            ignoreCase = true
        ) -> Icons.Default.Psychology

        careerName.contains("Economía", ignoreCase = true) || careerName.contains(
            "Economia",
            ignoreCase = true
        ) -> Icons.Default.TrendingUp

        else -> Icons.AutoMirrored.Filled.MenuBook
    }
}

fun getCareerGradient(careerName: String): List<Color> {
    return when {
        careerName.contains("Medicina", ignoreCase = true) -> listOf(Color(0xFFE0F2FE), Color(0xFFBAE6FD))
        careerName.contains("Sistemas", ignoreCase = true) -> listOf(Color(0xFFEDE9FE), Color(0xFFDDD6FE))
        careerName.contains("Derecho", ignoreCase = true) -> listOf(Color(0xFFFEF3C7), Color(0xFFFDE68A))
        careerName.contains("Administración", ignoreCase = true) -> listOf(Color(0xFFDCFCE7), Color(0xFFBBF7D0))
        careerName.contains("Contabilidad", ignoreCase = true) -> listOf(Color(0xFFFFEDD5), Color(0xFFFED7AA))
        careerName.contains("Psicología", ignoreCase = true) -> listOf(Color(0xFFFCE7F3), Color(0xFFFBCFE8))
        careerName.contains("Economía", ignoreCase = true) -> listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1))
        else -> listOf(Color(0xFFE6F0FF), Color(0xFFCCE0FF))
    }
}

@Composable
fun CarreraCard(
    carrera: Product,
    onClick: () -> Unit
) {
    val icon = getCareerIcon(carrera.name)
    val gradientColors = getCareerGradient(carrera.name)

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Brush.linearGradient(gradientColors)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(46.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = carrera.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (carrera.duration.isNotEmpty()) {
                    Text(
                        text = "Duración: ${carrera.duration}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = carrera.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ver perfil académico →",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
