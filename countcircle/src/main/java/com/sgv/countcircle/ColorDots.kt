package com.sgv.countcircle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*

class ColorDots : View {

    var dotRadius: Float
    var minimumDotGap: Float
    var orientation = Orientation.HORIZONTAL
    val paint: Paint = Paint()
    var colorCodeList = ArrayList<Int>()
    var isMultiColorDot=false

    constructor(context: Context?) : this(context, null) {

    }

    constructor(context: Context?, attrs: AttributeSet) : this(context, attrs, 0) {

    }

    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val metrics = resources.displayMetrics
        val twoDpDefault = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, metrics)
        val defaultBlack = Color.argb(255, 0, 0, 0)

        if (attrs != null) {
            val typedArray = context?.theme?.obtainStyledAttributes(attrs, R.styleable.ColorDots, defStyleAttr, 0)
            dotRadius = typedArray?.getDimension(R.styleable.ColorDots_dotRadius, twoDpDefault) ?: twoDpDefault
            minimumDotGap =
                typedArray?.getDimension(R.styleable.ColorDots_minimumDotGap, twoDpDefault) ?: twoDpDefault
            paint.color = typedArray?.getColor(R.styleable.ColorDots_dotColor, defaultBlack) ?: defaultBlack
            isMultiColorDot =typedArray?.getBoolean(R.styleable.ColorDots_isMultiColorDot,isMultiColorDot) ?:false
            val orientationOrdinal =
                typedArray?.getInt(R.styleable.ColorDots_orientationType, Orientation.HORIZONTAL.ordinal)
                    ?: Orientation.HORIZONTAL.ordinal
            if (orientationOrdinal == Orientation.VERTICAL.ordinal) {
                orientation = Orientation.VERTICAL
            } else {
                orientation = Orientation.HORIZONTAL
            }
            typedArray?.recycle()
        } else {
            dotRadius = twoDpDefault
            minimumDotGap = twoDpDefault
            paint.color = defaultBlack
        }

        paint.style = Paint.Style.FILL
        paint.flags = Paint.ANTI_ALIAS_FLAG
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (orientation == Orientation.HORIZONTAL) {
            val widthNeeded = paddingLeft + paddingRight + suggestedMinimumWidth
            val width = resolveSize(widthNeeded, widthMeasureSpec)

            val heightNeeded = paddingTop + paddingBottom + 2 * dotRadius
            val height = resolveSize(heightNeeded.toInt(), heightMeasureSpec)

            setMeasuredDimension(width, height)
        } else {
            val widthNeeded = paddingLeft + paddingRight + 2 * dotRadius
            val width = resolveSize(widthNeeded.toInt(), widthMeasureSpec)

            val heightNeeded = paddingTop + paddingBottom + suggestedMinimumHeight
            val height = resolveSize(heightNeeded, heightMeasureSpec)

            setMeasuredDimension(width, height)
        }
    }

    fun setColorCode(colorCodeList: ArrayList<Int>) {
        this.colorCodeList.clear()
        this.colorCodeList = colorCodeList
        isMultiColorDot=true
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (orientation == Orientation.HORIZONTAL) {
            val w = canvas.width - paddingLeft - paddingRight
            val d = 2 * dotRadius
            val m = minimumDotGap
            val c: Int = Math.floor(((w - d) / (d + m)).toDouble()).toInt()
            val g: Float = (w - (d * (c + 1))) / c
            var dotLength: Int = if (isMultiColorDot) colorCodeList.size -1 else c
            for (i in 0.. dotLength) {
                paint.color= if (isMultiColorDot) colorCodeList.get(i) else paint.color
                canvas.drawCircle(
                    paddingLeft + dotRadius + i * (d + g),
                    paddingTop + dotRadius,
                    dotRadius,
                    paint
                )
            }
        } else {
            val h = (canvas.height - paddingTop - paddingBottom).toFloat()
            val d = 2 * dotRadius
            val m = minimumDotGap
            val c: Int = Math.floor(((h - d) / (d + m)).toDouble()).toInt()
            val g: Float = (h - (d * (c + 1))) / c
            var dotLength: Int = if (isMultiColorDot) colorCodeList.size-1 else c
            for (i in 0..dotLength) {
                paint.color= if (isMultiColorDot) colorCodeList.get(i) else paint.color
                canvas.drawCircle(
                    paddingLeft + dotRadius,
                    paddingTop + dotRadius + i * (d + g),
                    dotRadius,
                    paint
                )
            }
        }
    }

    enum class Orientation {
        HORIZONTAL, VERTICAL
    }
}