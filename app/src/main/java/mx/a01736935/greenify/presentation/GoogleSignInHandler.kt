package mx.a01736935.greenify.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mx.a01736935.greenify.WEB_CLIENT


    class GoogleSignInHandler(
        private val context: Context,
        private val auth: FirebaseAuth,
        private val credentialManager: CredentialManager,
        private val scope: CoroutineScope,
        private val navController: NavHostController
    ) {
            }