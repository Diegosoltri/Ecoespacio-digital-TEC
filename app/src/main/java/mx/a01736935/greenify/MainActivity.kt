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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Determina si se debe mostrar la barra de navegación
    val showBottomBar = when (currentRoute) {
        "initialScreen", "secondScreen", "loginScreen","createAccountScreen","forgotPasswordScreen" -> false
        else -> true
    }
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBarComponent(navController)
            }
        },
        content = { innerPadding ->
            App(navController, Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun App(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "initialScreen") {
        composable("initialScreen") { InitialView(navController,showBottomBar = false, onSwipeToSecond = {navController.navigate("secondScreen")})}
        composable("secondScreen"){ SecondView(showBottomBar = false, onSwipeToLogin = { navController.navigate("loginScreen")},
            onSwipeBack = { navController.popBackStack()})}
        composable("loginScreen"){LoginView(navController, showBottomBar = false, onLoginSuccess = { navController.navigate("mainMenuScreen")})}
        composable("createAccountScreen") { CreateAccountView(navController, showBottomBar = false) }
        composable("forgotPasswordScreen") { ForgotPasswordView(navController, showBottomBar = false) }
        composable("mainMenuScreen") { MainMenuView(navController, showBottomBar = true) }
        composable("badgesScreen") { BadgesView(navController, showBottomBar = false)  }
        composable("articleScreen") { ArticleView(navController, showBottomBar = false)}
        composable("cameraScreen") { CameraView(navController, showBottomBar = false) }
    }
}


@Composable
fun NavigationBarComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar(containerColor = Color(0xFFFFFFA1)){
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "MainScreen", tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black) },
            selected = selectedItem == 0,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            onClick = {
                selectedItem = 0
                navController.navigate("MainMenuScreen")
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "ArticleScreen",tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black) },
            selected = selectedItem == 1,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            onClick = {
                selectedItem = 1
                navController.navigate("ArticleScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.camera),modifier = Modifier.size(50.dp), contentDescription = "CameraScreen",
                tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black)
               },
            selected = selectedItem == 2,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            onClick = {
                selectedItem = 2
                navController.navigate("CameraScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "BadgesScreen",tint = if (selectedItem == 2) Color(0xFF4CAF50) else Color.Black) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Black
            ),
            selected = selectedItem == 4,
            onClick = {
                selectedItem = 4
                navController.navigate("BadgesScreen")
            }
        )
    }
}