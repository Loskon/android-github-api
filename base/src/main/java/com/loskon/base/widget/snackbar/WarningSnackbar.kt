package com.loskon.base.widget.snackbar

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.loskon.base.R
import com.loskon.base.extension.context.getColorKtx

class WarningSnackbar : BaseSnackbar() {

    fun make(
        view: View,
        anchorView: View,
        message: String,
        success: Boolean
    ): WarningSnackbar {
        make(view, message, Snackbar.LENGTH_LONG)
        setAnchorView(anchorView)
        setActionTextColor(Color.WHITE)
        setBackgroundColor(view.context.getColorKtx(getSuccessColorId(success)))
        return this
    }

    private fun getSuccessColorId(success: Boolean): Int {
        return if (success) {
            R.color.bg_success
        } else {
            R.color.bg_error
        }
    }
}