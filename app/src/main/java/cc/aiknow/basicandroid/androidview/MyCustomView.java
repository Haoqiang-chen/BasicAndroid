package cc.aiknow.basicandroid.androidview;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

import cc.aiknow.basicandroid.R;

/**
 * 自定义View：用于学习View的相关知识
 * @author chen
 * @version 1.0
 * @since 2019-06-28 19:48
 */
public class MyCustomView extends ConstraintLayout {

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * inflate分为：LayoutInflater.inflate和View.inflate
         * LayoutInflater(布局填充器).inflate:
         * 该方法适用于所有布局填充的的场景，但使用前需要先实例化LayoutInflater对象
         * 首先要获取布局填充器实例，然后使用inflate方法根据布局文件进行填充布局。
         * 第一个参数为要被填充的资源文件
         * 第二个参数为指定填充的父容器
         * 第三个参数表示是否将资源文件布局填充到指定的父容器中，如果是false则只是表明添加了父容器约束，并不填充
         * 返回值为：若指定了父容器且表示填充到父容器则返回父容器,否则返回由资源文件所创建的view
         *
         * View.inflate：内部调用了LayoutInflater
         *
         * 以下两种方式实现的功能相同
         */
        inflate(context, R.layout.layout_my_custom_view, this);
//        LayoutInflater.from(context).inflate(R.layout.layout_my_custom_view, this, true);
    }
}
