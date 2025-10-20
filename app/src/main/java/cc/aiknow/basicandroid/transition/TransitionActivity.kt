package cc.aiknow.basicandroid.transition

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cc.aiknow.basicandroid.R
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

/**
 * @Description: 页面过度动画
 * @Author: chenhaoqiang
 * @Since: 2024/12/10
 * @Version: 1.0
 */
class TransitionActivity : AppCompatActivity() {
    var view1 : TextView? = null
    var view2 : TextView? = null
    var view3 : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        // Set up shared element transition
        setExitSharedElementCallback(object : MaterialContainerTransformSharedElementCallback() {
            override fun onCaptureSharedElementSnapshot(
                sharedElement: View,
                viewToGlobalMatrix: Matrix,
                screenBounds: RectF
            ): Parcelable? {
                return super.onCaptureSharedElementSnapshot(
                    sharedElement,
                    viewToGlobalMatrix,
                    screenBounds
                )
            }
        })

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_transition)
        /**
         * 使用共享元素实现页面的过度动画
         * 1. 源页面和目标页面需要对使用过度动画的控件设置android:transitionName属性
         */
        view1 = findViewById(R.id.transitionView1)
        view1?.setOnClickListener {
            val intent = Intent(this, OneTransitionActivity::class.java)
            val sharedViews = Pair<View, String>(view1!!, "sharedElement")
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, sharedViews).toBundle())
        }

        view2 = findViewById(R.id.transitionView2)
        view2?.setOnClickListener {
            val intent = Intent(this, OneTransitionActivity::class.java)
            val sharedViews = Pair<View, String>(view2!!, "sharedElement")
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, sharedViews).toBundle())
        }

        view3 = findViewById(R.id.transitionView3)
        view3?.setOnClickListener {
            val intent = Intent(this, OneTransitionActivity::class.java)
            val sharedViews = Pair<View, String>(view3!!, "sharedElement")
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, sharedViews).toBundle())
        }
    }
}