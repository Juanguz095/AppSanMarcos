package com.UniversidadSanMarcos.catalogo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UniversidadSanMarcos.catalogo.R
import com.UniversidadSanMarcos.catalogo.data.model.BannerItem
import com.UniversidadSanMarcos.catalogo.ui.navigation.Screen
import com.UniversidadSanMarcos.catalogo.ui.theme.CatalogoTheme
import com.UniversidadSanMarcos.catalogo.viewmodel.InicioUiState
import com.UniversidadSanMarcos.catalogo.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    onNavigate: (String) -> Unit,
    viewModel: InicioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is InicioUiState.Loading -> LoadingState()
            is InicioUiState.Success -> InicioContent(
                banners = state.banners,
                onNavigate = onNavigate
            )

            is InicioUiState.Empty -> EmptyState()
            is InicioUiState.Error -> ErrorState(
                onRetry = { viewModel.loadBanners() }
            )
        }
    }
}

@Composable
fun InicioContent(
    banners: List<BannerItem>,
    onNavigate: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            WelcomeHeader()
        }

        item {
            Button(
                onClick = { onNavigate(Screen.Servicios.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AppRegistration,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Inscripción y Modalidades 2027-I",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.5.sp),
                    maxLines = 1
                )
            }
        }

        item {
            Text(
                text = stringResource(id = R.string.section_quick_access),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            QuickActions(onNavigate = onNavigate)
        }

        item {
            Text(
                text = "Comunicados y Novedades",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(banners, key = { it.id }) { banner ->
            BannerCard(banner)
        }
    }
}

@Composable
fun WelcomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                )
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.welcome_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.welcome_subtitle),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            imageVector = Icons.Default.AccountBalance,
            contentDescription = null,
            modifier = Modifier.size(52.dp),
            tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.95f)
        )
    }
}

fun getBannerIllustration(id: Int): Pair<ImageVector, List<Color>> {
    return when (id) {
        1 -> Pair(Icons.Default.Campaign, listOf(Color(0xFF003366), Color(0xFF0055A5)))
        2 -> Pair(Icons.Default.Quiz, listOf(Color(0xFF800020), Color(0xFFA61C38)))
        3 -> Pair(Icons.Default.School, listOf(Color(0xFF0F5132), Color(0xFF198754)))
        else -> Pair(Icons.AutoMirrored.Filled.FactCheck, listOf(Color(0xFFB45309), Color(0xFFD97706)))
    }
}

@Composable
fun BannerCard(banner: BannerItem) {
    val (icon, gradient) = getBannerIllustration(banner.id)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Brush.horizontalGradient(gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = Color.White.copy(alpha = 0.92f)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = banner.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = banner.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun QuickActions(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.CalendarMonth,
                title = "Cronograma",
                subtitle = "Fechas clave",
                iconTint = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                onClick = { onNavigate(Screen.Cronograma.route) }
            )

            QuickActionItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.School,
                title = "Carreras",
                subtitle = "Catálogo 2027",
                iconTint = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                onClick = { onNavigate(Screen.Servicios.route) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.LocationOn,
                title = "Sedes UNMSM",
                subtitle = "Ubicación mapa",
                iconTint = Color(0xFFC05621),
                containerColor = Color(0xFFFEEBC8).copy(alpha = 0.6f),
                onClick = { onNavigate(Screen.Localizar.route) }
            )

            QuickActionItem(
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.HelpCenter,
                title = "Ayuda y FAQ",
                subtitle = "Preguntas guía",
                iconTint = Color(0xFF2B6CB0),
                containerColor = Color(0xFFBEE3F8).copy(alpha = 0.6f),
                onClick = { onNavigate(Screen.Faq.route) }
            )
        }
    }
}

@Composable
fun QuickActionItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconTint: Color,
    containerColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(68.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = iconTint
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 13.sp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.msg_loading))
    }
}

@Composable
fun ErrorState(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.msg_error),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.btn_retry))
        }
    }
}

@Composable
fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.msg_empty))
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    CatalogoTheme {
        InicioContent(
            banners = listOf(
                BannerItem(1, "Vista Previa", "Esto es una descripción de ejemplo para el banner.")
            ),
            onNavigate = {}
        )
    }
}
