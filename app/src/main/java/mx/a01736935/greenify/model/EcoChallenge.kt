package mx.a01736935.greenify.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class EcoChallenge(
    @DrawableRes val imageResId: Int,
    @StringRes val nameResId: Int,
    @StringRes val descriptionResId: Int,
    @StringRes val filterResId: Int,
    val id: Int

)