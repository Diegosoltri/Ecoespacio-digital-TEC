package mx.a01736935.greenify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mx.a01736935.greenify.authentification.AuthenticationManager

/* navController: NavController,*/
@Composable
fun CreateAccountView(
    onRegisterClick: (email: String, password: String) -> Unit,
    onGoogleSignInClick: () -> Unit // Agregar un callback para el botón de Google
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
                .height(50.dp)
                .padding(bottom = 16.dp)
        )

        // Botón para iniciar sesión con Google
        Button(
            onClick = { onGoogleSignInClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),

        ) {
            Image(
                painter = logoGoogle,
                contentDescription = "Iniciar sesión con Google",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Iniciar sesión con Google", color = Color.Black)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCreateAccountView() {
    CreateAccountView(
        onRegisterClick = { email, password ->
            // Acción de prueba para registro
        },
        onGoogleSignInClick = {
            // Acción de prueba para inicio de sesión con Google
        }
    )
}