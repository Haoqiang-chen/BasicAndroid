package cc.aiknow.basicandroid.androidimage

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import java.io.InputStream

/**
 * @Description: 大图查看控件，上下滑动
 * 目前存在问题：卡顿
 * @Author chenhaoqiang
 * @Since 2022/8/31
 * @Version 1.0
 */
class BigView : View, GestureDetector.OnGestureListener, View.OnTouchListener {

    /**
     * 分块加载
     */
    private lateinit var mRect: Rect

    /**
     * 内存复用
     */
    private lateinit var mOptions: BitmapFactory.Options

    /**
     * 手势
     */
    private lateinit var mGestureDetector: GestureDetector

    /**
     * 滑动
     */
    private lateinit var mScroller: Scroller

    /**
     * 图片区域解码器
     */
    private var mRegionDecoder: BitmapRegionDecoder? = null

    /**
     * 图片原始宽
     */
    private var imageWidth: Int = 0

    /**
     * 图片原始高
     */
    private var imageHeight: Int = 0

    /**
     * View的宽
     */
    private var viewWidth: Int = 0

    /**
     * View的高
     */
    private var viewHeight: Int = 0

    /**
     * 图片缩放比例
     */
    private var scale: Float = 0f

    /**
     * 绘制区域
     */
    private var mMatrix: Matrix = Matrix()

    private var mutableBitmap : Bitmap? = null


    constructor(context: Context) : super(context) {
        initBigView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initBigView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initBigView(context)
    }


    private fun initBigView(context: Context) {
        mRect = Rect()
        mOptions = BitmapFactory.Options()
        mGestureDetector = GestureDetector(context, this)
        mScroller = Scroller(context)
        setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        // 将屏幕点击事件委托给手机检测处理
        return mGestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        // 当用户点击时，如果图片在滑动时，强制停止滑动
        if (!mScroller.isFinished) {
            mScroller.forceFinished(true)
        }
        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        // 滑动过程中设置分块加载的区域
        mRect.offset(0, distanceY.toInt())
        // 边界处理，如果底部超过图片高度，设置为图片高度
        if (mRect.bottom > imageHeight) {
            mRect.bottom = imageHeight
            // 获取应该显示的区域的Top值，因为图片会根据View的宽进行缩放
            mRect.top = imageHeight - (viewHeight / scale).toInt()
        }

        if (mRect.top < 0) {
            mRect.top = 0
            mRect.bottom = (viewHeight / scale).toInt()
        }
        // 触发重绘
        postInvalidate()
        return false
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        // 将惯性滑动委托给Scroller进行计算
        mScroller.fling(0, mRect.top, 0, -velocityY.toInt(), 0, 0, 0, imageHeight - viewHeight)
        return false
    }

    fun setImageUrl(inputStream: InputStream) {
        // 设置获取图片宽高
        mOptions.inJustDecodeBounds = true
        // 获取图片宽高
        BitmapFactory.decodeStream(inputStream, null, mOptions)
        imageWidth = mOptions.outWidth
        imageHeight = mOptions.outHeight
        // 恢复只获取宽高以便复用
        mOptions.inJustDecodeBounds = false
        // 设置Options中的inBitmap缓存是否可变，即inBitmap可以复用
        mOptions.inMutable = true

        // 创建区域解码器
        // 如果是true，那么区域解码类可以保持对输入的浅引用。
        // 如果是false，区域解码类将显式地复制输入数据，并保留它。即使允许分享，仍可能去制作输入数据的深拷贝。如果图像是逐步编码的，允许共享可能会降低解码速度。
        mRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false)
        // 触发重新布局，以获得宽高
        requestLayout()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = measuredWidth
        viewHeight = measuredHeight
        // 计算View 和 图片的 比例
        scale = viewWidth / imageWidth.toFloat()
        // 初始化分块加载的位置
        mRect.left = 0
        mRect.top = 0
        mRect.right = imageWidth
        mRect.bottom = (viewHeight / scale).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mRegionDecoder?.let {
            // 获取区域解码的图像
            mOptions.inBitmap = mutableBitmap
            mutableBitmap = mRegionDecoder?.decodeRegion(mRect, mOptions)
            // 绘制图片
            mMatrix.setScale(scale, scale)
            mutableBitmap?.let {
                canvas?.drawBitmap(it, mMatrix, null)
            }
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        // 如果滑动已经停止，则直接返回
        if (mScroller.isFinished) {
            return
        }
        // 返回true 则表示滑动还未停止，依旧在计算滑动位置
        if (mScroller.computeScrollOffset()) {
            mRect.top = mScroller.currY
            mRect.bottom = mScroller.currY + (viewHeight / scale).toInt()
            // 滑动过程中如果超出边界，则进行边界处理
            if (mRect.bottom > imageHeight) {
                mRect.bottom = imageHeight
                // 获取应该显示的区域的Top值，因为图片会根据View的宽进行缩放
                mRect.top = imageHeight - (viewHeight / scale).toInt()
                mScroller.forceFinished(true)
            }
            postInvalidate()
        }
    }
}