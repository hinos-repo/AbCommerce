package com.hinos.abcommerce.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.hinos.abcommerce.R

class CornerTextView : AppCompatTextView
{
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        setAttrShape(attrs)
    }

    private fun setAttrShape(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HinosCorner)

        val shape = background?.let {
            return@let if (it is ColorDrawable)
            {
                GradientDrawable().apply {
                    setColor(it.color)
                }
            } else {
                GradientDrawable()
            }
        } ?: run {
            GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                getGradientColors(typedArray)
            )
        }

        val strokeWidth = typedArray.getDimension(R.styleable.HinosCorner_strokeWidth, 0f).toInt()
        @ColorInt val strokeColor = typedArray.getColor(R.styleable.HinosCorner_strokeColor, Color.parseColor("#00ffffff"))
        shape.setStroke(strokeWidth, strokeColor)
        val radius = typedArray.getDimension(R.styleable.HinosCorner_cornerRadius, 0f)
        shape.cornerRadius = radius
        background = shape
    }

    private fun getGradientColors(typedArray: TypedArray): IntArray {
        @ColorInt val gradientTopColor = typedArray.getColor(
            R.styleable.HinosCorner_gradientTopColor,
            Color.parseColor("#00ffffff")
        )
        @ColorInt val gradientMiddleColor = typedArray.getColor(
            R.styleable.HinosCorner_gradientMiddleColor,
            Color.parseColor("#00ffffff")
        )
        @ColorInt val gradientBottomColor = typedArray.getColor(
            R.styleable.HinosCorner_gradientBottomColor,
            Color.parseColor("#00ffffff")
        )
        return intArrayOf(
            gradientTopColor, gradientMiddleColor, gradientBottomColor
        )
    }
}