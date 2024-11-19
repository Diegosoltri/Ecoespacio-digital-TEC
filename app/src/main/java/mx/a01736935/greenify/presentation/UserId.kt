package mx.a01736935.greenify.presentation

import com.google.firebase.auth.FirebaseAuth

class UserId {

    private val firebaseAuth = FirebaseAuth.getInstance()

    // Constructor o función de inicialización
    fun getUserId(): String? {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.uid
    }

    // Función para verificar el userId
    fun printUserId() {
        val userId = getUserId()

        if (userId != null) {

            println("User ID: $userId")
        } else {
            // Si no hay un usuario autenticado
            println("No hay un usuario autenticado")
        }
    }
}
