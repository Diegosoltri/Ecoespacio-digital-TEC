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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun InitialView(navController: NavController, modifier: Modifier = Modifier) {
    val logoGreenify = painterResource(id = R.drawable.greenify)
    val logoEco = painterResource(id = R.drawable.logo_ecoespacio_2024)
    val greenButton = painterResource(id = R.drawable.greenarrow)
    val greenCircle = painterResource(id = R.drawable.greencircle)
    val grayCircle = painterResource(id = R.drawable.graycircle)



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp)
            //.background(Color(0xFFFAFAFA))
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = logoGreenify,
            contentDescription = "EcoMision",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(230.dp))
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Bienvenido a " + "Greenify",
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            fontSize = 50.sp,
            color = Color(0xFF66BB6A)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Powered by", fontSize = 10.sp, color = Color.Gray)
            Image(painter = logoEco, contentDescription = "EcoEspacio", contentScale = ContentScale.FillWidth, modifier = Modifier.width(40.dp))
        }
        Spacer(modifier = Modifier.height(120.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
            }
            Image(painter = greenButton, contentDescription = "Forward", contentScale = ContentScale.FillWidth, modifier = Modifier
                .width(50.dp)
                .clickable {
                    navController.navigate(Screen.explication.name)
                })
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewInitialView() {
    // Create a dummy NavController using rememberNavController
    val navController = rememberNavController()

    InitialView(navController = navController)
}