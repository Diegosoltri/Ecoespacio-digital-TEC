package mx.a01736935.greenify

import android.content.Intent
import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mx.a01736935.greenify.authentification.AuthenticationManager

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun LoginView(navController: NavController, modifier: Modifier = Modifier,
              onGoogleSignInClick: () -> Unit,
              showBottomBar: Boolean = false) {
    val swipeableState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, 150f to 1)
    val logo = painterResource(id = R.drawable.greenify)
    val context = LocalContext.current
    val authenticationManager = remember {
        AuthenticationManager(context)
    }
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val strongGreen = Color(0xFF00B300)
    val lightGreenCustom = Color(0xFFE0FFE0)
    val circle = painterResource(id = R.drawable.graycircle)
    val grcircle = painterResource(id = R.drawable.greencircle)
    val colorGreen = 0xFF4CAF50

    Box(modifier = modifier.fillMaxSize()
        .swipeable(state = swipeableState, anchors = anchors,
            thresholds = { _, _ -> FractionalThreshold(0.5f) },
            orientation = Orientation.Horizontal
        )
    )

    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().background(Color.White)
    ) {
        Image(painter = logo, contentDescription ="Logo",
            modifier = Modifier.width(120.dp).padding(top = 50.dp))

        Text(text = "User", color = Color.Black, textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.Start)
                .padding(start = 40.dp, top = 20.dp)
        )


        OutlinedTextField(value = email, singleLine = true, onValueChange = { email = it },
            shape = RoundedCornerShape(8.dp), placeholder = { Text("@gmail.com") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Text(text = "Password", textAlign = TextAlign.Start, color = Color.Black,
            modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.Start)
                .padding(start = 40.dp, top = 20.dp)
        )


        OutlinedTextField(value = password, onValueChange = { password = it }, singleLine = true,
            shape = RoundedCornerShape(8.dp), placeholder = { Text("*******") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                authenticationManager.loginWithEmail(email, password)
                    .onEach { response ->
                        when (response) {
                            is AuthenticationManager.AuthResponse.Success -> {
                                // Si la autenticación es exitosa, navegar a la pantalla de inicio
                                navController.navigate(Screen.Home.name) {
                                    // Evita que el usuario regrese a la pantalla de login usando el botón de "atrás"
                                    popUpTo(Screen.Login.name) { inclusive = true }
                                }
                            }
                            is AuthenticationManager.AuthResponse.Error -> {
                                // Mostrar mensaje de error
                                errorMessage = response.message
                            }
                        }
                    }
                    .launchIn(coroutineScope)
            } else {
                errorMessage = "Por favor, ingrese todos los campos."
            }
        }, shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),  // Color de fondo del botón
                contentColor = Color.White     // Color del texto del botón
            ),
            modifier = Modifier.fillMaxWidth(0.4f).padding(top = 30.dp)
                .border(2.dp, strongGreen, RoundedCornerShape(12.dp))
        ){
            Text(text = "Login", color = Color.White, fontSize = 20.sp,
                modifier = Modifier.padding(6.dp) // Espaciado interno del botón
            )
        }


        Button(onClick = {navController.navigate(Screen.Create.name)}, shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
            containerColor = lightGreenCustom, contentColor = Color.White
        ),
            modifier = Modifier.fillMaxWidth(0.9f).padding(top = 30.dp)
                .border(2.dp, strongGreen, RoundedCornerShape(12.dp))
        ){
            Text(text = "Crear nueva cuenta", color = Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(6.dp)
            )
        }

        Text(text = "Inicia sesión con otro metodo", color = Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top=20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) { IconButton(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                authenticationManager.signInWithGoogle()
                    .onEach { response ->
                        when (response) {
                            is AuthenticationManager.AuthResponse.Success -> {
                                // Si la autenticación es exitosa, navegar a la pantalla de inicio
                                navController.navigate(Screen.Home.name) {
                                    // Evita que el usuario regrese a la pantalla de login usando el botón de "atrás"
                                    popUpTo(Screen.Login.name) { inclusive = true }
                                }
                            }
                            is AuthenticationManager.AuthResponse.Error -> {
                                // Mostrar mensaje de error
                                errorMessage = response.message
                            }
                        }
                    }
                    .launchIn(coroutineScope)
            } else {
                errorMessage = "Por favor, ingrese todos los campos."
            }
        }
        ) {
            Image(painter = painterResource(R.drawable.google), contentDescription = "Login with Google",
                modifier = Modifier.size(30.dp)
            )
        }
        }

            }
        }

