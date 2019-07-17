package cc.aiknow.basicandroid.androidfragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cc.aiknow.basicandroid.R;

/**
 * @Description: 用于学习Fragment的Activity
 * @Author: chen
 * @Date: 2019/6/29
 */
public class MyFragmentActivity extends AppCompatActivity implements MyFragment.OnFragmentInteractionListener {

    private Button buttonFragmentReplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, MyFragment.newInstance("1", "1"));
        fragmentTransaction.commit();
        Toast.makeText(this, "fragmentActivity create", Toast.LENGTH_SHORT).show();
        initView();
        initListener();
    }

    private void initView() {
        buttonFragmentReplace = findViewById(R.id.fragment_replace);
    }

    private void initListener() {
        buttonFragmentReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取片段管理器
                FragmentManager fragmentManager = getSupportFragmentManager();
                // 开始执行事务，该事务与片段管理器相关联
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, MySecondFragment.newInstance("2", "2"));
                // 将改组事务添加到Activity管理的返回栈中
                fragmentTransaction.addToBackStack(null);
                // 设置过渡动画
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "点击Fragment事件传递给Acticity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "fragmentActivity start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "fragmentActivity restart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "fragmentActivity resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "fragmentActivity pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "fragmentActivity stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "fragmentActivity destroy", Toast.LENGTH_SHORT).show();
    }
}
