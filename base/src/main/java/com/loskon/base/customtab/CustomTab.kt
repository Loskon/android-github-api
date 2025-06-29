package com.loskon.base.customtab

import android.content.Context
import android.graphics.Color
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.loskon.base.R

object CustomTab {

    fun launchCustomTab(context: Context, url: String) {
        context.apply {
            val params = CustomTabColorSchemeParams.Builder()
                .setToolbarColor(context.getColor(R.color.bottom_bar_background_color))
                .setNavigationBarColor(Color.BLACK)
                .build()

            val intent = CustomTabsIntent.Builder()
                .setDefaultColorSchemeParams(params)
                .setUrlBarHidingEnabled(false)
                .setStartAnimations(this, R.anim.enter_slide_in_right, R.anim.exit_slide_out_left)
                .setExitAnimations(this, R.anim.enter_slide_in_left, R.anim.exit_slide_out_right)
                .build()

            intent.launchUrl(this, url.toUri())
        }
    }

    fun isAvailableCustomTab(context: Context) = CustomTabsClient.getPackageName(context, emptyList<String>()) != null
}