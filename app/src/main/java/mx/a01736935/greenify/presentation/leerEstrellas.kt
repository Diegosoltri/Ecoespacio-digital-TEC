package mx.a01736935.greenify.presentation

import com.google.firebase.firestore.FirebaseFirestore

fun getStarsFromFirestore(documentId: String, onResult: (Int?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("logros")
        .document(documentId)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val estrellasActuales = document.getLong("estrellasActuales")?.toInt()
                onResult(estrellasActuales)
            } else {
                onResult(null) // El documento no existe
            }
        }
        .addOnFailureListener {
            onResult(null) // Error en la lectura
        }
}