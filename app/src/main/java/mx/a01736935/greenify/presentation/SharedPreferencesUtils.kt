package mx.a01736935.greenify.presentation

import com.google.firebase.firestore.FirebaseFirestore


fun saveUserNameToFirestore(userId: String, userName: String) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    val userData = mapOf("name" to userName)

    userRef.set(userData)
        .addOnSuccessListener {
            // Nombre guardado correctamente
            println("Nombre de usuario guardado en Firestore")
        }
        .addOnFailureListener { e ->
            // Manejar el error
            println("Error al guardar el nombre de usuario: $e")
        }
}

fun getUserNameFromFirestore(userId: String, onComplete: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    userRef.get()
        .addOnSuccessListener { document ->
            if (document != null && document.contains("name")) {
                val name = document.getString("name") ?: "Nombre del Usuario"
                onComplete(name)
            } else {
                onComplete("Nombre del Usuario")
            }
        }
        .addOnFailureListener { e ->
            println("Error al recuperar el nombre de usuario: $e")
            onComplete("Nombre del Usuario")
        }
}
