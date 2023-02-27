package com.loskon.base.widget.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

enum class AppBarLayoutState {
    EXPANDED,
    COLLAPSED,
    IDLE
}

class OnAppBarLayoutStateChangeListener(
    val onStateChanged: (appBarLayoutState: AppBarLayoutState?) -> Unit
) : OnOffsetChangedListener {

    private var state = AppBarLayoutState.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        state = when {
            offset == 0 -> {
                if (state != AppBarLayoutState.EXPANDED) {
                    onStateChanged(AppBarLayoutState.EXPANDED)
                }
                AppBarLayoutState.EXPANDED
            }
            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (state != AppBarLayoutState.COLLAPSED) {
                    onStateChanged(AppBarLayoutState.COLLAPSED)
                }
                AppBarLayoutState.COLLAPSED
            }
            else -> {
                if (state != AppBarLayoutState.IDLE) {
                    onStateChanged(AppBarLayoutState.IDLE)
                }
                AppBarLayoutState.IDLE
            }
        }
    }
}

fun AppBarLayout.setOnStateChangedListener(
    onStateChanged: (appBarLayoutState: AppBarLayoutState?) -> Unit
) {
    addOnOffsetChangedListener(OnAppBarLayoutStateChangeListener(onStateChanged))
}