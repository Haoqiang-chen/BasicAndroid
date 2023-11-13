package cc.aiknow.basicandroid.textview
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
/**
 * @Description: TODO
 * @Author chenhaoqiang
 * @Since 2023/10/19
 * @Version 1.0
 */

class CircleProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var progress = 0
    private var maxProgress = 100
    private var circleBackgroundColor = 0xFFC0C0C0.toInt() // 默认圆形底部颜色
    private var progressBarColor = 0xFF00FF00.toInt() // 默认进度条颜色

    private val paint = Paint()

    init {
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
    }

    fun setProgress(progress: Int) {
        this.progress = when {
            progress < 0 -> 0
            progress > maxProgress -> maxProgress
            else -> progress
        }
        invalidate()
    }

    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
    }

    fun setCircleBackgroundColor(color: Int) {
        circleBackgroundColor = color
        invalidate()
    }

    fun setProgressBarColor(color: Int) {
        progressBarColor = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val strokeWidth = 15f
        val centerX = width / 2
        val centerY = height / 2
        val radius = minOf(centerX, centerY) - strokeWidth / 2

        // 绘制圆形底部
        paint.color = circleBackgroundColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
//        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)

        canvas.drawArc(
            0f + strokeWidth / 2, 0f + strokeWidth / 2, width.toFloat() - strokeWidth / 2, height.toFloat() - strokeWidth / 2,
            -90f, 360f, false, paint
        )

        // 绘制进度条
        paint.color = progressBarColor
        paint.strokeWidth = strokeWidth
        val sweepAngle = (360f * (progress / maxProgress.toFloat())).toInt()
        canvas.drawArc(
            0f + strokeWidth / 2, 0f + strokeWidth / 2, width.toFloat() - strokeWidth / 2, height.toFloat() - strokeWidth / 2,
            -90f, sweepAngle.toFloat(), false, paint
        )
    }
}
