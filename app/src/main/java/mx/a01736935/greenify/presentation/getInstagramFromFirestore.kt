package mx.a01736935.greenify.presentation

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

// Función para obtener el nombre de usuario de Instagram de Firestore
fun getInstagramFromFirestore(userId: String, onResult: (String?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId) // Suponiendo que tienes la colección "users"

    // Recuperamos el campo "instagram" del documento del usuario
    userRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                // Si el documento existe, obtenemos el campo "instagram"
                val instagramHandle = document.getString("instagram")
                onResult(instagramHandle) // Pasamos el valor a la función onResult
            } else {
                onResult(null) // Si no existe el documento, retornamos null
            }
        }
        .addOnFailureListener { exception ->
            // Si ocurre un error al obtener el documento
            onResult(null)
            println("Error obteniendo Instagram: ${exception.message}")
        }
}


fun saveInstagramToFirestore(userId: String, instagram: String) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    userRef.update("instagram", instagram)
        .addOnSuccessListener {
            println("Instagram actualizado exitosamente")
        }
        .addOnFailureListener { e ->
            println("Error al actualizar Instagram: ${e.message}")
        }
}