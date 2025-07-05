package com.loskon.base.widget.swiprerefreshlayout

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.loskon.base.R

class StyledSwipeRefreshLayout(
    context: Context,
    attrs: AttributeSet
) : SwipeRefreshLayout(context, attrs) {

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshLayout, 0, 0).apply {
            val progressBackgroundColor = getInt(R.styleable.SwipeRefreshLayout_progressBackgroundColor, 0)
            val schemeColors = getInt(R.styleable.SwipeRefreshLayout_schemeColors, 0)

            setProgressBackgroundColorSchemeColor(progressBackgroundColor)
            setColorSchemeColors(schemeColors)

            recycle()
        }
    }
}