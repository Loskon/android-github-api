package com.loskon.githubapi.base.presentation.dialogfragment

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.loskon.githubapi.R
import com.loskon.githubapi.base.extension.fragment.getColor
import com.loskon.githubapi.base.extension.fragment.getColorControlHighlight
import com.loskon.githubapi.base.extension.fragment.getFont
import com.loskon.githubapi.base.widget.snackbar.CustomSnackbar

open class BaseSnackbarFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var snackbar: CustomSnackbar? = null

    fun showTextSnackbar(
        view: View,
        anchorView: View,
        message: String?
    ) {
        snackbar = createStylizedSnackbar(view, anchorView, message, Snackbar.LENGTH_LONG)
        snackbar?.show()
    }

    fun showActionSnackbar(
        view: View,
        anchorView: View,
        message: String?,
        action: () -> Unit
    ) {
        snackbar = createStylizedActionSnackbar(view, anchorView, message, action)
        snackbar?.show()
    }

    private fun createStylizedSnackbar(
        view: View,
        anchorView: View,
        message: String?,
        length: Int
    ): CustomSnackbar {
        return CustomSnackbar().create {
            make(view, message, length)
            setAnchorView(anchorView)
            setBackgroundTintList(getColor(R.color.sienna))
            setTextColor(Color.WHITE)
            setFont(getFont(R.font.roboto_light))
        }
    }

    private fun createStylizedActionSnackbar(
        view: View,
        anchorView: View,
        message: String?,
        action: () -> Unit
    ): CustomSnackbar {
        return createStylizedSnackbar(view, anchorView, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.retry)) { action() }
            setActionRippleColor(getColorControlHighlight)
            setActionStroke(2, Color.WHITE)
        }
    }
}