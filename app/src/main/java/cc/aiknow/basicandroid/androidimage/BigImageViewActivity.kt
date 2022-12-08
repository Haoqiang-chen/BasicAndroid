package cc.aiknow.basicandroid.androidimage

import android.graphics.Matrix
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import cc.aiknow.basicandroid.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_big_image_view.*
import java.io.InputStream

class BigImageViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_image_view)
        val inputStream = resources.openRawResource(R.drawable.big_image)
        bigView.setImageUrl(inputStream)
        gif.scaleType = ImageView.ScaleType.MATRIX
        Glide.with(gif)
            .load(R.drawable.test_gif).listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    val imageView = (target as ImageViewTarget<*>).view
                    //这里先获取Drawable原始大小
                    //这里先获取Drawable原始大小
                    val dwidth = resource!!.intrinsicWidth
                    val dheight = resource.intrinsicHeight
                    val matrix = Matrix()
                    matrix.preTranslate(-dwidth / 2f, -dheight / 2f)
                    imageView.imageMatrix = matrix
                    return false
                }
            })
            .into(gif)

        Glide.with(gif_test).load(R.drawable.test_gif).into(gif_test)

        Glide.with(webp_test).load(R.drawable.pb_icon_tail).into(webp_test)
    }
}