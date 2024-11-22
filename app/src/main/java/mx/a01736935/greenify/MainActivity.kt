package mx.a01736935.greenify

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import mx.a01736935.greenify.ui.theme.GreenifyTheme
import mx.a01736935.greenify.viewmodel.AuthViewModel
import mx.a01736935.greenify.viewmodel.ProfilePage

const val WEB_CLIENT = "390208176910-0sage5goptts1hf64k1lsdckpdh00cmn.apps.googleusercontent.com"

enum class Screen {
    Login,
    Home,
    ProfilePage,
    InitialView,
    explication,
    ArticleScreen,
    BadgesScreen,
    CameraScreen,
    ForgotPasswordScreen
}

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)

        // Configura la apariencia de las barras del sistema para tener iconos claros y fondo oscuro
        windowInsetsController.isAppearanceLightStatusBars = true // Barra de estado con íconos claros
        windowInsetsController.isAppearanceLightNavigationBars = true // Barra de navegación con íconos claros

        // Configura el color de la barra de estado y la barra de navegación
        window.statusBarColor = getColor(android.R.color.transparent) // O cualquier color que desees
        window.navigationBarColor = getColor(android.R.color.transparent)

        // Configura el color de la barra de estado
        window.statusBarColor = getColor(R.color.transparent)
        enableEdgeToEdge()
        auth = Firebase.auth

        setContent {
            GreenifyTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val credentialManager = CredentialManager.create(context)

                val startDestination = if (auth.currentUser == null) Screen.InitialView.name else Screen.Home.name

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {

                    composable(Screen.InitialView.name){
                        InitialView(navController = navController)
                    }

                    composable(Screen.explication.name){
                        Initio(navController = navController)
                    }


                    composable(Screen.Login.name) {
                        CreateAccountView(
                            onRegisterClick = { email, password ->
                                authViewModel.registerUser(email, password)
                            },
                            onGoogleSignInClick = {
                                val googleIdOption = GetGoogleIdOption.Builder()
                                    .setFilterByAuthorizedAccounts(false)
                                    .setServerClientId(WEB_CLIENT)
                                    .build()

                                val request = GetCredentialRequest.Builder()
                                    .addCredentialOption(googleIdOption)
                                    .build()

                                scope.launch {
                                    try {
                                        val result = credentialManager.getCredential(
                                            context = context,
                                            request = request
                                        )
                                        val credential = result.credential
                                        val googleIdTokenCredential = GoogleIdTokenCredential
                                            .createFrom(credential.data)
                                        val googleIdToken = googleIdTokenCredential.idToken

                                        val firebaseCredential =
                                            GoogleAuthProvider.getCredential(googleIdToken, null)

                                        auth.signInWithCredential(firebaseCredential)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    navController.popBackStack()
                                                    navController.navigate(Screen.Home.name)
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
                        )
                    }

                    composable(Screen.Home.name) {
                        MainMenuView(navController = navController)
                    }

                    composable(Screen.ProfilePage.name) {
                        ProfilePage(navController = navController)
                    }
                    composable(Screen.BadgesScreen.name) {
                        BadgesView(navController = navController)
                    }

                    composable(Screen.ArticleScreen.name) {
                        ArticleView(navController = navController)
                    }

                    composable(Screen.CameraScreen.name) {
                        CameraView(navController = navController)
                    }

                    composable(Screen.ForgotPasswordScreen.name) {
                        ForgotPasswordView(navController = navController)
                    }
                }
            }
        }
    }
}
