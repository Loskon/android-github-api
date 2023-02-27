package com.loskon.base.widget.preference

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.google.android.material.slider.Slider
import com.loskon.base.R

@SuppressLint("PrivateResource")
class SliderPreference constructor(
    context: Context,
    attrs: AttributeSet
) : Preference(context, attrs) {

    private var onChangeListener: ((Int) -> Unit)? = null
    private var defaultValue: Int = 0
    private var min: Int = 0
    private var max: Int = 100

    init {
        layoutResource = R.layout.preference_slider
        context.theme.obtainStyledAttributes(attrs, androidx.preference.R.styleable.Preference, 0, 0).apply {
            defaultValue = getInt(androidx.preference.R.styleable.Preference_defaultValue, 0)
        }
        context.theme.obtainStyledAttributes(attrs, R.styleable.SliderPreference, 0, 0).apply {
            min = getInt(R.styleable.SliderPreference_sliderMinValue, MIN_VALUE)
            max = getInt(R.styleable.SliderPreference_sliderMaxValue, MAX_VALUE)
        }
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        holder.itemView.isClickable = false

        val savedValue = sharedPreferences?.getInt(key, defaultValue) ?: 0

        val slider = holder.findViewById(R.id.slider_preference) as Slider
        val tvSliderValue = holder.findViewById(R.id.tv_value_slider_preference) as TextView

        slider.value = savedValue.toFloat()
        slider.valueFrom = min.toFloat()
        slider.valueTo = max.toFloat()
        tvSliderValue.text = savedValue.toString()

        slider.addOnChangeListener(
            Slider.OnChangeListener { _, value: Float, _ ->
                val currentValue = value.toInt()
                sharedPreferences?.edit()?.putInt(key, currentValue)?.apply()
                tvSliderValue.text = currentValue.toString()
                onChangeListener?.invoke(currentValue)
            }
        )
    }

    fun setOnChangeListener(onChangeListener: ((Int) -> Unit)?) {
        this.onChangeListener = onChangeListener
    }

    companion object {
        private const val MIN_VALUE = 0
        private const val MAX_VALUE = 100
    }
}