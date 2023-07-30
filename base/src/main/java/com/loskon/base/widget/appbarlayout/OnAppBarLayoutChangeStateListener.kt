package com.loskon.base.widget.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

enum class AppBarLayoutState {
    EXPANDED,
    COLLAPSED,
    IDLE
}

class OnAppBarLayoutChangeStateListener(
    val onChangeState: (appBarLayoutState: AppBarLayoutState?) -> Unit
) : OnOffsetChangedListener {

    private var state = AppBarLayoutState.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        state = when {
            offset == 0 -> {
                if (state != AppBarLayoutState.EXPANDED) onChangeState(AppBarLayoutState.EXPANDED)
                AppBarLayoutState.EXPANDED
            }
            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (state != AppBarLayoutState.COLLAPSED) onChangeState(AppBarLayoutState.COLLAPSED)
                AppBarLayoutState.COLLAPSED
            }
            else -> {
                if (state != AppBarLayoutState.IDLE) onChangeState(AppBarLayoutState.IDLE)
                AppBarLayoutState.IDLE
            }
        }
    }
}

fun AppBarLayout.setChangeStateListener(
    onChangeState: (appBarLayoutState: AppBarLayoutState?) -> Unit
) {
    addOnOffsetChangedListener(OnAppBarLayoutChangeStateListener(onChangeState))
}