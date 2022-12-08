package cc.aiknow.basicandroid.androidX2C;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cc.aiknow.basicandroid.R;

/**
 * @Description: 学习如何进行布局加载优化
 * @Author: chenhaoqiang
 * @Since: 2022/9/6
 * @Version: 1.0
 */
public class X2CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * LayoutInflater.from(this).setFactory
         * 提供一个Hook功能，用于在布局填充时对即将填充的View进行特殊处理
         * 注意LayoutInflater.from(this)获取的LayoutInflater内部mPrivateFactory 就是Activity本身， 在View创建时会调用四个参数的onCreateView
         */
        LayoutInflater.from(this).setFactory(new LayoutInflater.Factory() {
            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                Log.e("setFactory", "name:"  + name + "\n");
                for (int i = 0; i < attrs.getAttributeCount(); i++) {
                    Log.e("setFactory", "attrName: " + attrs.getAttributeName(i) + "  attrValue: " + attrs.getAttributeValue(i));
                }
                if ("Button".equals(name)) {
                    return new TextView(context, attrs);
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x2_cactivity);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);

    }
}