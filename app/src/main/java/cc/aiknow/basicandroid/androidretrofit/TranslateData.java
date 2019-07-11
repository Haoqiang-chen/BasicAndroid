package cc.aiknow.basicandroid.androidretrofit;

/**
 * 请求HTTP API后返回的数据格式
 * @author chen
 * @version 1.0
 * @since 2019-07-11 16:37
 */
public class TranslateData {
    private String type;
    private int errorCode;
    private int elapsedTime;
    private TranslateWord[][] translateResult;

    public int getElapsedTime() {
        return elapsedTime;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getType() {
        return type;
    }

    public TranslateWord[][] getTranslateResult() {
        return translateResult;
    }
}
