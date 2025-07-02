package com.loskon.features.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.loskon.base.R
import com.loskon.features.util.preference.AppPreference

@Composable
private fun darkColorPalette() = darkColors(
    primary = colorResource(id = R.color.material_blue),
    primaryVariant = colorResource(id = R.color.material_blue),
    secondary = colorResource(id = R.color.material_blue)
)

@Composable
private fun lightColorPalette() = lightColors(
    primary = colorResource(id = R.color.material_blue),
    primaryVariant = colorResource(id = R.color.material_blue),
    secondary = colorResource(id = R.color.material_blue)
)

@Composable
fun GitHubTheme(
    darkTheme: Boolean = AppPreference.getHasDarkMode(LocalContext.current),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette()
    } else {
        lightColorPalette()
    }

    MaterialTheme(
        colors = colors
    ) {

    }
}