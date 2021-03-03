package xh.zero.test.launcher

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

class ItemOptionView : FrameLayout {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    private inner class OverlayView : View {
        constructor(context: Context) : super(context) {
            setWillNotDraw(false)
        }

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            if (event == null) {
                return super.onTouchEvent(event)
            }
            return true
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
        }
    }
}