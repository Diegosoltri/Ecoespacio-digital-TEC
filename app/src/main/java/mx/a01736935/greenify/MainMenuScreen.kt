package mx.a01736935.greenify

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    val categories = listOf("Todos", "Transporte", "Energía", "Residuos") // Categorías simplificadas

    LazyRow(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
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
            containerColor = if (isSelected) Color(0xFFFFF176) else Color(0xFFEFEFEF),
            contentColor = Color.Black
        ),
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(text = category, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun EcoChallengeCard(challenge: EcoChallenge, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = challenge.imageResId),
                contentDescription = "Challenge Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            )
            Text(
                text = stringResource(id = challenge.nameResId),
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun EcoChallengeGrid(challenges: List<EcoChallenge>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(8.dp),
        content = {
            items(challenges) { challenge ->
                EcoChallengeCard(challenge)
            }
        }
    )
}

/*
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
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainMenuView(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("All") }

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) ) {
                    Text(
                        text = "GREENIFY",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "CONOCE NUESTROS NUEVOS RETOS!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4CAF50),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                // Carrusel de categorías
                CategoriesCarousel(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
                // Grid de retos basado en la categoría seleccionada
                EcoChallengeGrid(challenges = DataSource().loadEcoChallenges())
            }
        }