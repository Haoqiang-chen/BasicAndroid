package cc.aiknow.basicandroid.androidviewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import cc.aiknow.basicandroid.R;
import cc.aiknow.basicandroid.androidviewpager.fragment.ViewPagerItemFragment;
import cc.aiknow.basicandroid.androidviewpager.transformer.DepthPageTransformer;
import cc.aiknow.basicandroid.androidviewpager.transformer.ZoomOutPageTransformer;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ViewPagerItemFragment.newInstance(String.valueOf(position));
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}