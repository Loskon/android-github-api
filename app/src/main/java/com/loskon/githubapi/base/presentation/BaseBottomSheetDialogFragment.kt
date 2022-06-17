package com.loskon.githubapi.base.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loskon.githubapi.R
import timber.log.Timber

open class BaseBottomSheetDialogFragment(@LayoutRes val layoutId: Int = 0) : BottomSheetDialogFragment() {

    open val skipCollapsedState = true
    open val isDraggableState = true
    open val isHideableState = true

    var animation: Int? = null

    override fun getTheme(): Int {
        return R.style.RoundedSheetDialogStyle
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheetDialog.behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = skipCollapsedState
            isDraggable = isDraggableState
            isHideable = isHideableState
        }

        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layoutId != 0) {
            inflater.inflate(layoutId, container, false)
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated: " + animation)
        animation = dialog?.window?.attributes?.windowAnimations
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart: " + animation)
        animation?.let { dialog?.window?.setWindowAnimations(it) }
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop: " +  animation)
        dialog?.window?.setWindowAnimations(-1)
    }

    /** stop Bottom sheet enter animation when returning to screen*/
    /*    override fun onStop() {
            super.onStop()
            dialog?.show()
        }*/
}