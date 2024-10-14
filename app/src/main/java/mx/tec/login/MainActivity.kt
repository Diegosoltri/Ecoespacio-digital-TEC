package mx.tec.login

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable


import mx.tec.login.ui.theme.LoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "inicio") {
                    composable("inicio") {
                        Inicio(
                            onSwipeToLogin = {
                                navController.navigate("start") // Navega a la pantalla de login
                            },
                            onSwipeBack = {
                                navController.popBackStack() // Regresa a la pantalla anterior
                            }
                        )
                    }
                    composable("start") {
                        Start(
                            onLoginSuccess = {
                                navController.navigate("inicio") // Maneja la navegación si es necesario
                            },
                            onSwipeBack = {
                                navController.popBackStack() // Regresa a la pantalla anterior
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun Start(modifier: Modifier = Modifier, onLoginSuccess: () -> Unit,
          onSwipeBack: () -> Unit) {

    val swipeableState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, 150f to 1)

    val logo = painterResource(id = R.drawable.diamante)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val strongGreen = Color(0xFF00B300)
    var clicked by remember { mutableStateOf(false) }
    val lightGreenCustom = Color(0xFFE0FFE0)
    val circle = painterResource(id = R.drawable.graycircle)
    val grcircle = painterResource(id = R.drawable.greencircle)
    val arrow = painterResource(id = R.drawable.greenarrow)



    Box(
        modifier = modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
    )

    Column (

        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = logo,
            contentDescription ="Logo",
            modifier = Modifier
                .width(120.dp)
                .padding(top = 50.dp))

        Text(

                text = "User",
            color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.Start)
                    .padding(start = 40.dp, top = 20.dp)
            )


        OutlinedTextField(
            value = email,
            singleLine = true,
            onValueChange = { email = it },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("@gmail.com") },
            modifier = Modifier
                .fillMaxWidth(0.9f)


        )

        Text(

            text = "Password",
            textAlign = TextAlign.Start,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.Start)
                .padding(start = 40.dp, top = 20.dp)

        )


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("*******") },
            modifier = Modifier
                .fillMaxWidth(0.9f)


        )

        Button(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,  // Color de fondo del botón
                contentColor = Color.White     // Color del texto del botón
            ),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .padding(top = 30.dp)
                .border(2.dp, strongGreen, RoundedCornerShape(12.dp))
        ){
            Text(
                text = "Login",
                color = Color.White, // Color del texto del botón
                fontSize = 20.sp,
                modifier = Modifier.padding(6.dp) // Espaciado interno del botón
            )



        }

        Text(
            text = "¿Olvidaste tu contraseña?",
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline,
            color = Color.Black, // Color del enlace
            modifier = Modifier
                .padding(top = 23.dp)
                .clickable {
                    // Acción al hacer clic: abrir el enlace en el navegador
                    val url = "https://www.ejemplo.com"
                    clicked = true // Cambia el estado si es necesario
                }
        )

        Button(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = lightGreenCustom,  // Color de fondo del botón
                contentColor = Color.White     // Color del texto del botón
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 30.dp)
                .border(2.dp, strongGreen, RoundedCornerShape(12.dp))
        ){
            Text(
                text = "Crear nueva cuenta",
                color = Color.Gray, // Color del texto del botón
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(6.dp) // Espaciado interno del botón
            )



        }

        Text(
            text = "Inicia sesión con otro metodo",
            color = Color.Gray, // Color del texto del botón
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top=20.dp) // Espaciado interno del botón
        )


        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Login con Google
            IconButton(onClick = { /* Lógica para login con Google */ }) {
                Image(
                    painter = painterResource(R.drawable.cromo),
                    contentDescription = "Login with Google",
                modifier = Modifier
                    .size(30.dp)
                )

            }

            // Login con Apple
            //IconButton(onClick = { /* Lógica para login con Apple */ }) {
            //    Image(painter = painterResource(R.drawable.ic_apple_logo), contentDescription = "Login with Apple")
            }




        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment= Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.Start) {
                Image(
                    painter = grcircle,
                    contentDescription = "CurrentScreen",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .width(30.dp)
                        .padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = circle,
                    contentDescription = "NextScreen",
                    modifier = Modifier
                        .width(21.dp)
                        .padding(start = 10.dp)
                )
            }


        }



    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun Inicio(
    modifier: Modifier = Modifier,
    onSwipeToLogin: () -> Unit, // Parámetro para navegar cuando se detecta el gesto de deslizamiento
    onSwipeBack: () -> Unit // Regresar a la pantalla anterior
) {
    val swipeableState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, -150f to 1, 150f to 2)

    LaunchedEffect(swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            1 -> {
                onSwipeToLogin() // Deslizar a la izquierda: navegar a "start"
            }
            -1 -> {
                onSwipeBack() // Deslizar a la derecha: regresar
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
    )

    val qr = painterResource(id = R.drawable.qr)
    val logo = painterResource(id = R.drawable.diamante)
    val circle = painterResource(id = R.drawable.graycircle)
    val grcircle = painterResource(id = R.drawable.greencircle)
    val arrow = painterResource(id = R.drawable.greenarrow)






    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)  // Asegurarse que el fondo aquí es transparente
        ) {
            Image(
                painter = logo,
                contentDescription = "Logo",
                modifier = Modifier
                    .width(80.dp)
                    .padding(start = 16.dp)
            )
        }

        Image(
            painter = qr,
            contentDescription = "qr representativo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(200.dp)


        )
        Text(
            text = "Únete a Greenify, la red social que te desafía a cuidar el planeta." +
                    "Participa en retos ambientales, conecta con personas comprometidas y transforma tus acciones en un impacto positivo.",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = modifier
                .padding(25.dp)
                .fillMaxHeight(0.5f)


        )


        Spacer(modifier = Modifier.height(70.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                Image(
                    painter = grcircle,
                    contentDescription = "CurrentScreen",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = circle,
                    contentDescription = "NextScreen",
                    modifier = Modifier.width(20.dp)
                )
            }

            Image(
                painter = arrow,
                contentDescription = "Forward",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(50.dp)
            )


        }

    }

    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            onSwipeToLogin()


        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginTheme {

       // MainScreen()
    }
}