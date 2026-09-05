package com.UniversidadSanMarcos.catalogo.ui.screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.UniversidadSanMarcos.catalogo.R
import com.UniversidadSanMarcos.catalogo.viewmodel.ThemeViewModel

@Composable
fun ConfiguracionScreen(themeViewModel: ThemeViewModel = viewModel()) {
    val isDarkThemePref by themeViewModel.isDarkTheme.collectAsState()
    val systemInDark = isSystemInDarkTheme()
    // Si la preferencia no ha sido cambiada manualmente, refleja el estado del sistema
    val isChecked = isDarkThemePref ?: systemInDark

    var showLanguageDialog by remember { mutableStateOf(false) }

    // Obtener idioma actual configurado en la app o por defecto del sistema
    val currentLocales = AppCompatDelegate.getApplicationLocales()
    val currentLangTag = if (!currentLocales.isEmpty) {
        currentLocales.get(0)?.language ?: "es"
    } else {
        java.util.Locale.getDefault().language
    }

    val currentLangName = if (currentLangTag.startsWith("en", ignoreCase = true)) {
        stringResource(R.string.lang_english)
    } else {
        stringResource(R.string.lang_spanish)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        SettingsItem(
            title = stringResource(R.string.settings_dark_mode),
            icon = Icons.Default.Brightness4,
            onClick = { themeViewModel.setDarkTheme(!isChecked) }
        ) {
            Switch(
                checked = isChecked,
                onCheckedChange = { themeViewModel.setDarkTheme(it) }
            )
        }

        HorizontalDivider()

        SettingsItem(
            title = stringResource(R.string.settings_language),
            icon = Icons.Default.Language,
            onClick = { showLanguageDialog = true }
        ) {
            Text(
                text = currentLangName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text(text = stringResource(R.string.dialog_select_language))
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags("es")
                                )
                                showLanguageDialog = false
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !currentLangTag.startsWith("en", ignoreCase = true),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags("es")
                                )
                                showLanguageDialog = false
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.lang_spanish))
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags("en")
                                )
                                showLanguageDialog = false
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentLangTag.startsWith("en", ignoreCase = true),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags("en")
                                )
                                showLanguageDialog = false
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.lang_english))
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(stringResource(id = R.string.btn_cerrar))
                }
            }
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: (() -> Unit)? = null,
    control: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
        control()
    }
}
