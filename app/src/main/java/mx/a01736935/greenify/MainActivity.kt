package mx.a01736935.greenify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.a01736935.greenify.ui.theme.GreenifyTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.a01736935.greenify.data.DataSource
import mx.a01736935.greenify.model.EcoChallenge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenifyTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationSetup(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
/*
@Composable
fun NavigationSetup(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "initialScreen") {
        composable("initialScreen") {
            InitialScreen(onNavigateForward = {
                navController.navigate("createAccountScreen")
            })
        }
        composable("createAccountView") {
            CreateAccountScreen(onNavigateForward = {
                navController.navigate("mainMenuScreen")
            })
        }
        composable("mainMenuScreen") {
            MainMenuScreen()
        }
        // Define las rutas de las otras pantallas si ya existen
        composable("RewardScreen") { /* RewardScreen() */ }
        composable("ProfileScreen") { /* ProfileScreen() */ }
        composable("MapScreen") { /* MapScreen() */ }
        composable("CameraScreen") { /* CameraScreen() */ }
    }
}*/
@Composable
fun NavigationSetup(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "initialScreen") {
        composable("initialScreen") { InitialView(navController) }
        composable("createAccountScreen") { CreateAccountView(navController) }
        composable("forgotPasswordScreen") { ForgotPasswordView(navController) }
        composable("mainMenuScreen") { MainMenuView(navController) }
    }
}

/*
@Composable
fun NavigationBarComponent(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = "RewardScreen") },
            label = { Text("Rewards") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                navController.navigate("RewardScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "ChallengeScreen") },
            label = { Text("Challenges") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                navController.navigate("ChallengeScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "ProfileScreen") },
            label = { Text("Profile") },
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                navController.navigate("ProfileScreen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "MapScreen") },
            label = { Text("Map") },
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                navController.navigate("MapScreen")
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
    }
}*/