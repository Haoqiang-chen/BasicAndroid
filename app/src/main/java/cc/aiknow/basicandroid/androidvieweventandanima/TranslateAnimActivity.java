package cc.aiknow.basicandroid.androidvieweventandanima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import cc.aiknow.basicandroid.R;

/**
 * 学习补间动画（只需要置顶动画开始帧、结束帧，中间部分自动补齐的动画）：平移动画
 * 注意：补间动画不会影响view的属性值
 */
public class TranslateAnimActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btn1, btn2, btn3;
    // 平移动画类
    private TranslateAnimation translateAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_anim);
        initView();
        initListener();
    }
    private void initView() {
        imageView = findViewById(R.id.translate_image);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
    }

    private void initListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 普通的平移动画构造方法：
                 * 四个参数分别为：平移起始的x坐标，平移后的x坐标，y相同的设置
                 */
                translateAnimation = new TranslateAnimation(0, 200, 0, 0);
                translateAnimation.setDuration(3000);
                translateAnimation.setFillAfter(true);
                imageView.startAnimation(translateAnimation);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 第二种构造方法：fromXType表示相对自己或者父布局计算平移后的位置
                 * 例如：TranslateAnimation.RELATIVE_TO_SELF, 0.5f  表示x轴正向移动自身宽度50%的距离
                 */
                translateAnimation = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_SELF, 0,
                        TranslateAnimation.RELATIVE_TO_SELF, 0.5f,
                        TranslateAnimation.RELATIVE_TO_SELF, 0,
                        TranslateAnimation.RELATIVE_TO_SELF, 0);
                translateAnimation.setDuration(3000);
                translateAnimation.setFillAfter(true);
                imageView.startAnimation(translateAnimation);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 第二种构造方法：fromXType表示相对自己或者父布局计算平移后的位置
                 * 例如：TranslateAnimation.RELATIVE_TO_PARENT, 0.5f  表示x轴正向移动父布局宽度50%的距离
                 */
                translateAnimation = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_PARENT, 0,
                        TranslateAnimation.RELATIVE_TO_PARENT, 0.5f,
                        TranslateAnimation.RELATIVE_TO_PARENT, 0,
                        TranslateAnimation.RELATIVE_TO_PARENT, 0);
                translateAnimation.setDuration(3000);
                translateAnimation.setFillAfter(true);
                imageView.startAnimation(translateAnimation);
            }
        });
    }
}