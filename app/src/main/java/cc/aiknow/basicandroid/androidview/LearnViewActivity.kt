package cc.aiknow.basicandroid.androidview

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.databinding.ActivityLearnViewBinding

/**
 * @Description: 自定义view的学习（应该系统学习）
 * @Author: chen
 * @Date: 2019/6/28
 */
class LearnViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearnViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        my_draw_view_loop.setOnClickListener {
//            my_draw_view.loop()
//        }

//        btn.setOnLongClickListener {
////            ejectionEmojiAnimation.visibility = View.VISIBLE
////            ejectionEmojiAnimation.start()
//            val rect = Rect()
//            it.getGlobalVisibleRect(rect)
//            ejectionAnimationView.visibility = View.VISIBLE
////            ejectionAnimationView.setAnchorPosition((rect.right + rect.left) / 2, (rect.bottom + rect.top) / 2)
//            ejectionAnimationView.setAnchorPosition((it.right + it.left) / 2, (it.bottom + it.top) / 2)
//            ejectionAnimationView.start()
//            true
//        }
//
//        btn.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                Log.e("chenhaoqiang", "抬起")
//                ejectionAnimationView.stop()
//                true
//            }
//            false
//        }

        binding.btn.setOnClickListener {
            createDialog(this,
                dialogConfig = {
                    createAndConfig(
                        renderView = {

                        },
                        viewConfig = {
                            title {
                                text = "这个是标题"
                            }
                            message {
                                text = "这个是文本这个是文本这个是文本这个是文本这个是文本这个是文本"
                            }
                            oneBtn {
                                text = "取消"
                            }
                            twoBtn {
                                text = "确定"
                            }
                        },
                        listener = {
                            Toast.makeText(this@LearnViewActivity, "点击", Toast.LENGTH_SHORT).show()
                        })
                })
        }
    }
}
