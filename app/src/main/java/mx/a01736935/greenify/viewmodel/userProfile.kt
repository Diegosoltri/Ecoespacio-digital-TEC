package mx.a01736935.greenify.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import mx.a01736935.greenify.MainActivity
import java.io.File
import java.io.FileOutputStream
import mx.a01736935.greenify.presentation.UserId
import mx.a01736935.greenify.presentation.getPhraseFromFirestore
import mx.a01736935.greenify.presentation.getUserNameFromFirestore
import mx.a01736935.greenify.presentation.savePhraseToFirestore
import mx.a01736935.greenify.presentation.saveUserNameToFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(navController: NavController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val userIdInstance = UserId()
    val userId = userIdInstance.getUserId()

    var phrase by remember { mutableStateOf("Frase por defecto...") }
    var userName by remember { mutableStateOf("Cargando...") }

    // Cargar datos de Firestore
    LaunchedEffect(userId) {
        if (userId != null) {
            getUserNameFromFirestore(userId) { name ->
                userName = name ?: "Usuario sin nombre"
            }
            getPhraseFromFirestore(userId) { storedPhrase ->
                phrase = storedPhrase ?: "Frase por defecto..."
            }
        }
    }

    // Mostrar diálogo para editar nombre de usuario y frase
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            content = {
                var newUserName by remember { mutableStateOf("") }
                var newPhrase by remember { mutableStateOf(phrase) }

                Column {
                    Text("Ingresa tu nuevo nombre")
                    TextField(
                        value = newUserName,
                        onValueChange = { newUserName = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Ingresa una nueva frase")
                    TextField(
                        value = newPhrase,
                        onValueChange = { newPhrase = it }
                    )

                    Button(onClick = {
                        if (newUserName.isNotEmpty() && newPhrase.isNotEmpty()) {
                            userName = newUserName
                            phrase = newPhrase
                            userId?.let {
                                saveUserNameToFirestore(it, newUserName)
                                savePhraseToFirestore(it, newPhrase)  // Guardar la nueva frase en Firestore
                            }
                            showDialog = false
                        }
                    }) {
                        Text("Actualizar")
                    }
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Settings, contentDescription = "Ajustes")
            }
            IconButton(onClick = {
                shareScreenshot(context)
            }) {
                Icon(Icons.Filled.Share, contentDescription = "Compartir")
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberImagePainter("https://example.com/profile_picture.jpg"),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = userName, fontSize = 30.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Estrella",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Yellow
                )
                Text(text = "120", fontSize = 18.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(50.dp))
            Text(text = phrase, fontSize = 20.sp, color = Color.Gray)

            // Botones debajo de la frase
            Spacer(modifier = Modifier.height(32.dp)) // Espaciado entre la frase y los botones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Espaciado uniforme entre botones
            ) {
                Button(
                    onClick = {
                        // Acción del primer botón
                    },
                    modifier = Modifier.weight(1f), // Ambos botones tendrán el mismo ancho
                ) {
                    Text(
                        "Conoce sobre la guía ciudadana...",
                        maxLines = 1 // Limitar el texto a una sola línea
                    )
                }
                Spacer(modifier = Modifier.width(16.dp)) // Separación fija entre los botones
                Button(
                    onClick = {
                        // Acción del segundo botón
                    },
                    modifier = Modifier.weight(1f), // Ambos botones tendrán el mismo ancho
                ) {
                    Text(
                        "¿Qué es EcoEspacio?",
                        maxLines = 1 // Limitar el texto a una sola línea
                    )
                }
            }
        }
    }
}

// Función para compartir captura de pantalla
fun shareScreenshot(context: Context) {
    val rootView = (context as MainActivity).window.decorView.rootView
    val bitmap = Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    rootView.draw(canvas)

    val file = File(context.cacheDir, "screenshot.png")
    FileOutputStream(file).use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }

    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/png"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Compartir imagen"))
}

