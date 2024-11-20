package mx.a01736935.greenify

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.a01736935.greenify.ui.theme.GreenifyTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

const val WEB_CLIENT = "390208176910-0sage5goptts1hf64k1lsdckpdh00cmn.apps.googleusercontent.com"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenifyTheme{
                val navController = rememberNavController()
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val credentialManager = CredentialManager.create(context)
                AppScaffold()
            }
        }
    }
}

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Determina si se debe mostrar la barra de navegación
    val showBottomBar = when (currentRoute) {
        "initialScreen", "secondScreen", "loginScreen","createAccountScreen","forgotPasswordScreen" -> false
        else -> true
    }
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBarComponent(navController)
            }
        },
        content = { innerPadding ->
            App(navController, Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun App(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "initialScreen") {
        composable("initialScreen") { InitialView(navController,showBottomBar = false, onSwipeToSecond = {navController.navigate("secondScreen")})}
        composable("secondScreen"){ SecondView(navController,showBottomBar = false, onSwipeToLogin = { navController.navigate("loginScreen")},
            onSwipeBack = { navController.popBackStack()})}
        composable("loginScreen"){LoginView(navController, showBottomBar = false, onLoginSuccess = { navController.navigate("mainMenuScreen")})}
        composable("createAccountScreen") { CreateAccountView(navController, showBottomBar = false, ) }
        composable("forgotPasswordScreen") { ForgotPasswordView(navController, showBottomBar = false) }
        composable("mainMenuScreen") { MainMenuView(navController, showBottomBar = true) }
        composable("badgesScreen") { BadgesView(navController, showBottomBar = false)  }
        composable("articleScreen") { ArticleView(navController, showBottomBar = false)}
        composable("cameraScreen") { CameraView(navController, showBottomBar = false) }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OnGoogleSignInClick(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Inicializar FirebaseAuth
    val auth = FirebaseAuth.getInstance()

    // Configuración para Google Sign-In
    val credentialManager = CredentialManager.create(context)
    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(WEB_CLIENT) // Asegúrate de definir correctamente WEB_CLIENT
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    scope.launch {
        try {
            // Obtener credenciales
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            // Convertir credenciales de Google a credenciales de Firebase
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

            // Autenticar en Firebase con las credenciales de Google
            auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Si el inicio de sesión es exitoso, redirigir al usuario
                        navController.popBackStack()
                        navController.navigate("mainMenuView")
                    } else {
                        // Manejo de errores
                        Toast.makeText(context, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Error: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
}


@Composable
fun NavigationBarComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar(containerColor = Color(0xFFFFFFA1)){
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "MainScreen", tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black) },
            selected = selectedItem == 0,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            onClick = {
                selectedItem = 0
                navController.navigate("MainMenuScreen")
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "ArticleScreen",tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black) },
            selected = selectedItem == 1,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            onClick = {
                selectedItem = 1
                navController.navigate("ArticleScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.camera),modifier = Modifier.size(50.dp), contentDescription = "CameraScreen",
                tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black)
               },
            selected = selectedItem == 2,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            onClick = {
                selectedItem = 2
                navController.navigate("CameraScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "BadgesScreen",tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            selected = selectedItem == 4,
            onClick = {
                selectedItem = 4
                navController.navigate("BadgesScreen")
            }
        )
    }
}