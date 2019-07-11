package cc.aiknow.basicandroid.androidretrofit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 使用Retrofit进行网络请求
 *
 * @author chen
 * @version 1.0
 * @since 2019-07-11 17:07
 */
public class TranslateRequest {
    private TranslateData translateData = null;
    private Handler handler;

    public void requestTranslateResult(final String sourceWord) {
        // 第一步：首先创建Retorfit实例并设置数据解析器，不同的数据解析器需要添加不同的依赖项
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 第二步：使用retrofit实例创建网络请求接口实例
        final TranslateService translateService = retrofit.create(TranslateService.class);
        // 第三步应用网络请求接口实现异步或者同步请求
        translateService.getTranslateResult("json", "AUTO", sourceWord).enqueue(new Callback<TranslateData>() {
            @Override
            public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
                translateData = response.body();
                Message message = new Message();
                message.what = 311;
                Bundle bundle = new Bundle();
                bundle.putString("targetWord", translateData.getTranslateResult()[0][0].getTgt());
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<TranslateData> call, Throwable t) {
                translateData = null;
                Message message = new Message();
                message.what = 1995;
                Bundle bundle = new Bundle();
                bundle.putString("error", "error");
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

        // 同步请求，应放在子线程中，放在main线程中报错
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    translateData = translateService.getTranslateResult("json", "AUTO", sourceWord).execute().body();
//                    Message message = new Message();
//                    message.what = 311;
//                    Bundle bundle = new Bundle();
//                    bundle.putString("targetWord", translateData.getTranslateResult()[0][0].getTgt());
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Message message = new Message();
//                    message.what = 1995;
//                    Bundle bundle = new Bundle();
//                    bundle.putString("error", "error");
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                }
//            }
//        }.start();

    }

    public TranslateData getTranslateData() {
        return translateData;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
