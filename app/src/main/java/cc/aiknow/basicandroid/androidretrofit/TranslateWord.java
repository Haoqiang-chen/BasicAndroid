package cc.aiknow.basicandroid.androidretrofit;

/**
 * 请求HTTP API后返回的翻译词的数据格式
 * @author chen
 * @version 1.0
 * @since 2019-07-11 16:35
 */
public class TranslateWord {
    private String src;
    private String tgt;

    public String getSrc() {
        return src;
    }

    public String getTgt() {
        return tgt;
    }
}
