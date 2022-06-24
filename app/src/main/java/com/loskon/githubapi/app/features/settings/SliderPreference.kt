package com.loskon.githubapi.app.features.settings

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.google.android.material.slider.Slider
import com.loskon.githubapi.R
import com.loskon.githubapi.databinding.PreferenceSliderBinding
import timber.log.Timber

@SuppressLint("PrivateResource")
class SliderPreference constructor(
    context: Context,
    attrs: AttributeSet
) : Preference(context, attrs) {

    private val binding = PreferenceSliderBinding.inflate(LayoutInflater.from(context))
    private var onChangeListener: ((Int) -> Unit)? = null
    private var defaultValue: Int = 0

    init {
        layoutResource = R.layout.preference_slider
        context.theme.obtainStyledAttributes(attrs, androidx.preference.R.styleable.Preference, 0, 0).apply {
            defaultValue = getInt(androidx.preference.R.styleable.Preference_defaultValue, 0)
        }
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        holder.itemView.isClickable = false

        val slider = holder.findViewById(binding.slider.id) as Slider
        val tvSliderValue = holder.findViewById(binding.tvSliderValue.id) as TextView

        val savedValue = sharedPreferences?.getInt(key, defaultValue) ?: 0
        slider.value = savedValue.toFloat()
        tvSliderValue.text = savedValue.toString()

        slider.addOnChangeListener(Slider.OnChangeListener { _, value: Float, _ ->
            Timber.d(value.toString())
            tvSliderValue.text = value.toInt().toString()
            sharedPreferences?.edit()?.putInt(key, value.toInt())?.apply()
            onChangeListener?.invoke(value.toInt())
        })
    }

    fun setOnChangeListenerListener(onChangeListener: ((Int) -> Unit)?) {
        this.onChangeListener = onChangeListener
    }
}