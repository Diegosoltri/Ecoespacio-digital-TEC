package mx.a01736935.greenify

import android.content.Intent
import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SecondView(modifier: Modifier = Modifier, onSwipeToLogin: () -> Unit,
    onSwipeBack: () -> Unit, showBottomBar: Boolean = false
) {
    val swipeableState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, -150f to 1, 150f to 2)

    LaunchedEffect(swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            1 -> {
                onSwipeToLogin() // Deslizar a la izquierda: navegar a "start"
            }
            -1 -> {
                onSwipeBack() // Deslizar a la derecha: regresar
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
    )
    val qr = painterResource(id = R.drawable.qr)
    val logo = painterResource(id = R.drawable.greenify)
    val circle = painterResource(id = R.drawable.graycircle)
    val grcircle = painterResource(id = R.drawable.greencircle)
    val arrow = painterResource(id = R.drawable.greenarrow)

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)  // Asegurarse que el fondo aquí es transparente
        ) {
            Image(
                painter = logo,
                contentDescription = "Logo",
                modifier = Modifier
                    .width(80.dp)
                    .padding(start = 16.dp)
            )
        }

        Image(
            painter = qr,
            contentDescription = "qr representativo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(200.dp)
        )
        Text(
            text = "Únete a Greenify, la red social que te desafía a cuidar el planeta." +
                    "Participa en retos ambientales, conecta con personas comprometidas y transforma tus acciones en un impacto positivo.",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = modifier
                .padding(25.dp)
                .fillMaxHeight(0.5f)
        )

        Spacer(modifier = Modifier.height(70.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = circle,
                    contentDescription = "PrevScreen",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = grcircle,
                    contentDescription = "CurrentScreen",
                    modifier = Modifier.width(20.dp)
                )
            }

            Image(
                painter = arrow,
                contentDescription = "Forward",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(50.dp)
            )
        }
    }

    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            onSwipeToLogin()
        }
    }
}