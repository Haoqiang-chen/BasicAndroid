package cc.aiknow.basicandroid.androidimage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.security.MessageDigest;

/**
 * @Description: TODO
 * @Author chenhaoqiang
 * @Since 2022/9/15
 * @Version 1.0
 */
public class GifTranslateTransform extends BitmapTransformation {
    private static final String ID = "cc.aiknow.basicandroid.androidimage";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Canvas canvas = new Canvas(toTransform);
        Paint paint = new Paint();
        canvas.drawRect(0,0 , toTransform.getWidth() / 2f, toTransform.getHeight() / 2f, paint);
        return toTransform;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

}
