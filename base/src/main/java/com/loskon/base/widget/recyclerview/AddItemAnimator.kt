package com.loskon.base.widget.recyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Bypass ripple effect bug for CardView
 */
class AddItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        val view = holder.itemView

        endAnimation(holder)
        view.alpha = 0f

        val animation = view.animate()
        animation.alpha(1.0f).setDuration(0).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animator: Animator) {
                dispatchAddStarting(holder)
            }

            override fun onAnimationCancel(animator: Animator) {
                view.alpha = 1.0f
            }

            override fun onAnimationEnd(animator: Animator) {
                animation.setListener(null)
                dispatchAddFinished(holder)
            }
        }).start()

        return true
    }
}