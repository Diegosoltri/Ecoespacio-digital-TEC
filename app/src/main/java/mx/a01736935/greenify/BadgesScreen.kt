package mx.a01736935.greenify

import BottomButtonBar
import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import mx.a01736935.greenify.model.logros


@Composable
fun Header() {

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GREENIFY",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Text(
                text = "Escanea aqui tus misiones para obtener tus recompensas",
                fontSize = 17.sp,
                color = Color(0xFF4CAF50),
                textAlign = TextAlign.Center,
                modifier = Modifier

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
fun BadgesList(badges: List<logros>, modifier: Modifier = Modifier) {
    if (badges.isEmpty()) {
        // Mostrar un mensaje cuando no hay logros en la categoría seleccionada
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay logros en esta categoría todavía.",
                color = Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        // Mostrar la lista de logros si no está vacía
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(badges) { badge ->
                BadgeRow(badge)
            }
        }
    }
}

object BadgeRepository {
    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()

    suspend fun loadBadges(userId: String): List<logros> {
        return try {
            db.collection("users")
                .document(userId)
                .collection("logros")
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    val badge = document.toObject(logros::class.java)
                    badge?.copy(
                        estrellasActuales = document.getLong("estrellasActuales")?.toInt() ?: 0,
                        estrellasPorActividad = document.getLong("estrellasPorActividad")?.toInt() ?: 0,
                        estrellasRequeridas = document.getLong("estrellasRequeridas")?.toInt() ?: 0,
                        imagenNombre = document.getString("imagenNombre") ?: "",
                        titulo = document.getString("titulo") ?: ""
                    )?.let {
                        it.copy(imageResId = getImageResId(it.imagenNombre))
                    }
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun initializeLogrosForUser(userId: String) {
        try {
            val userLogrosRef = db.collection("users")
                .document(userId)
                .collection("logros")

            val logrosSnapshot = userLogrosRef.get().await()

            // Si la colección está vacía, inicializa con logros predeterminados
            if (logrosSnapshot.isEmpty) {
                val initialLogros = listOf(
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 10,
                        "estrellasRequeridas" to 100,
                        "imagenNombre" to "energia",
                        "titulo" to "Aprende a cuidar la energía"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 5,
                        "estrellasRequeridas" to 50,
                        "imagenNombre" to "residuos",
                        "titulo" to "Aprende a separar tus residuos"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 10,
                        "estrellasRequeridas" to 100,
                        "imagenNombre" to "reciclado",
                        "titulo" to "Asiste al centro de reciclado"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 20,
                        "estrellasRequeridas" to 200,
                        "imagenNombre" to "camina",
                        "titulo" to "Camina 3 KM"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 10,
                        "estrellasRequeridas" to 100,
                        "imagenNombre" to "carro",
                        "titulo" to "Comparte automóvil con un compañero"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 10,
                        "estrellasRequeridas" to 100,
                        "imagenNombre" to "graycircle",
                        "titulo" to "Inicia tu viaje ecológico"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 5,
                        "estrellasRequeridas" to 50,
                        "imagenNombre" to "flora",
                        "titulo" to "Conoce la flora del vivero TEC"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 5,
                        "estrellasRequeridas" to 50,
                        "imagenNombre" to "regar",
                        "titulo" to "Riega el vivero TEC"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 5,
                        "estrellasRequeridas" to 50,
                        "imagenNombre" to "thermo",
                        "titulo" to "Trae tu propio termo"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 20,
                        "estrellasRequeridas" to 200,
                        "imagenNombre" to "bicicleta",
                        "titulo" to "Usa la bicicleta para llegar al TEC"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 15,
                        "estrellasRequeridas" to 150,
                        "imagenNombre" to "composta",
                        "titulo" to "Ve al taller de composta"
                    ),
                    mapOf(
                        "estrellasActuales" to 0,
                        "estrellasPorActividad" to 10,
                        "estrellasRequeridas" to 100,
                        "imagenNombre" to "desechos",
                        "titulo" to "Ve al taller sobre los desechos"
                    )
                )

                // Agregar logros iniciales a la colección
                initialLogros.forEach { logro ->
                    userLogrosRef.add(logro)
                }
            }
        } catch (e: Exception) {
            Log.e("BadgeRepository", "Error al inicializar logros: ${e.message}")
        }
    }
}

fun getImageResId(imageName: String): Int {
    return when (imageName) {
        "camina" -> R.drawable.camina
        "bicicleta" -> R.drawable.bicicleta
        "carro" -> R.drawable.carro
        "composta" -> R.drawable.composta
        "desechos" -> R.drawable.desechos
        "energia" -> R.drawable.energia
        "flora" -> R.drawable.flora
        "reciclado" -> R.drawable.reciclado
        "regar" -> R.drawable.regar
        "residuos" -> R.drawable.residuos
        "thermo" -> R.drawable.thermo
        else -> R.drawable.placeholder // Imagen predeterminada si no se encuentra
    }
}


@Composable
fun BadgeRow(badge: logros) {
    val estrellas = badge.estrellasActuales.toFloat()
    val maxProgress = badge.estrellasRequeridas.toFloat()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Cargar imagen desde recursos locales
        Image(
            painter = painterResource(id = badge.imageResId),
            contentDescription = badge.titulo,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = badge.titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Text(
                text = "${badge.estrellasActuales}/${badge.estrellasRequeridas} estrellas",
                color = Color.Gray,
                fontSize = 14.sp
            )
            LinearProgressIndicator(
                progress = { estrellas / maxProgress },
                modifier = Modifier
                    .width(50.dp)
                    .height(8.dp),
                color = Color.Green,
                trackColor = Color.Gray
            )
        }
    }
}


@Composable
fun BadgesView(navController: NavHostController) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid // Sustituye con el ID del usuario autenticado
    var selectedButton by remember { mutableStateOf("Badge") }
    var selectedBadge by remember { mutableStateOf("Todos") }
    var badges by remember { mutableStateOf<List<logros>>(emptyList()) }
    var filteredBadges by remember { mutableStateOf<List<logros>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        scope.launch {
            if (userId != null) {
                BadgeRepository.initializeLogrosForUser(userId)
            }
        }
    }
    // Cargar las insignias desde Firebase al inicio
    LaunchedEffect(Unit) {
        isLoading = true
        badges = userId?.let { BadgeRepository.loadBadges(it) }!!
        isLoading = false
        filteredBadges = badges // Mostrar todas inicialmente
    }

    // Filtrar insignias según la categoría seleccionada
    LaunchedEffect(selectedBadge, badges) {
        filteredBadges = when (selectedBadge) {
            "Desbloqueadas" -> badges.filter { it.estrellasActuales == it.estrellasRequeridas }
            "Proximos" -> badges.filter { it.estrellasActuales > 0 && it.estrellasActuales < it.estrellasRequeridas }
            else -> badges // "Todos"
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
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                Header()

                Spacer(modifier = Modifier.height(8.dp))

                // Carrusel de categorías
                BadgesCarousel(
                    selectedBadge = selectedBadge,
                    onBadgeSelected = { selectedBadge = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de insignias
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color(0xFF4CAF50)
                    )
                } else {
                    BadgesList(badges = filteredBadges)
                }
            }
        }
    )
}