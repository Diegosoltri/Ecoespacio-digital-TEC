package mx.a01736935.greenify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Initio(navController: NavController, modifier: Modifier = Modifier, ) {

    val qr = painterResource(id = R.drawable.qr)
    val logo = painterResource(id = R.drawable.diamante_3)
    val greenButton = painterResource(id = R.drawable.greenarrow)
    val greenCircle = painterResource(id = R.drawable.greencircle)
    val grayCircle = painterResource(id = R.drawable.graycircle)





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
            Image(painter = greenButton, contentDescription = "Forward", contentScale = ContentScale.FillWidth, modifier = Modifier
                .width(50.dp)
                .clickable {
                    navController.navigate("Iniciar_sesion")
                })
        }

    }


    }
