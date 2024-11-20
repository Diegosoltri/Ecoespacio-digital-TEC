package mx.a01736935.greenify.model

import android.health.connect.datatypes.units.Percentage
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.IntegerRes
import mx.a01736935.greenify.R

data class BadgeItem(
    val estrellasActuales: Int = 0,
    val estrellasPorActividad: Int = 0,
    val estrellasRequeridas: Int = 0,
    val imagenNombre: String = "",
    val titulo: String = "",
    @DrawableRes val imageResId: Int = R.drawable.placeholder // Imagen por defecto
)

