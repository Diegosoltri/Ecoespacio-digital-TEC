package mx.a01736935.greenify.model

import android.health.connect.datatypes.units.Percentage
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.IntegerRes

data class BadgeItem(
    @DrawableRes val imageResId: Int,
    @StringRes val nameResId: Int,
    @IntegerRes val progressResId: Int,
    @IntegerRes val maxProgressResId: Int
)
