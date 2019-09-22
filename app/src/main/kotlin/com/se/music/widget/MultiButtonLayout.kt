package com.se.music.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2018/10/11 下午5:00
 */

class MultiButtonLayout @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val DEFAULT_BUTTON_COUNT = 0
        private const val DEFAULT_BUTTON_RADIUS = 8
    }

    private var buttonCount = DEFAULT_BUTTON_COUNT
    private var buttonRadius = DEFAULT_BUTTON_RADIUS

    private var buttonSolid: ColorStateList?
    private var buttonSelectedColor: ColorStateList?

    private var buttonMarginLeft = 0
    private var buttonMarginRight = 0

    private lateinit var gradientDrawable: Drawable
    private lateinit var selectedGradientDrawable: Drawable
    private lateinit var params: LayoutParams

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultiButtonLayout, defStyleAttr, 0)
        buttonCount = a.getInt(R.styleable.MultiButtonLayout_button_count, DEFAULT_BUTTON_COUNT)
        buttonRadius = a.getDimensionPixelOffset(R.styleable.MultiButtonLayout_button_radius, DEFAULT_BUTTON_RADIUS)
        buttonSolid = a.getColorStateList(R.styleable.MultiButtonLayout_button_solid)
        buttonSelectedColor = a.getColorStateList(R.styleable.MultiButtonLayout_button_selected_color)
        buttonMarginLeft = a.getDimensionPixelOffset(R.styleable.MultiButtonLayout_buttonMarginLeft, 0)
        buttonMarginRight = a.getDimensionPixelOffset(R.styleable.MultiButtonLayout_buttonMarginRight, 0)
        a.recycle()
        init()
    }

    private fun init() {
        gradientDrawable = generateGradientDrawable(false)
        selectedGradientDrawable = generateGradientDrawable(true)

        params = LayoutParams(buttonRadius * 2, buttonRadius * 2)
        params.rightMargin = buttonMarginRight
        params.leftMargin = buttonMarginLeft

        if (buttonCount > 0) {
            for (i in 0 until buttonCount) {
                addView(generateView())
            }
        }
    }

    private fun generateView(): View {
        val view = View(context)
        view.layoutParams = params
        view.background = generateStateListDrawable()
        return view
    }

    private fun generateStateListDrawable(): Drawable {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), selectedGradientDrawable)
        stateListDrawable.addState(IntArray(0), gradientDrawable)
        return stateListDrawable
    }

    private fun generateGradientDrawable(isSelected: Boolean): Drawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setSize(buttonRadius * 2, buttonRadius * 2)
        gradientDrawable.shape = GradientDrawable.OVAL
        if (isSelected) {
            gradientDrawable.color = buttonSelectedColor
        } else {
            gradientDrawable.color = buttonSolid
        }
        return gradientDrawable
    }

    fun setSelectedChild(index: Int) {
        for (i in 0 until childCount) {
            if (index < childCount) {
                val child = getChildAt(i)
                child.isSelected = i == index
            }
        }
    }
}