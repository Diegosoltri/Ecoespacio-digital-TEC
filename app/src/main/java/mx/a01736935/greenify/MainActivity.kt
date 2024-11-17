package mx.a01736935.greenify

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.a01736935.greenify.ui.theme.GreenifyTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenifyTheme{
                AppScaffold()
            }
        }
    }
}

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBarComponent(navController)
        },
        content = { innerPadding ->
            App(navController, Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun App(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "initialScreen") {
        composable("initialScreen") { InitialView(navController) }
        composable("createAccountScreen") { CreateAccountView(navController) }
        composable("forgotPasswordScreen") { ForgotPasswordView(navController) }
        composable("mainMenuScreen") { MainMenuView(navController) }
        composable("badgesScreen") { BadgesView(navController)  }
        composable("articleScreen") { ArticleView(navController)}
        composable("cameraScreen") { CameraView(navController) }
    }
}
/*
@Composable
fun BottomNavigationBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 8.dp)
            .background(Color(0xFFE8F5E9)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationIconButton(R.drawable.ic_home, "Inicio") { /* Acción */ }
        NavigationIconButton(R.drawable.ic_gallery, "Galería") { /* Acción */ }
        NavigationIconButton(R.drawable.ic_camera, "Cámara") { /* Acción */ }
        NavigationIconButton(R.drawable.ic_user, "Perfil") { /* Acción */ }
        NavigationIconButton(R.drawable.ic_star, "Favoritos") { /* Acción */ }
    }
}

@Composable
fun NavigationIconButton(iconRes: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF4CAF50),
            textAlign = TextAlign.Center
        )
    }
}
 */

@Composable
fun NavigationBarComponent(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = "RewardScreen") },
            label = { Text("Start") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                navController.navigate("InitialScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "ChallengeScreen") },
            label = { Text("Create Account") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                navController.navigate("CreateAccountScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "ProfileScreen") },
            label = { Text("I forgor") },
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                navController.navigate("ForgotPasswordScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "MapScreen") },
            label = { Text("Main Menu") },
            selected = selectedItem == 3,
            onClick = {
                selectedItem = 3
                navController.navigate("MainMenuScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "BadgesScreen") },
            label = { Text("Badges") },
            selected = selectedItem == 4,
            onClick = {
                selectedItem = 4
                navController.navigate("BadgesScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "ArticleScreen") },
            label = { Text("Articles") },
            selected = selectedItem == 5,
            onClick = {
                selectedItem = 5
                navController.navigate("ArticleScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "CameraScreen") },
            label = { Text("Camera") },
            selected = selectedItem == 5,
            onClick = {
                selectedItem = 5
                navController.navigate("CameraScreen")
            }
        )
    }
}