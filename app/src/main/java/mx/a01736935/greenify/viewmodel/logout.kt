package mx.a01736935.greenify.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth
import mx.a01736935.greenify.Screen

fun logout(context: Context, navController: NavController) {
    try {
        // Si usas Firebase:
        FirebaseAuth.getInstance().signOut()

        // Opcional: Muestra un mensaje
        Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()

        // Navegar a la pantalla de inicio de sesión
        navController.navigate(Screen.Create.name) {
            popUpTo(0) // Borra el historial de navegación
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error al cerrar sesión", Toast.LENGTH_SHORT).show()
    }
}