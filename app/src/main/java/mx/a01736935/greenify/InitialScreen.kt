package mx.a01736935.greenify

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.rememberSwipeableState

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun InitialView(navController: NavController, modifier: Modifier = Modifier, onSwipeToSecond: () -> Unit) {
    val swipeableState = rememberSwipeableState(0)
    val logoGreenify = painterResource(id = R.drawable.greenify)
    val logoEco = painterResource(id = R.drawable.logo_ecoespacio_2024)
    val greenButton = painterResource(id = R.drawable.greenarrow)
    val greenCircle = painterResource(id = R.drawable.greencircle)
    val grayCircle = painterResource(id = R.drawable.graycircle)

    LaunchedEffect(swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            1 -> { onSwipeToSecond() // Deslizar a la izquierda: navegar a "start"
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Image(painter = logoGreenify, contentDescription = "EcoMision", contentScale = ContentScale.FillWidth, modifier = Modifier.width(125.dp))
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Bienvenido a " + "Greenify",
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            fontSize = 50.sp,
            color = Color.Green
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Powered by", fontSize = 30.sp, color = Color.Gray)
            Image(painter = logoEco, contentDescription = "EcoEspacio", contentScale = ContentScale.FillWidth, modifier = Modifier.width(40.dp))
        }
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                Image(painter = greenCircle, contentDescription = "CurrentScreen", contentScale = ContentScale.FillWidth, modifier = Modifier.width(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Image(painter = grayCircle, contentDescription = "NextScreen", contentScale = ContentScale.FillWidth, modifier = Modifier.width(20.dp))
            }
            Image(painter = greenButton, contentDescription = "Forward", contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(50.dp).clickable {
                    navController.navigate("secondScreen")
                })
        }
    }
    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            onSwipeToSecond()
        }
    }
}