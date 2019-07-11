package cc.aiknow.basicandroid.androidretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API:http://fanyi.youdao.com/translate?doctype=json&type=AUTO&i=输入带翻译的文本
 * Retrofit将HTTP API在接口中声明
 * 然后使用动态代理进行解析为HTTP请求
 * @author chen
 * @version 1.0
 * @since 2019-07-11 16:44
 */
public interface TranslateService {

    @GET("/translate")
    Call<TranslateData> getTranslateResult(@Query("doctype") String doctype, @Query("type") String type, @Query("i") String sourceWord);
}
