package com.loskon.base.customtab

import android.content.Context
import android.graphics.Color
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.loskon.base.R

/**
 * Android Custom Tabs for color consistency with app
 * and entrance and exit animations
 */
object CustomTab {

    fun launchCustomTab(
        context: Context,
        url: String
    ) {
        val params = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(context.getColor(R.color.bottom_bar_background))
            .setNavigationBarColor(Color.BLACK)
            .build()

        val intent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(params)
            .setUrlBarHidingEnabled(false)
            .setStartAnimations(context, R.anim.enter_slide_in_right, R.anim.exit_slide_out_left)
            .setExitAnimations(context, R.anim.enter_slide_in_left, R.anim.exit_slide_out_right)
            .build()

        intent.launchUrl(context, url.toUri())
    }

    fun isAvailableCustomTab(context: Context): Boolean {
        return CustomTabsClient.getPackageName(context, emptyList<String>()) != null
    }
}