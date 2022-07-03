package com.loskon.githubapi.app.base.widget.appbarlayout

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout

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