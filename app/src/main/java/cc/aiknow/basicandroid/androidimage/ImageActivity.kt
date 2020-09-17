package cc.aiknow.basicandroid.androidimage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val lp: ConstraintLayout.LayoutParams = testGradientDrawable.layoutParams as ConstraintLayout.LayoutParams
        lp.height = 400
        testGradientDrawable.layoutParams = lp

        // 将分享内容转换成包含水印背景的Bitmap
        val originWaterMarkingBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic_pb_post_share_watermarking);
        Log.e("chenhaoqiang", "0   " + originWaterMarkingBitmap.width + "  " + originWaterMarkingBitmap.height)
        val repeatBitmap = createRepeatBitmap(originWaterMarkingBitmap, originWaterMarkingBitmap.width, 400)
        testGradientDrawable.background = BitmapDrawable(repeatBitmap)

        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metric)
        val width = metric.widthPixels // 屏幕宽度（像素）

        val height = metric.heightPixels // 屏幕高度（像素）

        val density = metric.density // 屏幕密度（0.75 / 1.0 / 1.5）

        val densityDpi = metric.densityDpi

        Log.e("chenhaoqiang", "$width   $height    $density    $densityDpi")
    }

    private fun createRepeatBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap? {
        val repeatBitmap: Bitmap
        val canvas: Canvas
        Log.e("chenhaoqiang", "1   " + bitmap.width + "  " + height)
        repeatBitmap = Bitmap.createBitmap(bitmap.width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(repeatBitmap)
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        if (bitmap.height > height) {
            val srcRect = Rect(0, 0, bitmap.width, height)
            val dstRect = Rect(0, 0, bitmap.width, height)
            canvas.drawBitmap(bitmap, srcRect, dstRect, null)
            Log.e("chenhaoqiang", "1   " + bitmap.width + "  " + bitmap.height)
        } else {
            val repeatCount = height / bitmap.height
            val restHeight = height - repeatCount * bitmap.height
            for (i in 0 until repeatCount) {
                canvas.drawBitmap(bitmap, 0f, i * bitmap.height.toFloat(), null)
            }
            if (restHeight > 0) {
                canvas.drawBitmap(bitmap, 0f, repeatCount * bitmap.height.toFloat(), null)
            }
        }
        Log.e("chenhaoqiang", "2   " + repeatBitmap.width + "  " + repeatBitmap.height)
        return repeatBitmap
    }
}