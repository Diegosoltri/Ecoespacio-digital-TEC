package mx.a01736935.greenify

import BottomButtonBar
import android.content.Intent
import android.net.Uri
import android.net.Uri.parse
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import mx.a01736935.greenify.model.ArticleItem
import mx.a01736935.greenify.model.ArticleConsumption
import mx.a01736935.greenify.model.ArticleEnergy
import mx.a01736935.greenify.model.ArticleTransport
import mx.a01736935.greenify.model.ArticleWaste
import mx.a01736935.greenify.data.DataSource

@Composable
fun ArticleCarousel(selectedArticle: String, onArticleSelected: (String) -> Unit) {
    val articles = listOf("Todos", "Transporte", "Energía", "Consumo", "Desecho") // Lista de categorías

    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles) { article ->
            ArticleButton(
                article = article,
                isSelected = article == selectedArticle,
                onClick = { onArticleSelected(article) }
            )
        }
    }
}

@Composable
fun ArticleButton(article: String, isSelected: Boolean, onClick: () -> Unit) {
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
        Text(text = article, fontSize = 14.sp)
    }
}

@Composable
fun ArticleView(navController: NavHostController) {
    val context = LocalContext.current
    val guideUrl = "https://drive.google.com/file/d/1dta8BKrz4zCz3BS4PdqqO-pQKGuqwyWl/view?usp=sharing"
    var selectedButton by remember { mutableStateOf("Article") }
    // Estado para la categoría seleccionada y el consejo actual
    var selectedCategory by remember { mutableStateOf("Todos") }
    var currentTip by remember { mutableStateOf("Selecciona una categoría y presiona 'Nuevo Consejo'") }

    // Cargar las listas predefinidas
    val transportTips = DataSource().loadArticleTransport()
    val energyTips = DataSource().loadArticleEnergy()
    val consumptionTips = DataSource().loadArticleConsumption()
    val wasteTips = DataSource().loadArticleWaste()
    val articleTips = DataSource().loadArticle()

    // Función para obtener un consejo aleatorio
    fun getRandomTip(category: String): String {
        return when (category) {
            "Transporte" -> context.getString(transportTips.random().transportResId)
            "Energía" -> context.getString(energyTips.random().energyResId)
            "Consumo" -> context.getString(consumptionTips.random().consumptionResId)
            "Desecho" -> context.getString(wasteTips.random().wasteResId)
            else -> {
                // Concatenar todas las listas y seleccionar un elemento aleatorio
                context.getString(articleTips.random().articleResId)
            }
        }
    }
    Scaffold(
        bottomBar = {
            BottomButtonBar(
                selectedButton = selectedButton,
                onButtonSelected = { selectedButton = it },
                navController = navController // Pasar el NavController aquí
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "GREENIFY",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Guía de cuidado del Medio Ambiente",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para abrir la guía completa
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, parse(guideUrl))
                    context.startActivity(intent)
                }) {
                    Text(text = "Guía Completa", color = Color.Blue)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Carrusel de categorías
                ArticleCarousel(
                    selectedArticle = selectedCategory,
                    onArticleSelected = { selectedCategory = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Texto del consejo actual
                Text(
                    text = currentTip,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para generar un nuevo consejo
                Button(
                    onClick = {
                        currentTip = getRandomTip(selectedCategory)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB8F168),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Nuevo Consejo")
                }
            }
        }
    )
}