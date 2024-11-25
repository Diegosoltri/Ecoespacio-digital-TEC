package mx.a01736935.greenify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mx.a01736935.greenify.authentification.AuthenticationManager

/* navController: NavController,*/
@Composable
fun CreateAccountView(
    onRegisterClick: (email: String, password: String) -> Unit,
    onGoogleSignInClick: () -> Unit ,// Agregar un callback para el botón de Google,
    navController: NavController

) {
    val logoGreenify = painterResource(id = R.drawable.greenify)
    val logoGoogle = painterResource(id = R.drawable.google)

    val context = LocalContext.current

    val authenticationManager = remember {
        AuthenticationManager(context)
    }

    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = logoGreenify,
            contentDescription = "Greenify",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(150.dp) // Imagen más grande
                .padding(bottom = 16.dp)
        )

        // Correo
        Text(text = "Correo", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Ingrese su correo electrónico") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x8034C759), shape = MaterialTheme.shapes.small)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Contraseña
        Text(text = "Contraseña", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Ingrese su contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x8034C759), shape = MaterialTheme.shapes.small),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // Llamada para crear la cuenta
            if (email.isNotBlank() && password.isNotBlank()) {
                authenticationManager.createAccountWithEmail(email, password)
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
        }, modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))


        ){
            Text("Crear cuenta")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Ya tienes una cuenta?", fontSize = 14.sp, color = Color.Gray,
            modifier = Modifier.clickable {
            navController.navigate(Screen.Login.name)})
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "O inicia sesión con redes sociales", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(10.dp))
        // Botón para iniciar sesión con Google
        Button(
            onClick = { onGoogleSignInClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))

        ) {
            Image(
                painter = logoGoogle,
                contentDescription = "Iniciar sesión con Google",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Iniciar sesión con Google", color = Color.White)
        }
    }
}


