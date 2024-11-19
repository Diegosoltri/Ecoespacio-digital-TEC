package mx.a01736935.greenify

import android.annotation.SuppressLint
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

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
fun ProfileImage(profileImageUrl: String?, onProfileClick: () -> Unit) {
    // Usa AsyncImage de Coil para cargar la imagen desde la URL
    AsyncImage(
        model = profileImageUrl,
        contentDescription = "Profile Photo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(60.dp)
            .padding(8.dp)
            .clickable { onProfileClick() }
    )
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
                painter = painterResource(id = challenge.imageResId),
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
                        painter = painterResource(id = challenge.imageResId),
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
            items(challenges) { challenge ->
                EcoChallengeCard(challenge)
            }
        }
    )
}
@Composable
fun BottomNavigationBar(
    onProfileClick: () -> Unit // Añadido el parámetro
) {
    BottomAppBar(
        contentColor = Color.White,
        containerColor = Color(0x4D4CAF50), // El prefijo 0x4D ajusta la opacidad (aproximadamente 30% de transparencia)
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

        // Icono de perfil que lleva al perfil del usuario
        IconButton(onClick = onProfileClick) {  // Modificado para usar el parámetro
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
fun MainMenuView(
    onSignOutClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onProfileClick = onProfileClick // Agregado para navegación al perfil
            )
        },
        floatingActionButton = {
            CameraButton()
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            // Agregamos un Spacer al inicio de la columna para empujar toda la sección más abajo
            Spacer(modifier = Modifier.height(32.dp)) // Ajusta este valor según lo necesites

            // Row para alinear el texto y los elementos con la foto a la derecha
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                // Columna para los textos a la izquierda
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f) // Esto hace que el texto ocupe el espacio disponible
                ) {
                    // Título "GREENIFY"
                    Text(
                        text = "GREENIFY",
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50),
                        textAlign = TextAlign.Start,
                    )
                    // Subtítulo "CONOCE NUESTROS NUEVOS RETOS!"
                    Text(
                        text = "CONOCE NUESTROS NUEVOS RETOS!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4CAF50),
                        textAlign = TextAlign.Start
                    )
                }



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


@Preview(showBackground = true)
@Composable
fun PreviewMainMenuView() {
    // Dummy placeholders para las funciones que no son necesarias en el preview
    val onSignOutClick = {}
    val onProfileClick = {}

    MainMenuView(
        onSignOutClick = onSignOutClick,  // Usamos funciones vacías
        onProfileClick = onProfileClick   // Usamos funciones vacías
    )
}

