package mx.a01736935.greenify

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import mx.a01736935.greenify.data.DataSource
import mx.a01736935.greenify.model.BadgeItem

@Composable
fun CameraView(navController: NavController, showBottomBar: Boolean = true) {
    val context = LocalContext.current
    val scannedValue = remember { mutableStateOf("") }
    val showConfirmationIcon = remember { mutableStateOf(false) }

    // Lanzador para ZXing
    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
        } else {
            scannedValue.value = result.contents
            validateScannedValueFirebase(scannedValue.value, context, showConfirmationIcon)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB8F168),
                contentColor = Color.Black
            ),
            onClick = {
                scannerLauncher.launch(
                    ScanOptions()
                        .setPrompt("Scan QR Code")
                        .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                )
            }
        ) {
            Text("Scan QR Code")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (scannedValue.value.isEmpty()) "Scanned value will appear here" else "Scanned Value: ${scannedValue.value}",
            color = Color.Green,
            fontSize = 16.sp
        )

        if (showConfirmationIcon.value) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Validación exitosa",
                tint = Color.Green,
                modifier = Modifier
                    .size(120.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}


fun validateScannedValueFirebase(
    scannedValue: String,
    context: Context,
    showConfirmationIcon: MutableState<Boolean>
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    if (userId == null) {
        Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        return
    }

    val userLogrosRef = db.collection("usuarios").document(userId).collection("logros")

    // Buscar el logro con el título que coincida con el valor escaneado
    userLogrosRef
        .whereEqualTo("titulo", scannedValue)
        .get()
        .addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                // Solo tomamos el primer documento (se asume que los títulos son únicos)
                val document = documents.first()
                val estrellasActuales = document.getLong("estrellasActuales") ?: 0
                val estrellasRequeridas = document.getLong("estrellasRequeridas") ?: 0
                val logroId = document.id

                if (estrellasActuales < estrellasRequeridas) {
                    val nuevasEstrellas = estrellasActuales + 10

                    // Actualizar las estrellas en la base de datos
                    userLogrosRef.document(logroId)
                        .update("estrellasActuales", nuevasEstrellas)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Progreso actualizado: $nuevasEstrellas/$estrellasRequeridas", Toast.LENGTH_SHORT).show()

                            // Mostrar ícono de confirmación por 2 segundos
                            showConfirmationIcon.value = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                showConfirmationIcon.value = false
                            }, 2000)
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error al actualizar el logro", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "Reto ya completado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "El QR no coincide con ningún reto", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error al buscar el logro", Toast.LENGTH_SHORT).show()
        }
}