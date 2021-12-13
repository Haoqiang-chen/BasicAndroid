package cc.aiknow.basicandroid.androidviewpager.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @Description: 缩小页面转换器 在相邻页面之间滚动时，该页面转换器会使页面收缩并淡出。随着页面越来越靠近中心，页面会恢复到正常大小并淡入
 * @Author chenhaoqiang
 * @Since 2021/12/3
 * @Version 1.0
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    /**
     * position: 当页面填满屏幕时为0 ，当页面刚刚离开屏幕右侧时为1， 当页面刚刚离开屏幕左侧时为-1
     * 示例： 如果用户在第一页和第二页之间滚动到一半，则第一页的位置为 -0.5，第二页的位置为 0.5
     * @param view
     * @param position
     */
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0f);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink（缩小） the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));  // [0, 2]
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0f);
        }
    }
}
