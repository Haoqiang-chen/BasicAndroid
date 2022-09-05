package cc.aiknow.basicandroid.androidimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_big_image_view.*
import java.io.InputStream

class BigImageViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_image_view)
        val inputStream = resources.openRawResource(R.drawable.big_image)
        bigView.setImageUrl(inputStream)
    }
}