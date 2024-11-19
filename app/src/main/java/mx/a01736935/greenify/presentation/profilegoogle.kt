package mx.a01736935.greenify.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage



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