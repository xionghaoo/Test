package xh.zero.test.launcher

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import xh.zero.test.R

/**
 * ACTION_DOWN = 0
 * ACTION_UP = 1
 * ACTION_MOVE = 2
 */
class ItemOptionView : FrameLayout {

    private var _dragging = false
    private val _dragLocation = PointF()
    private var _overlayPopup: RecyclerView = RecyclerView(context)
    private val _slideInLeftAnimator = SlideInLeftAnimator(AccelerateDecelerateInterpolator())
    private val _slideInRightAnimator = SlideInRightAnimator(AccelerateDecelerateInterpolator())
    private val _overlayPopupAdapter = FastItemAdapter<PopupIconLabelItem>()
    private var _overlayPopupShowing: Boolean = false

    private val uninstallItemIdentifier = 83L
    private val infoItemIdentifier = 84L
    private val editItemIdentifier = 85L
    private val removeItemIdentifier = 86L
    private val resizeItemIdentifier = 87L

    private val _overlayView = OverlayView(context)

    private val uninstallItem = PopupIconLabelItem(R.string.uninstall, R.drawable.ic_delete).withIdentifier(
        uninstallItemIdentifier
    )
    private val infoItem = PopupIconLabelItem(R.string.info, R.drawable.ic_info).withIdentifier(
        infoItemIdentifier
    )
    private val editItem = PopupIconLabelItem(R.string.edit, R.drawable.ic_edit).withIdentifier(
        editItemIdentifier
    )
    private val removeItem = PopupIconLabelItem(R.string.remove, R.drawable.ic_close).withIdentifier(
        removeItemIdentifier
    )

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        _overlayPopup.visibility = View.INVISIBLE
        _overlayPopup.alpha = 0f;
        _overlayPopup.overScrollMode = OVER_SCROLL_NEVER
        _overlayPopup.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        _overlayPopup.itemAnimator = _slideInRightAnimator
        _overlayPopup.adapter = _overlayPopupAdapter
        addView(_overlayView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        addView(_overlayPopup, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        setWillNotDraw(false)

    }

    private inner class OverlayView : View {
        constructor(context: Context) : super(context) {
            setWillNotDraw(false)
        }

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            if (event == null || event.actionMasked != 0 || !_overlayPopupShowing) {
                return super.onTouchEvent(event)
            }
            collapse()
            return true
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null && ev.actionMasked == 1 && _dragging) {
            // 手指抬起
        }
        if (_dragging) {
            return true
        }
        if (ev != null) {

        }
        return super.onInterceptTouchEvent(ev)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if (_dragging) {
                _dragLocation.set(event.x, event.y)

            }
        }
        return super.onTouchEvent(event)

    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        _overlayView.bringToFront()
        _overlayPopup.bringToFront()
    }

    private fun showPopupMenuForItem(
        x: Float,
        y: Float,
        popupItem: List<PopupIconLabelItem?>?,
        listener: com.mikepenz.fastadapter.listeners.OnClickListener<PopupIconLabelItem?>?
    ) {
        if (!_overlayPopupShowing) {
            _overlayPopupShowing = true
            _overlayPopup.visibility = VISIBLE
            _overlayPopup.translationX = x
            _overlayPopup.translationY = y
            _overlayPopup.alpha = 1.0f
            _overlayPopupAdapter.add(popupItem)
            _overlayPopupAdapter.withOnClickListener(listener)
        }
    }

    fun showItemPopup() {
        val itemList = ArrayList<PopupIconLabelItem>()
        itemList.add(uninstallItem)
        itemList.add(infoItem)
        itemList.add(editItem)
        itemList.add(removeItem)

        // TODO 计算App图标的坐标
        val x = 0f
        val y = 0f
        showPopupMenuForItem(
            x,
            y,
            itemList,
            com.mikepenz.fastadapter.listeners.OnClickListener<PopupIconLabelItem?> { v, adapter, item, position ->

                true
            }
        )
    }

    fun collapse() {
        if (_overlayPopupShowing) {
            _overlayPopupShowing = false
            _overlayPopup.animate().alpha(0.0f).withEndAction {
                _overlayPopup.visibility = INVISIBLE
                _overlayPopupAdapter.clear()
            }
            if (!_dragging) {
//                _dragView = null
//                _dragItem = null
//                _dragAction = null
            }
        }
    }
}