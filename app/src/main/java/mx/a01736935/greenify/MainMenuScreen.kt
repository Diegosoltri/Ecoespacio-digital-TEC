package mx.a01736935.greenify

import BottomButtonBar
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

import mx.a01736935.greenify.model.EcoChallenge
import mx.a01736935.greenify.data.DataSource





@Composable
fun CategoriesCarousel(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf(
        stringResource(R.string.category1),
        stringResource(R.string.category2),
        stringResource(R.string.category3),
        stringResource(R.string.category4),
        stringResource(R.string.category5)
    )
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

/*
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
*/

@Composable
fun CategoryButton(category: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = category,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() }
            .background(
                if (isSelected) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = if (isSelected) Color.White else Color.Black,
        fontSize = 14.sp
    )
}


@Composable
fun EcoChallengeCard(challenge: EcoChallenge, modifier: Modifier = Modifier) {
    // Estado para controlar la visibilidad del popup
    var showPopup by remember { mutableStateOf(false) }

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
            // Imagen del reto
            Image(
                painter = rememberImagePainter(challenge.imageResId),  // Usar Coil para carga eficiente
                contentDescription = "Challenge Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            )
            // Título del reto
            Text(
                text = stringResource(id = challenge.nameResId),
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Botón de información
            Button(
                onClick = { showPopup = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info), // Icono de información
                    contentDescription = "Información",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    // Mostrar popup si el estado está activo
    if (showPopup) {
        Dialog(onDismissRequest = { showPopup = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Título del reto
                    Text(
                        text = stringResource(id = challenge.nameResId),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Imagen del reto
                    Image(
                        painter = rememberImagePainter(challenge.imageResId),  // Usar Coil aquí también
                        contentDescription = "Imagen del reto",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Descripción del reto
                    Text(
                        text = stringResource(id = challenge.descriptionResId),
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para cerrar
                    Button(
                        onClick = { showPopup = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2196F3),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Cerrar")
                    }
                }
            }
        }
    }
}


@Composable
fun EcoChallengeGrid(challenges: List<EcoChallenge>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(8.dp),
        content = {
            items(challenges, key = { challenge -> challenge.id }) { challenge ->  // Usando 'id' como clave única
                EcoChallengeCard(challenge)
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainMenuView(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf("Reciclaje") } // Botón seleccionado
    val allChallenges = remember { DataSource().loadEcoChallenges() }
    val filteredChallenges = if (selectedCategory == "Todos") {
        allChallenges
    } else {
        allChallenges.filter { challenge ->
            stringResource(challenge.filterResId) == selectedCategory
        }
    }

    Scaffold(
        bottomBar = {
            BottomButtonBar(
                selectedButton = selectedCategory,
                onButtonSelected = { selectedCategory = it },
                navController = navController // Pasar el NavController aquí
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF5F5F5))
            ) {
                // Contenido del MainMenuView
                Text(
                    text = "GREENIFY",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                Text(
                    text = "CONOCE NUESTROS NUEVOS RETOS!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4CAF50),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                CategoriesCarousel(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
                // Grid de retos basado en la categoría seleccionada
                EcoChallengeGrid(challenges = filteredChallenges)
            }
        }
    )
}
