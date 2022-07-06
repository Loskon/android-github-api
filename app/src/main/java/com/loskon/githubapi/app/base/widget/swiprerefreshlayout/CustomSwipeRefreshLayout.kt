package com.loskon.githubapi.app.base.widget.swiprerefreshlayout

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.loskon.githubapi.R

class CustomSwipeRefreshLayout(
    context: Context,
    attrs: AttributeSet
) : SwipeRefreshLayout(context, attrs) {

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshLayout, 0, 0).apply {
            val progressBackgroundColor = getInt(R.styleable.SwipeRefreshLayout_progressBackgroundColor, 0)
            setProgressBackgroundColorSchemeColor(progressBackgroundColor)

            val schemeColors = getInt(R.styleable.SwipeRefreshLayout_schemeColors, 0)
            setColorSchemeColors(schemeColors)

            recycle()
        }
    }
}