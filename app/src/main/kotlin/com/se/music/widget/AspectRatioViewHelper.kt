package com.se.music.widget

import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.se.music.R
import java.util.regex.Pattern
import kotlin.math.abs

/**
 * Created by gaojin on 2017/12/31.
 */
class AspectRatioViewHelper(private var view: View, attrs: AttributeSet?) {

    companion object {
        private val RATIO_STRING_PATTERN = Pattern.compile("(\\d+):(\\d+)(W|H)?")
    }

    private var ratio: Float = 0f
    private var widthFixed: Boolean = false
    private val outDimension = IntArray(2)

    init {
        val a = view.context.obtainStyledAttributes(attrs, R.styleable.AspectRatioView)
        try {
            val input = a.getString(R.styleable.AspectRatioView_aspectRatio)
            if (!TextUtils.isEmpty(input)) {

                val matcher = RATIO_STRING_PATTERN.matcher(input!!)
                if (!matcher.matches()) {
                    throw IllegalArgumentException("Wrong aspect ratio string format: $input\nYou must specify an aspect ratio like \"16:9W\".")
                } else {
                    try {
                        val width = java.lang.Float.valueOf(matcher.group(1))
                        val height = java.lang.Float.valueOf(matcher.group(2))
                        val fixOptions = matcher.group(3)
                        val widthFixed = TextUtils.isEmpty(fixOptions) || "W" == fixOptions

                        ratio = width / height
                        this.widthFixed = widthFixed
                        if (ratio == 0f) {
                            throw IllegalArgumentException("Illegal aspect ratio: 0")
                        }
                    } catch (nfe: NumberFormatException) {
                        // 本意就是要强行使用者输入正确格式的参数，但 lint 非要让 try-catch，那么就 catch 后再 throw 出来
                        throw IllegalArgumentException(nfe)
                    }
                }
            } else {
                ratio = 0f
            }
        } finally {
            a.recycle()
        }
    }

    fun calculateDimension(measuredWidth: Int, measuredHeight: Int): IntArray {
        val ratio = this.ratio

        if (abs(ratio - 0f) >= 0.000001f) {
            if (widthFixed) {
                outDimension[0] = measuredWidth
                outDimension[1] = (measuredWidth / ratio).toInt()
            } else {
                outDimension[0] = (measuredHeight * ratio).toInt()
                outDimension[1] = measuredHeight
            }
        } else {
            outDimension[0] = measuredWidth
            outDimension[1] = measuredHeight
        }
        return outDimension
    }

    fun setAspectRatio(widthFactor: Float, heightFactor: Float, widthFixed: Boolean) {
        ratio = widthFactor / heightFactor
        this.widthFixed = widthFixed
        if (ratio == 0f) {
            throw IllegalArgumentException("Illegal aspect ratio: 0")
        }
        view.requestLayout()
    }
}