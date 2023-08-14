package cc.aiknow.basicandroid.androidnested

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.aiknow.basicandroid.R

class NestedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested)
    }
}