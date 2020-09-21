package cc.aiknow.basicandroid.androidvieweventandanima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cc.aiknow.basicandroid.R;

/**
 * 学习view的滑动
 */
public class ScrollActivity extends AppCompatActivity {

    private EventViewOne eventViewOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        intView();
        learnScrollToAndBy();
    }

    private void intView() {
        eventViewOne = findViewById(R.id.event_view_one);
    }

    private void learnScrollToAndBy() {
        eventViewOne.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * scrollTo内部中会有一个mScrollX 和mScrollY 表示当前view中内容的偏移量
                 * mScrollX：为view左边缘距离内部内容左边缘的距离及：mScrollX = view.left - content.left  因此 mScrollX > 0时view内部内容向左移动，反之
                 * mScrollY：为view上边缘距离内部内容上边缘的距离及：mScrollY = view.top - content.top  因此 mScrollY > 0时view内部内容向上移动，反之
                 * scrollTo中参数x,y会复制给mScrollX， mScrollY，因此该方法的作用是将该view移动到（x, y）位置
                 * scrollTo/By方法：内容移动范围只会在view中，会移出view的显示范围，但是不会移到其他view的显示区域
                 */
                eventViewOne.scrollTo(0, -400);
            }
        }, 1000);

        eventViewOne.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * scrollBy内部会调用scrollTo: scrollTo(mScrollX + x, mScrollY + y)
                 * 是对mScrollX 和mScrollY进行修改，因此scrollBy的作用是将该view中的内容基于当前位置进行相对移动
                 * 由于mScrollX 和mScrollY的计算方式，x, y > 0 时坐标轴反向移动，反之
                 */
                eventViewOne.scrollBy(-100, 400);
            }
        }, 2000);
    }
}