package mx.a01736935.greenify.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import mx.a01736935.greenify.MainActivity
import mx.a01736935.greenify.R
import mx.a01736935.greenify.presentation.UserId
import mx.a01736935.greenify.presentation.getInstagramFromFirestore
import java.io.File
import java.io.FileOutputStream
import mx.a01736935.greenify.presentation.getUserNameFromFirestore
import mx.a01736935.greenify.presentation.saveInstagramToFirestore
import mx.a01736935.greenify.presentation.saveUserNameToFirestore





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val userIdInstance = UserId()
    val userId = userIdInstance.getUserId()

    var instagramHandle by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("Cargando...") }

    LaunchedEffect(userId) {
        if (userId != null) {
            getUserNameFromFirestore(userId) { name ->
                userName = name ?: "Usuario sin nombre"
            }
            getInstagramFromFirestore(userId) { instagram ->
                instagramHandle = instagram ?: ""
            }
        }
    }

    if (showDialog) {
        BasicAlertDialog(onDismissRequest = { showDialog = false },
            content = {
                var newUserName by remember { mutableStateOf("") }
                var newInstagram by remember { mutableStateOf(instagramHandle) }

                Column {
                    Text("Ingresa tu nuevo nombre")
                    TextField(
                        value = newUserName,
                        onValueChange = { newUserName = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Ingresa tu usuario de Instagram")
                    TextField(
                        value = newInstagram,
                        onValueChange = { newInstagram = it }
                    )

                    Button(onClick = {
                        if (newUserName.isNotEmpty() && newInstagram.isNotEmpty()) {
                            userName = newUserName
                            instagramHandle = newInstagram
                            userId?.let {
                                saveUserNameToFirestore(it, newUserName)
                                saveInstagramToFirestore(it, newInstagram) // Guardar Instagram
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
                painter = rememberAsyncImagePainter("https://example.com/profile_picture.jpg"),
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
            if (instagramHandle.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.instagram__2_), // Reemplaza con tu recurso de ícono
                    contentDescription = "Instagram",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            openInstagramProfile(instagramHandle, context)
                        },
                    tint = Color.Unspecified
                )
            }
        }
    }
}

// Función para abrir el perfil de Instagram del usuario
fun openInstagramProfile(instagramHandle: String, context: Context) {
    val instagramUrl = "https://www.instagram.com/$instagramHandle/"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
    context.startActivity(intent)
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