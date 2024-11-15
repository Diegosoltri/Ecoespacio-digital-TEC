package mx.a01736935.greenify

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
fun ArticleCard(article: ArticleItem, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = stringResource(id = article.articleResId))
    }
}

@Composable
fun ArticleGrid(articles: List<ArticleItem>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Dos columnas
        modifier = modifier.padding(8.dp),
        content = {
            items(articles) { article ->
                ArticleCard(article)
            }
        }
    )
}

@Composable
fun ArticleView(navController: NavController){
    val context = LocalContext.current
    val guideUrl = "https://drive.google.com/file/d/1dta8BKrz4zCz3BS4PdqqO-pQKGuqwyWl/view?usp=sharing"
    var selectedArticle by remember { mutableStateOf("All") }
    val allArticles = DataSource().loadArticle()
    val filteredArticles = when (selectedArticle) {
        "Transporte" -> allArticles.filter { it.articleResId in R.string.transport1..R.string.transport10 }
        "Energía" -> allArticles.filter { it.articleResId in R.string.energy1..R.string.energy10 }
        "Consumo" -> allArticles.filter { it.articleResId in R.string.consumption1..R.string.consumption10 }
        "Desecho" -> allArticles.filter { it.articleResId in R.string.waste1..R.string.waste10 }
        else -> allArticles
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
            Text(
                    text = "Guía de Cuidado" + "del Medio Ambiente",
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
        Spacer(modifier = Modifier.height(10.dp))
            TextButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, parse(guideUrl))
                context.startActivity(intent)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Guía Completa", color = Color.Blue)
        }

            // Carrusel de categorías
        ArticleCarousel(
                selectedArticle = selectedArticle,
                onArticleSelected = { selectedArticle = it }
        )
            // Grid de retos basado en la categoría seleccionada
        ArticleGrid(articles = filteredArticles)
    }
}