package mx.a01736935.greenify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import mx.a01736935.greenify.ui.theme.GreenifyTheme
import mx.a01736935.greenify.viewmodel.AuthViewModel

import androidx.activity.viewModels

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()  // Aquí inicializas el ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GreenifyTheme {
                val currentUser = authViewModel.currentUser.collectAsState()
                if (currentUser.value != null) {
                    MainMenuView()
                } else {
                    CreateAccountView(onRegisterClick = { email, password ->
                        authViewModel.registerUser(email, password)
                    })
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authViewModel.ListenToAuthState()
    }
}