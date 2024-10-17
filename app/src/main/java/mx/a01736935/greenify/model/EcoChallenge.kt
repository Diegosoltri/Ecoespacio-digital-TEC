package mx.a01736935.greenify.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class EcoChallenge(
    @DrawableRes val imageResId: Int,
    @StringRes val nameResId: Int,
    @StringRes val locationResId: Int,
    @StringRes val bountyResId: Int
)

