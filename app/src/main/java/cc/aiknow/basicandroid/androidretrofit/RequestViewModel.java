package cc.aiknow.basicandroid.androidretrofit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 采用LiveData以及ViewModel的方式请求网络数据并显式在UI
 * 学习资料 https://www.jianshu.com/p/661c0459b375
 * @author chen
 * @version 1.0
 * @since 2019-07-18 20:44
 */
public class RequestViewModel extends ViewModel {

    private MutableLiveData<String> result = new MutableLiveData<>();

    public void requestTranslateRequest(String sourceWord) {
        // 第一步创建Retrofit实例并设置数据解析器
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 第二步使用retrofit实例创建网络请求接口实例
        TranslateService translateService = retrofit.create(TranslateService.class);
        // 第三步采用异步或者同步请求数据
        translateService.getTranslateResult("json", "AUTO", sourceWord).enqueue(new Callback<TranslateData>() {
            @Override
            public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
                result.setValue(response.body().getTranslateResult()[0][0].getTgt());
            }

            @Override
            public void onFailure(Call<TranslateData> call, Throwable t) {
                result.setValue("request error");
            }
        });
    }

    public MutableLiveData<String> getResult() {
        return result;
    }
}
