package mx.a01736935.greenify.viewmodel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _currentUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(auth.currentUser)
    val currentUser = _currentUser



    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                Log.d(javaClass.simpleName, "Usuario registrado")
            } catch (e: Exception) {
                Log.d(javaClass.simpleName, "${e.message}")
            }
        }
    }

    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
            } catch (e: Exception){
                Log.d(javaClass.simpleName,"${e.message}")
            }
        }
    }

    fun logout(){
        try {
            auth.signOut()
        } catch (e: Exception){
            Log.d(javaClass.simpleName, "${e.message}")
        }
    }

    fun ListenToAuthState() {
        auth.addAuthStateListener {
            this._currentUser.value = it.currentUser
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}
