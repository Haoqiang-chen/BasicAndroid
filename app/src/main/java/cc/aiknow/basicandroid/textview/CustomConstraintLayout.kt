package cc.aiknow.basicandroid.textview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import cc.aiknow.basicandroid.R
import com.airbnb.lottie.LottieAnimationView

/**
 * @Description: TODO
 * @Author chenhaoqiang
 * @Since 2023/9/26
 * @Version 1.0
 */
class CustomConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    init {
    }



    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        var leftView: View? = null
        var rightView: View? = null
        var width = 0
        var height = 0
        children.forEach {
            val view = findViewById<View>(R.id.test_view)
            if (it.id == R.id.test_anim) {
                leftView = it
                Log.e("chq", "左： ${it.left}  上: ${it.top}  右: ${it.right}  下：${it.bottom}")
                view.left = it.right
                view.top = it.bottom
            }
            if (it.id == R.id.test_lottie) {
                rightView = it
                Log.e("chq", "左： ${it.left}  上: ${it.top}  右: ${it.right}  下：${it.bottom}")
                view.right = it.left
                view.bottom = it.top
            }
        }
//        if (leftView != null && rightView != null) {
//            width = rightView!!.left - leftView!!.right
//            height = rightView!!.top - leftView!!.bottom
//            val view = findViewById<View>(R.id.test_view)
//            val lp = view.layoutParams
//            if (lp.width != width || lp.height != height) {
//                lp.width = width
//                lp.height = height
//                view.layoutParams = lp
//            }
//        }
    }
}