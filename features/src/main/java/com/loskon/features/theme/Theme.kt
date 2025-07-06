package com.loskon.features.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.loskon.base.R
import com.loskon.features.util.preference.AppPref

@Composable
private fun darkColorPalette() = darkColors(
    primary = colorResource(id = R.color.primary_app_color),
    primaryVariant = colorResource(id = R.color.primary_app_color),
    secondary = colorResource(id = R.color.primary_app_color)
)

@Composable
private fun lightColorPalette() = lightColors(
    primary = colorResource(id = R.color.primary_app_color),
    primaryVariant = colorResource(id = R.color.primary_app_color),
    secondary = colorResource(id = R.color.primary_app_color)
)

val Typography = _root_ide_package_.androidx.compose.material.Typography(
    defaultFontFamily = FontFamily(Font(R.font.roboto_light))
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GitHubTheme(
    darkTheme: Boolean = AppPref.hasDarkMode(LocalContext.current),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette()
    } else {
        lightColorPalette()
    }

    MaterialTheme(
        colors = colors,
        typography = Typography
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            content = content
        )
    }
}