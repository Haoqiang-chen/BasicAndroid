package cc.aiknow.basicandroid.androidJson;

import com.google.gson.Gson;

/**
 * 学习GSON（一种将Java对象进行JSON序列化与反序列化的Java库）的使用
 * @author chen
 * @version 1.0
 * @since 2019-07-10 17:58
 */
public class GsonTest {
    private static Gson gson = new Gson();
    private static String jsonStr;
    /**
     * 序列化
     */
    public static void serialization() {

        System.out.println("序列化结果：   " + gson.toJson("this is a String"));
        jsonStr = gson.toJson(new GsonObject());
        System.out.println("序列化对象：   " + jsonStr);
    }

    /**
     * 反序列化
     */
    public static void deserialization() {
        String str = gson.fromJson("\"this is a String\"", String.class);
        System.out.println("反序列化结果：   " + str);
        GsonObject gsonObject = gson.fromJson(jsonStr, GsonObject.class);
        System.out.println("反序列化对象：   " + gsonObject.getValue2());
    }
}
