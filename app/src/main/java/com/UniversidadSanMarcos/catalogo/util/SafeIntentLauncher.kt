package com.UniversidadSanMarcos.catalogo.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object SafeIntentLauncher {

    fun launchBrowser(context: Context, url: String) {
        val uri = Uri.parse(url)
        if (uri.scheme != "https") {
            Toast.makeText(context, "URL insegura bloqueada", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_VIEW, uri)
        safeStartActivity(context, intent)
    }

    fun launchDialer(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        safeStartActivity(context, intent)
    }

    fun launchEmail(context: Context, email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        safeStartActivity(context, intent)
    }

    fun launchMaps(context: Context, latitude: Double, longitude: Double, label: String) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=${Uri.encode(label)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        
        // Try Google Maps first
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            // Fallback to generic map handler
            val genericIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            safeStartActivity(context, genericIntent)
        }
    }

    private fun safeStartActivity(context: Context, intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "No se encontró una aplicación para realizar esta acción", Toast.LENGTH_SHORT).show()
        }
    }
}
