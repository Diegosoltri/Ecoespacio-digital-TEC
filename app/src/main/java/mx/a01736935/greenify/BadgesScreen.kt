package mx.a01736935.greenify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.a01736935.greenify.data.DataSource
import mx.a01736935.greenify.model.BadgeItem

@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "GREENIFY",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50) // Color verde
            )
            Text(
                text = "LISTO PARA TU INSIGNIA!",
                fontSize = 16.sp,
                color = Color(0xFF8BC34A) // Color verde claro
            )
        }
        Image(
            painter = painterResource(id = R.drawable.gadiro), // Reemplaza con tu recurso de imagen de perfil
            contentDescription = "Perfil",
            modifier = Modifier.size(48.dp)
        )
    }
}


@Composable
fun BadgesCarousel(selectedBadge: String, onBadgeSelected: (String) -> Unit) {
    val badges = listOf("Desbloqueados", "Proximos", "Todos")
    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            .background(Color(0xFFE0E0E0), shape = MaterialTheme.shapes.medium),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(badges) { badge ->
            BadgesButton(
                badge = badge,
                isSelected = badge == selectedBadge,
                onClick = { onBadgeSelected(badge) }
            )
        }
    }
}

@Composable
fun BadgesButton(badge: String, isSelected: Boolean, onClick: () -> Unit) {
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
        Text(text = badge, fontSize = 14.sp)
    }
}


@Composable
fun BadgesList(badges: List<BadgeItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(badges) { badge ->
            BadgeRow(badge)
        }
    }
}

@Composable
fun BadgeRow(badge: BadgeItem) {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var maxProgress by remember { mutableFloatStateOf(0f) }
    currentProgress = badge.progressResId.toFloat()
    maxProgress = badge.maxProgressResId.toFloat()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Icono de insignia
        Image(
            painter = painterResource(id = badge.imageResId), // Reemplaza con el icono de cada insignia
            contentDescription = stringResource(id = badge.nameResId),
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = badge.nameResId),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(text = "${integerResource(id = badge.progressResId)}/${integerResource(id = badge.maxProgressResId)}", color = Color(0xFF4CAF50), fontSize = 14.sp)
                Spacer(modifier = Modifier.width(16.dp))
            }
            LinearProgressIndicator(
                progress = { currentProgress/maxProgress },
                color = Color(0xFF4CAF50),
                modifier = Modifier.fillMaxWidth().height(8.dp),
            )
        }
    }
}

@Composable
fun BadgesView(navController : NavController) {
    var selectedBadge by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Header()

        Spacer(modifier = Modifier.height(16.dp))

        BadgesCarousel(
            selectedBadge = selectedBadge,
            onBadgeSelected = { selectedBadge = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de insignias
        BadgesList(badges = DataSource().loadBadges())
    }
}