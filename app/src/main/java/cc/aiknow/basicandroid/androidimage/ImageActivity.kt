package cc.aiknow.basicandroid.androidimage

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import cc.aiknow.basicandroid.R
import com.bumptech.glide.Glide
import cc.aiknow.basicandroid.databinding.ActivityImageBinding
import java.util.*


class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lp: ConstraintLayout.LayoutParams = binding.testGradientDrawable.layoutParams as ConstraintLayout.LayoutParams
        lp.height = 400
        binding.testGradientDrawable.layoutParams = lp

        // 将分享内容转换成包含水印背景的Bitmap
        val originWaterMarkingBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic_pb_post_share_watermarking);
        Log.e("chenhaoqiang", "0   " + originWaterMarkingBitmap.width + "  " + originWaterMarkingBitmap.height)
        val repeatBitmap = createRepeatBitmap(originWaterMarkingBitmap, originWaterMarkingBitmap.width, 400)
        binding.testGradientDrawable.background = BitmapDrawable(repeatBitmap)

        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metric)
        val width = metric.widthPixels // 屏幕宽度（像素）

        val height = metric.heightPixels // 屏幕高度（像素）

        val density = metric.density // 屏幕密度（0.75 / 1.0 / 1.5）

        val densityDpi = metric.densityDpi

        Log.e("chenhaoqiang", "$width   $height    $density    $densityDpi")

        binding.originImage.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.stamp))

        setContrastBitmap(0f)

        // 亮度
        var brightness = 0
        // 对比度
        var contrast = 0
        // 饱和度
        var saturation = 0;
        // 色调
        var hue = 0



        binding.contrastText.text = "对比度: 0"
        binding.seekbar.max = 100
        binding.seekbar.min = -100
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               contrast = progress
                binding.contrastText.text = "对比度: " + contrast
                setContrastBitmap(brightness, contrast, saturation, hue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.e("chenhaoqiang", "开始")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.e("chenhaoqiang", "停止")
            }
        })

        binding.lightText.text = "亮度：0"
        binding.seekbarLight.max = 100
        binding.seekbarLight.min = -100
        binding.seekbarLight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                brightness = progress
                binding.lightText.text = "亮度：" + brightness
                setContrastBitmap(brightness, contrast, saturation, hue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


        binding.saturationText.text = "饱和度：0"
        binding.seekbarSaturation.max = 100
        binding.seekbarSaturation.min = -100
        binding.seekbarSaturation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                saturation = progress
                binding.saturationText.text = "饱和度：" + saturation
                setContrastBitmap(brightness, contrast, saturation, hue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.hueText.text = "色调：0"
        binding.seekbarHue.max = 100
        binding.seekbarHue.min = -100
        binding.seekbarHue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                hue = progress
                binding.hueText.text = "色调：" + hue
                setContrastBitmap(brightness, contrast, saturation, hue)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


    }

    private fun setContrastBitmap(value: Float) {
        // 对比度
        val stampBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.stamp)
        val faceIconGreyBitmap = Bitmap
            .createBitmap(stampBitmap.width, stampBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(faceIconGreyBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val colorMatrixColorFilter = setContrast(value)
        paint.setColorFilter(colorMatrixColorFilter)
        canvas.drawBitmap(stampBitmap,0.0f, 0.0f, paint)
        binding.testContrast.setImageBitmap(faceIconGreyBitmap)
    }

    private fun setContrastBitmap(brightness: Int, contrast: Int, saturation: Int, hue: Int) {
        // 对比度
        val stampBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.stamp)
        val faceIconGreyBitmap = Bitmap
            .createBitmap(stampBitmap.width, stampBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(faceIconGreyBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val colorMatrixColorFilter = ColorFilterGenerator.adjustColor(brightness, contrast, saturation, hue)
        paint.setColorFilter(colorMatrixColorFilter)
        canvas.drawBitmap(stampBitmap,0.0f, 0.0f, paint)
        binding.testContrast.setImageBitmap(faceIconGreyBitmap)
    }

    fun getConvertedValue(intVal: Int): Float {
        var floatVal = 0.0f
        floatVal = .1f * intVal
        return floatVal
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


    fun setContrast(contrast: Float): ColorMatrixColorFilter {
        val scale: Float = contrast + 1f
        val translate: Float = (-.5f * scale + .5f) * 255f
        val array = floatArrayOf(
            scale, 0f, 0f, 0f, translate, 0f, scale, 0f, 0f, translate, 0f, 0f, scale, 0f, translate, 0f, 0f, 0f, 1f, 0f
        )
        val matrix = ColorMatrix()
        matrix.setSaturation(0.0f)
        matrix.postConcat(ColorMatrix(array))
        return ColorMatrixColorFilter(matrix)
    }
}