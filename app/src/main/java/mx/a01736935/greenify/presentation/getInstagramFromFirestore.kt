package mx.a01736935.greenify.presentation

import com.google.firebase.firestore.FirebaseFirestore

// Función para obtener una frase desde Firestore
fun getPhraseFromFirestore(userId: String, onResult: (String?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId) // Suponiendo que tienes la colección "users"

    // Recuperamos el campo "phrase" del documento del usuario
    userRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                // Si el documento existe, obtenemos el campo "phrase"
                val phrase = document.getString("phrase")
                onResult(phrase) // Pasamos el valor a la función onResult
            } else {
                onResult(null) // Si no existe el documento, retornamos null
            }
        }
        .addOnFailureListener { exception ->
            // Si ocurre un error al obtener el documento
            onResult(null)
            println("Error obteniendo la frase: ${exception.message}")
        }
}

// Función para guardar una frase en Firestore
fun savePhraseToFirestore(userId: String, phrase: String) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    // Actualizamos o agregamos el campo "phrase" en el documento del usuario
    userRef.update("phrase", phrase)
        .addOnSuccessListener {
            println("Frase actualizada exitosamente")
        }
        .addOnFailureListener { e ->
            println("Error al actualizar la frase: ${e.message}")
        }
}
