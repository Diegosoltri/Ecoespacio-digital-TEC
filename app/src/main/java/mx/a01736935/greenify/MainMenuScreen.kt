package mx.a01736935.greenify

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.a01736935.greenify.model.EcoChallenge
import mx.a01736935.greenify.data.DataSource

@Composable
fun CategoriesCarousel(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("All", "Movilidad", "Reciclado", "Agua", "Energía", "Residuos") // Lista de categorías

    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryButton(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryButton(category: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFB8F168) else Color(0xFFEFEFEF),
            contentColor = Color.Black
        ),
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 8.dp)
    ) {
        Text(text = category, fontSize = 14.sp)
    }
}

@Composable
fun EcoChallengeCard(challenge: EcoChallenge, modifier: Modifier = Modifier){
    Card (elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(4.dp)) {
        Row {
            Image(painter = painterResource(id = challenge.imageResId), contentDescription = stringResource(
                id = challenge.nameResId), contentScale = ContentScale.Crop, modifier = Modifier.width(200.dp))
            Column (modifier = Modifier
                .padding(16.dp)
                .weight(1f)) {
                Text(text = stringResource(id = challenge.nameResId), fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, color = Color(0,154,20)
                )
                Text(text = stringResource(id = challenge.locationResId))
                Text(text = stringResource(id = challenge.bountyResId))
            }
        }
    }
}

@Composable
fun EcoChallengeGrid(challenges: List<EcoChallenge>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Dos columnas
        modifier = modifier.padding(8.dp),
        content = {
            items(challenges) { challenge ->
                EcoChallengeCard(challenge)
            }
        }
    )
}

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        contentColor = Color.White,
        containerColor = Color(0xFF4CAF50), // Cambia el color de la barra según el estilo de tu app
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
    ) {
        // Íconos de navegación (izquierda y derecha de la barra)
        IconButton(onClick = { /* Acción para la primera opción */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Espaciador para centrar el contenido

        IconButton(onClick = { /* Acción para la segunda opción */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CameraButton() {
    FloatingActionButton(
        onClick = { /* Acción de la cámara */ },
        containerColor = Color(0xFF4CAF50), // Color de fondo del botón
        shape = MaterialTheme.shapes.large, // Puedes cambiar la forma si lo deseas
        modifier = Modifier.size(64.dp) // Ajuste del tamaño del FAB
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = "Camera",
            tint = Color.White
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainMenuView(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("All") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        },
        floatingActionButton = {
            CameraButton()
        },
        floatingActionButtonPosition = FabPosition.Center, // Posicionamos el FAB al centro
    ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Greenify",
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp,
                        fontSize = 50.sp,
                        color = Color.Green
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gadiro),
                        contentDescription = "Profile Photo",
                        modifier = Modifier.width(60.dp)
                    )
                }

                // Carrusel de categorías
                CategoriesCarousel(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
                // Grid de retos basado en la categoría seleccionada
                EcoChallengeGrid(challenges = DataSource().loadEcoChallenges())
            }
        }
}