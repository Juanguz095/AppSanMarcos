package com.UniversidadSanMarcos.catalogo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UniversidadSanMarcos.catalogo.R
import com.UniversidadSanMarcos.catalogo.data.repository.PhoneDirectoryItem
import com.UniversidadSanMarcos.catalogo.util.SafeIntentLauncher
import com.UniversidadSanMarcos.catalogo.viewmodel.CentralTelefonicaViewModel

@Composable
fun CentralTelefonicaScreen(viewModel: CentralTelefonicaViewModel = viewModel()) {
    val contacts by viewModel.contacts.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Directorio Telefónico",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val url = "https://api.whatsapp.com/send?phone=51900000000&text=Hola,%20tengo%20una%20consulta%20sobre%20el%20proceso%20de%20admisión"
                SafeIntentLauncher.launchBrowser(context, url)
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Whatsapp, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(id = R.string.btn_whatsapp), color = Color.White, fontWeight = FontWeight.Bold)
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(contacts, key = { it.name }) { contact ->
                ContactItem(
                    contact = contact,
                    onPhoneClick = { SafeIntentLauncher.launchDialer(context, contact.number) },
                    onEmailClick = { email -> SafeIntentLauncher.launchEmail(context, email) }
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: PhoneDirectoryItem,
    onPhoneClick: () -> Unit,
    onEmailClick: (String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = contact.number,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                contact.email?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            Row {
                contact.email?.let { email ->
                    IconButton(
                        onClick = { onEmailClick(email) },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Enviar Correo",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                IconButton(
                    onClick = onPhoneClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Llamar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
