package com.loskon.base.widget.snackbar

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout

/**
 * Allows to create a custom snackbar
 */
@Suppress("unused", "ktlint:standard:wrapping")
@SuppressLint("RestrictedApi")
open class BaseSnackbar {

    private var snackbar: Snackbar? = null
    private var onShowListener: (() -> Unit)? = null
    private var onDismissedListener: (() -> Unit)? = null

    fun make(view: View, message: String, length: Int): BaseSnackbar {
        snackbar = Snackbar.make(view, message, length)
        addSnackbarTransientListener()
        return this
    }

    fun make(context: Context, view: View, message: String, length: Int): BaseSnackbar {
        snackbar = Snackbar.make(context, view, message, length)
        addSnackbarTransientListener()
        return this
    }

    fun make(view: View, addedView: View, length: Int): BaseSnackbar {
        snackbar = Snackbar.make(view, "", length)
        (snackbar?.view as Snackbar.SnackbarLayout?)?.addView(addedView)
        addSnackbarTransientListener()
        return this
    }

    private fun addSnackbarTransientListener() {
        snackbar?.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                onShowListener?.invoke()
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                onDismissedListener?.invoke()
                snackbar = null
            }
        })
    }

    fun setAnchorView(anchorView: View): BaseSnackbar {
        snackbar?.anchorView = anchorView
        return this
    }

    fun setAction(text: String, action: () -> Unit): BaseSnackbar {
        snackbar?.setAction(text) {
            action()
            dismiss()
        }
        return this
    }

    fun setBackgroundColor(color: Int) {
        snackbar?.view?.backgroundTintList = ColorStateList.valueOf(color)
    }

    fun setActionTextColor(color: Int) {
        snackbarButton?.setTextColor(color)
    }

    fun enableHideByClickSnackbar() {
        snackbar?.view?.setOnClickListener { dismiss() }
    }

    fun show() = snackbar?.show()

    fun dismiss() = snackbar?.dismiss()

    fun setOnShowListener(onShowListener: (() -> Unit)? = null) {
        this.onShowListener = onShowListener
    }

    fun setOnDismissedListener(onDismissedListener: (() -> Unit)? = null) {
        this.onDismissedListener = onDismissedListener
    }

    val snackbarTextView
        get() = ((snackbar?.view as Snackbar.SnackbarLayout?)
            ?.getChildAt(0) as SnackbarContentLayout?)
            ?.getChildAt(0) as TextView?

    val snackbarButton
        get() = ((snackbar?.view as Snackbar.SnackbarLayout?)
            ?.getChildAt(0) as SnackbarContentLayout?)
            ?.getChildAt(1) as MaterialButton?

    inline fun create(functions: BaseSnackbar.() -> Unit): BaseSnackbar {
        this.functions()
        return this
    }
}