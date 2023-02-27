package com.loskon.base.widget.appbarlayout

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout

/**
 * Disable scroll AppBarLayout when dragging
 */
class AppBarLayoutDragBehavior(
    context: Context,
    attrs: AttributeSet
) : AppBarLayout.Behavior(context, attrs) {

    init {
        setDragCallback(object : DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean = false
        })
    }
}