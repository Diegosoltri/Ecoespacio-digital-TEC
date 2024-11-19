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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CreateAccountView(navController: NavController) {
    val logoGreenify = painterResource(id = R.drawable.greenify)
    val logoFb = painterResource(id = R.drawable.facebook)
    val logoGoogle = painterResource(id = R.drawable.google)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen superior
        Image(painter = logoGreenify, contentDescription = "Greenify",
            contentScale = ContentScale.FillWidth, modifier = Modifier.width(80.dp))
        Spacer(modifier = Modifier.height(10.dp))

        // Nombre
        Text(text = "Nombre", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Ingrese su nombre") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Correo
        Text(text = "Correo", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Ingrese su correo electrónico") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Contraseña
        Text(text = "Contraseña", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Ingrese su contraseña") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Para ocultar la contraseña
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirmar Contraseña
        Text(text = "Confirmar Contraseña", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirme su contraseña") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Botón de registro
        Button(onClick = { navController.navigate("mainMenuScreen") }, modifier = Modifier.fillMaxWidth()) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Link para iniciar sesión
        Text(text = "Ya tienes una cuenta?", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {(navController.navigate("loginScreen"))}, modifier = Modifier.fillMaxWidth()) {
            Text("Inicia Sesión")
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Redes sociales
        Text(text = "O inicia sesión con redes sociales", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Image(painter = logoFb, contentDescription = "Facebook", modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Image(painter = logoGoogle, contentDescription = "Google", modifier = Modifier.size(50.dp))
        }
    }
}