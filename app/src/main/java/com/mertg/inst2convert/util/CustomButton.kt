package com.mertg.inst2convert.util

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mertg.inst2convert.R

class CustomButton : ConstraintLayout {
    private var textValue: String? = ""

    lateinit var customButtonText: TextView
    lateinit var nextIcon: ImageView
    lateinit var bgCustomButton: ConstraintLayout

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initialize(context, attrs)
        inflate()
    }

    private fun inflate() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_custom_button, this, true)
        customButtonText = view.findViewById(R.id.customButtonText)
        nextIcon = view.findViewById(R.id.iv_next)
        bgCustomButton = view.findViewById(R.id.bg_custom_button)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomButton, 0, 0
        )
        try {
            if (typedArray.hasValue(R.styleable.CustomButton_cb_textValue)) {
                textValue = typedArray.getString(R.styleable.CustomButton_cb_textValue)
            }
        } finally {
            typedArray.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    private fun init() {
        setTextValue()
    }

    private fun setTextValue() {
        customButtonText.text = textValue
    }

    fun setTextValue(value: String) {
        customButtonText.text = value
    }

    fun setTextWithTL(value: String): CustomButton {
        val valueWithTL = "₺$value"
        customButtonText.text = valueWithTL
        return this
    }

    fun hideNextIcon(): CustomButton {
        nextIcon.visibility = GONE
        return this
    }

    fun alignTextToCenter(): CustomButton {
        val layoutParams = customButtonText.layoutParams as LayoutParams
        layoutParams.width = LayoutParams.MATCH_PARENT
        customButtonText.layoutParams = layoutParams
        customButtonText.gravity = Gravity.CENTER
        return this
    }

    fun setButtonTextPadding(left: Int, top: Int, right: Int, bottom: Int): CustomButton {
        customButtonText.setPadding(left, top, right, bottom)
        return this
    }

    fun setTextColor(colorResId: Int): CustomButton {
        val color = ContextCompat.getColor(context, colorResId)
        customButtonText.setTextColor(color)
        return this
    }

    fun setCustomButtonBackground(resourceId: Int): CustomButton {
        bgCustomButton.setBackgroundResource(resourceId)
        return this
    }

    fun setCustomButtonBackgroundColor(colorResId: Int): CustomButton {
        val color = ContextCompat.getColor(context, colorResId)
        bgCustomButton.setBackgroundColor(color)
        return this
    }

    fun setCustomButtonBackgroundDrawable(drawableResId: Int): CustomButton {
        bgCustomButton.setBackgroundResource(drawableResId)
        return this
    }

    fun setTextSize(size: Float): CustomButton {
        customButtonText.textSize = size
        return this
    }

    fun quickBaseButtonBlack(): CustomButton{
        alignTextToCenter().hideNextIcon().setTextColor(R.color.white_1).setCustomButtonBackgroundDrawable(R.drawable.bg_general_buttons_black)
        return this
    }

    fun quickBaseButtonWhite(): CustomButton{
        alignTextToCenter().hideNextIcon().setTextColor(R.color.black).setCustomButtonBackgroundDrawable(R.drawable.bg_general_buttons_white_with_stroke)
        return this
    }

    fun enableButton(): CustomButton {
        isEnabled = true
        bgCustomButton.alpha = 1f
        customButtonText.alpha = 1f
        nextIcon.alpha = 1f
        return this
    }

    fun disableButton(): CustomButton {
        isEnabled = false
        bgCustomButton.alpha = 0.5f
        customButtonText.alpha = 0.5f
        nextIcon.alpha = 0.5f
        return this
    }

}
