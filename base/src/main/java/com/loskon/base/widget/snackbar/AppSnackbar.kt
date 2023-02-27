package com.loskon.base.widget.snackbar

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.loskon.base.R
import com.loskon.base.extension.context.getColorKtx
import com.loskon.base.extension.context.getFontKtx

class AppSnackbar : BaseSnackbar() {

    fun make(
        view: View,
        anchorView: View?,
        message: String?,
        success: Boolean,
    ): AppSnackbar {
        make(view, message, Snackbar.LENGTH_LONG)
        if (anchorView != null) setAnchorView(anchorView)
        setBackgroundTintList(view.context.getColorKtx(getSuccessColorId(success)))
        setFont(view.context.getFontKtx(R.font.roboto_light))
        setTextColor(Color.WHITE)
        return this
    }

    private fun getSuccessColorId(success: Boolean): Int {
        return if (success) {
            R.color.success_color
        } else {
            R.color.error_color
        }
    }
}