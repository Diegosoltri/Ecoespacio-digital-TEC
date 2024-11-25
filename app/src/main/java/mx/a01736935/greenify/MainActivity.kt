package mx.a01736935.greenify

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import mx.a01736935.greenify.carruser.SetupTransparentSystemUi
import mx.a01736935.greenify.ui.theme.GreenifyTheme
import mx.a01736935.greenify.viewmodel.AuthViewModel
import mx.a01736935.greenify.viewmodel.ProfilePage

const val WEB_CLIENT = "390208176910-0sage5goptts1hf64k1lsdckpdh00cmn.apps.googleusercontent.com"

enum class Screen {
    Create,
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
        // This will lay out our app behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)


        enableEdgeToEdge()
        auth = Firebase.auth

        WindowCompat.setDecorFitsSystemWindows(window, false);
        setContent {
            GreenifyTheme {
                SetupTransparentSystemUi(
                    systemUiController = rememberSystemUiController(),
                    actualBackgroundColor = MaterialTheme.colorScheme.background
                )


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // app content here
                }
            }
                val navController = rememberNavController()
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val credentialManager = CredentialManager.create(context)

                val startDestination =
                    if (auth.currentUser == null) Screen.InitialView.name else Screen.Home.name

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {

                    composable(Screen.InitialView.name) {
                        InitialView(navController = navController)
                    }

                    composable(Screen.explication.name) {
                        Initio(navController = navController)
                    }

                    composable(Screen.Login.name) {
                        LoginView(
                            navController= navController,
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

                    composable(Screen.Create.name) {
                        CreateAccountView(
                            navController= navController,
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

                }


            }
    }
}