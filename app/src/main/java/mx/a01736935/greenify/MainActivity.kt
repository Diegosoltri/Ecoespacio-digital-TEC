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
import mx.a01736935.greenify.data.DataSource
import mx.a01736935.greenify.model.BadgeItem

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
        composable("initialScreen") { InitialView(navController, onSwipeToSecond = {navController.navigate("secondScreen")})}
        composable("secondScreen"){ SecondView(onSwipeToLogin = { navController.navigate("loginScreen")},
            onSwipeBack = { navController.popBackStack()})}
        composable("loginScreen"){LoginView(navController, onLoginSuccess = { navController.navigate("mainMenuScreen")})}
        composable("createAccountScreen") { CreateAccountView(navController) }
        composable("forgotPasswordScreen") { ForgotPasswordView(navController) }
        composable("mainMenuScreen") { MainMenuView(navController) }
        composable("badgesScreen") { BadgesView(navController)  }
        composable("articleScreen") { ArticleView(navController)}
        composable("cameraScreen") { CameraView(navController) }
    }
}


@Composable
fun NavigationBarComponent(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "MainScreen") },
            label = { Text("Main Menu") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                navController.navigate("MainMenuScreen")
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "ArticleScreen") },
            label = { Text("Articles") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                navController.navigate("ArticleScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "CameraScreen") },
            label = { Text("Camera") },
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                navController.navigate("CameraScreen")
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
    }
}