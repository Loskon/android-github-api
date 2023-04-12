package com.loskon.features.theme

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.loskon.features.utils.AppPreference

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

private object AppRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = Color.DarkGray

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        //Color(LocalContext.current.getControlHighlightColorKtx()),
        Color.Black,
        lightTheme = isSystemInDarkTheme().not()
    )
}

@Composable
fun GitHubTheme(
    darkTheme: Boolean = AppPreference.getHasDarkMode(LocalContext.current),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            LocalRippleTheme provides AppRippleTheme,
            content = content
        )
    }
}