package `in`.dimigo.dimigoin.ui.custom

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomAccentSpan(private val typeface: Typeface?, private val color: Int) : MetricAffectingSpan() {
    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
        paint.color = color
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
        paint.color = color
    }
}
