package cc.aiknow.basicandroid.androidview

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import cc.aiknow.basicandroid.R


/**
 * @Description: 采用DSL方式设计一个通用弹窗实现
 * 主要思想就是通过带接受者的函数，提供相应类型的作用域，使得该类型在盖函数中可以直接调用函数
 * @Author chenhaoqiang
 * @Since 2023/3/1
 * @Version 1.0
 */

fun createDialog(ctx: Context, dialogConfig: AlertDialog.Builder.() -> Unit) {
    val dialogBuilder = AlertDialog.Builder(ctx)
    dialogBuilder.dialogConfig()
}

/**
 * 定义AlertDialog中扩展函数，提供创建默认布局的方式
 * @param renderView 自定义渲染弹窗最外层根布局
 * @param viewConfig 自定义弹窗各个控件的设置
 * @param listener 自定义渲染弹窗最外层根布局
 */
fun AlertDialog.Builder.createAndConfig(
    renderView: View.() -> Unit,
    viewConfig: View.() -> Unit,
    listener: View.() -> Unit
) {
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dsl_dialog_layout, null)
    with(GradientDrawable()) {
        // 设置默认渐变背景色
        colors = intArrayOf(
            context.resources.getColor(R.color.colorPrimary),
            context.resources.getColor(R.color.colorPrimary)
        )
        orientation = GradientDrawable.Orientation.TOP_BOTTOM
        // 设置默认形状
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 50f
        // 设置自定义的整体View的自定义渲染
        dialogView.renderView()
        dialogView.background = this
    }
    // 1.设置Dialog中各个控件的自定义设置, 我想对弹窗控件进行设置，那么我就需要提供一个作用域是弹窗控件的函数，所以增加了viewConfig: View.() -> Unit 函数，向外提供View作用域
    dialogView.viewConfig()
    // 填充到Dialog, 创建Dialog并展示
    val dialog = setView(dialogView).create()
    dialog.showDialog()
    dialog.click(listener)
}

/**
 * 扩展AlertDialog 添加showDialog函数，封装展示以及如何设置window
 */
fun AlertDialog.showDialog() {
    // 设置window的基础设置
    window?.setBackgroundDrawableResource(R.color.transparent)
    show()
}

/**
 * 2. 因为有了View的作用域，所以扩展View的函数来设置标题等控件
 * 3. 标题等控件类型也需要自定义，那么就可以在该扩展函数中提供一个与控件类型相同的带接受者类型的函数，向外暴露设置
 */
fun View.title(titleConfig: TextView.() -> Unit) {
    val textView = findViewById<TextView>(R.id.dialog_title)
    textView.titleConfig()
}

fun View.message(messageBtnConfig: TextView.() -> Unit) {
    val oneBtn = findViewById<TextView>(R.id.dialog_message)
    oneBtn.messageBtnConfig()
}


fun View.oneBtn(oneBtnConfig: TextView.() -> Unit) {
    val oneBtn = findViewById<TextView>(R.id.btn_one)
    oneBtn.oneBtnConfig()
}

fun View.twoBtn(twoBtnConfig: TextView.() -> Unit) {
    val twoBtn = findViewById<TextView>(R.id.btn_two)
    twoBtn.twoBtnConfig()
}

fun AlertDialog.click(listener: View.() -> Unit) {
    val twoBtn = findViewById<TextView>(R.id.btn_two)
    twoBtn?.setOnClickListener {
        dismiss()
        twoBtn.listener()
    }
}