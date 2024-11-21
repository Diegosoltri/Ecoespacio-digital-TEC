import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.a01736935.greenify.Screen
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import mx.a01736935.greenify.R

@Composable
fun BottomButtonBar(
    selectedButton: String,
    onButtonSelected: (String) -> Unit,
    navController: NavHostController,

) {
    val buttons = listOf(
        Pair("Home", R.drawable.ic_home),
        Pair("Tip",  R.drawable.ic_home),
        Pair("Camera",  R.drawable.ic_home),
        Pair("Profile",  R.drawable.ic_home),
        Pair("MC",  R.drawable.ic_home)
    )


    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xA0FF0000)) // Fondo verde semi-transparente
            .padding(bottom = 0.dp) // Ajustar padding si es necesario
            .systemBarsPadding() // Asegura que no se superponga a la barra de estado
    ) {
        buttons.forEach { (button, iconRes) ->
            NavigationBarItem(
                selected = button == selectedButton,
                onClick = {
                    onButtonSelected(button)
                    onNavigate(button, navController) // Navegar al botón seleccionado
                },
                icon = {
                    val iconPainter: Painter = rememberImagePainter(iconRes) // Cargar SVG
                    Image(
                        painter = iconPainter,
                        contentDescription = button,
                        modifier = Modifier.size(24.dp) // Tamaño del icono
                    )
                },
                label = {
                    Text(
                        text = button,
                        color = if (button == selectedButton) Color.White else Color(0xFF4CAF50),
                        fontSize = 10.sp
                    )
                },
            )
        }
    }
}
fun onNavigate(button: String, navController: NavHostController) {
    when (button) {
        "Home" -> navController.navigate(Screen.Home.name)
        "Tip" -> navController.navigate(Screen.BadgesScreen.name)
        "Camera" -> navController.navigate(Screen.BadgesScreen.name)
        "Profile" -> navController.navigate(Screen.ProfilePage.name)
    }
}