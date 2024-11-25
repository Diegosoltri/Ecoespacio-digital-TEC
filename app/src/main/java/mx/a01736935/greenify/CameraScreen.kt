package mx.a01736935.greenify

import BottomButtonBar
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun CameraView(navController: NavHostController) {
    var selectedButton by remember { mutableStateOf("Camera") }
    val context = LocalContext.current
    val scannedValue = remember { mutableStateOf("") }
    val showConfirmationIcon = remember { mutableStateOf(false) }
    val colorGreen = 0xFF4CAF50
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
    Scaffold(
        bottomBar = {
            BottomButtonBar(
                selectedButton = selectedButton,
                onButtonSelected = { selectedButton = it },
                navController = navController // Pasar el NavController aquí
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                // Título
                Text(
                    text = "GREENIFY",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(colorGreen),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                Text(
                    text = "Escanea aqui tus misiones para obtener tus recompensas",
                    fontSize = 17.sp,
                    color = Color(colorGreen),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )


                Spacer(modifier = Modifier.height(70.dp))

                Image(
                    painter = painterResource(id = R.drawable.camara_reflex_digital),
                    contentDescription = "Camera icon",
                    modifier = Modifier.size(170.dp) // Puedes ajustar el tamaño si lo necesitas
                )

                Spacer(modifier = Modifier.height(50.dp))

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
                    text = if (scannedValue.value.isEmpty()) "El valor escaneado aparecera aqui" else "Scanned Value: ${scannedValue.value}",
                    color =Color(colorGreen),
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
    )
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
                val estrellasPorActividad = document.getLong("estrellasPorActividad") ?: 0
                val logroId = document.id

                if (estrellasActuales < estrellasRequeridas) {
                    val nuevasEstrellas = estrellasActuales + estrellasPorActividad

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