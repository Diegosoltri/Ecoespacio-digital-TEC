package mx.a01736935.greenify

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import mx.a01736935.greenify.model.BadgeItem

@Composable
fun CameraView(navController: NavController, badgeList: List<BadgeItem>) {
    val context = LocalContext.current
    val scannedValue = remember { mutableStateOf("") }
    val showConfirmationIcon = remember { mutableStateOf(false) }

    // Lanzador para ZXing
    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            scannedValue.value = result.contents
            validateScannedValue(scannedValue.value, badgeList, context, showConfirmationIcon)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            scannerLauncher.launch(
                ScanOptions()
                    .setPrompt("Scan QR Code")
                    .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            )
        }) {
            Text("Scan QR Code")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (scannedValue.value.isEmpty()) "Scanned value will appear here" else "Scanned Value: ${scannedValue.value}",
            color = Color.Black,
            fontSize = 16.sp
        )
        if (showConfirmationIcon.value) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Validación exitosa",
                tint = Color.Green,
                modifier = Modifier
                    .size(120.dp).align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

fun validateScannedValue(
    scannedValue: String,
    badgeList: List<BadgeItem>,
    context: Context,
    showConfirmationIcon: MutableState<Boolean>
) {
    // Buscar coincidencia en la lista de insignias
    val matchingBadge = badgeList.find { badge ->
        context.getString(badge.nameResId) == scannedValue
    }

    if (matchingBadge != null) {
        // Actualizar el progreso de la insignia
        val currentProgress = context.resources.getInteger(matchingBadge.progressResId)
        val maxProgress = context.resources.getInteger(matchingBadge.maxProgressResId)

        if (currentProgress < maxProgress) {
            val updatedProgress = currentProgress + 10

            Toast.makeText(context, "Progreso actualizado: $updatedProgress/$maxProgress", Toast.LENGTH_SHORT).show()

            // Mostrar ícono de validación por 2 segundos
            showConfirmationIcon.value = true
            Handler(Looper.getMainLooper()).postDelayed({
                showConfirmationIcon.value = false
            }, 1000)
        } else {
            Toast.makeText(context, "Reto ya completado", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "El QR no coincide con ningún reto", Toast.LENGTH_SHORT).show()
    }
}
