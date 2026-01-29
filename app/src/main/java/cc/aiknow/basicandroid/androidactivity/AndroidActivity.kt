package cc.aiknow.basicandroid.androidactivity

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.databinding.ActivityAndroidBinding

/**
 * @Description: 用于声明周期得验证、显式启动Activity
 * @Author: chen
 * @Date: 2019/6/2
 */
class AndroidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAndroidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAndroidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        Toast.makeText(this, "Father---create", Toast.LENGTH_SHORT).show()
        Log.i("Father---", "create")
    }

    private fun initListener() {
        binding.activityA.setOnClickListener {
            // 显式启动Activity A
            val i = Intent()
            // TODO 类引用的Kotlin学习
            // 用于测试当启动模式为栈顶单例(singleTop)或者栈内单列(singleTask)或CLEAR_TOP的情况下启动响应的Activity时，生命周期变为先pause然后调用onNewIntent然后调用resume
            i.putExtra("source", "Father Intent")
            i.setClass(this, AActivity::class.java)
            startActivity(i)
        }
        binding.activityB.setOnClickListener {
            // 隐式启动Activity B 注意隐式启动Activity时Intent中数据的匹配规则
            val i = Intent().apply {
                action = "activityB_action"
                addCategory("acticityB_categories")
            }
            // 使用resolveActivity来判断是否有Activity可以响应隐式启动
            i.resolveActivity(packageManager)?.let {
                startActivity(i)
                Toast.makeText(this, "隐式启动ActivityB", Toast.LENGTH_SHORT).show()
            }?:let {
                Toast.makeText(this, "未找到匹配的Activity", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "Father---start", Toast.LENGTH_SHORT).show()
        Log.i("Father---", "start")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Father---resume", Toast.LENGTH_SHORT).show()
        Log.i("Father---", "resume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Father---pause", Toast.LENGTH_SHORT).show()
        Log.i("Father---", "pause")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "Father---stop", Toast.LENGTH_SHORT).show()
        Log.i("Father---", "stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Father---destroy", Toast.LENGTH_SHORT).show()
        Log.i("Father---", "destroy")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "Father---Restart", Toast.LENGTH_SHORT).show()
        Log.i("Father---", "Restart")
    }
}
