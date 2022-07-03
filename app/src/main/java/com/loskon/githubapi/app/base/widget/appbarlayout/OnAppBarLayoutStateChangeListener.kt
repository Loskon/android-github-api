package com.loskon.githubapi.app.base.widget.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

enum class AppBarLayoutState {
    EXPANDED,
    COLLAPSED,
    IDLE
}

class OnAppBarLayoutStateChangeListener(
    val onStateChanged: (appBarLayout: AppBarLayout?, appBarLayoutState: AppBarLayoutState?) -> Unit
) : OnOffsetChangedListener {

    private var currentState = AppBarLayoutState.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        currentState = when {
            offset == 0 -> {
                if (currentState != AppBarLayoutState.EXPANDED) {
                    onStateChanged(appBarLayout, AppBarLayoutState.EXPANDED)
                }
                AppBarLayoutState.EXPANDED
            }
            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (currentState != AppBarLayoutState.COLLAPSED) {
                    onStateChanged(appBarLayout, AppBarLayoutState.COLLAPSED)
                }
                AppBarLayoutState.COLLAPSED
            }
            else -> {
                if (currentState != AppBarLayoutState.IDLE) {
                    onStateChanged(appBarLayout, AppBarLayoutState.IDLE)
                }
                AppBarLayoutState.IDLE
            }
        }
    }
}

fun AppBarLayout.setOnOffsetChangedListener(
    onStateChanged: (appBarLayout: AppBarLayout?, appBarLayoutState: AppBarLayoutState?) -> Unit
) {
    addOnOffsetChangedListener(OnAppBarLayoutStateChangeListener(onStateChanged))
}