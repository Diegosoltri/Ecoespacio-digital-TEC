package mx.a01736935.greenify.carruser


import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
internal fun SetupTransparentSystemUi(
    systemUiController: SystemUiController = rememberSystemUiController(),
    actualBackgroundColor: Color
) {
    val minLuminanceForDarkIcons = .5f
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = actualBackgroundColor.luminance() > minLuminanceForDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = actualBackgroundColor.luminance() > minLuminanceForDarkIcons,
            navigationBarContrastEnforced = false
        )
    }
}