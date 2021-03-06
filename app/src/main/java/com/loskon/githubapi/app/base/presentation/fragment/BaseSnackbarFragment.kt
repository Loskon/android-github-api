package com.loskon.githubapi.app.base.presentation.fragment

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.loskon.githubapi.R
import com.loskon.githubapi.app.base.extension.fragment.controlHighlight
import com.loskon.githubapi.app.base.extension.fragment.getColor
import com.loskon.githubapi.app.base.extension.fragment.getFont
import com.loskon.githubapi.app.base.widget.snackbar.BaseCustomSnackbar

open class BaseSnackbarFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var snackbar: BaseCustomSnackbar? = null

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
    ): BaseCustomSnackbar {
        return BaseCustomSnackbar().create {
            make(view, message, length)
            setAnchorView(anchorView)
            setBackgroundTintList(getColor(R.color.error_color))
            setTextColor(Color.WHITE)
            setFont(getFont(R.font.roboto_light))
        }
    }

    private fun createStylizedActionSnackbar(
        view: View,
        anchorView: View,
        message: String?,
        action: () -> Unit
    ): BaseCustomSnackbar {
        return createStylizedSnackbar(view, anchorView, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.retry)) { action() }
            setActionRippleColor(controlHighlight)
            setActionStroke(2, Color.WHITE)
        }
    }
}