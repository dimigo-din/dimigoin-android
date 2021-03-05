package `in`.dimigo.dimigoin.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

class SwipeLayoutForHorizontal(context: Context, attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {
    private var touchSlop = 0
    private var prevX = 0.0f

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val motionEvent = MotionEvent.obtain(event)
                prevX = motionEvent.x
                motionEvent.recycle()
            }
            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff: Float = abs(eventX - prevX)

                if (xDiff > touchSlop) {
                    return false
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }
}
