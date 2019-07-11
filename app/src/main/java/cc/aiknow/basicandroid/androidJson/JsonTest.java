package cc.aiknow.basicandroid.androidJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

/**
 * 学习JSON的基本使用
 *
 * @author chen
 * @version 1.0
 * @since 2019-07-10 16:29
 * <p>
 * 输出如下：
 * {
 * "array": [1, "2", true, null, {
 * "itemObject": "this is a JSON object"
 * }],
 * "object": {
 * "object": "this is a another JSON object"
 * }
 * }
 */
public class JsonTest {
    private static String jsonStr;

    public static void creatJSON() {
        try {
            JSONObject root = new JSONObject();

            JSONArray array = new JSONArray();
            array.put(1);
            array.put("2");
            array.put(true);
            array.put(null);
            JSONObject item = new JSONObject();
            item.put("itemObject", "this is a JSON object");
            array.put(item);

            JSONObject object = new JSONObject();
            object.put("object", "this is a another JSON object");

            root.put("array", array);
            root.put("object", object);

            jsonStr = root.toString();
            System.out.println(root.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void jsonTokener() {
        try {
            JSONTokener jsonTokener = new JSONTokener(jsonStr);
            JSONObject root = (JSONObject) jsonTokener.nextValue();
            System.out.println(root.getJSONArray("array").get(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void jsonStringer() {
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object();

            jsonStringer.key("array");
            jsonStringer.array();
            jsonStringer.value(1);
            jsonStringer.value("2");
            jsonStringer.value(true);
            jsonStringer.value(null);
            jsonStringer.object();
            jsonStringer.key("itemObject");
            jsonStringer.value("this is a JSON object");
            jsonStringer.endObject();
            jsonStringer.endArray();

            jsonStringer.key("object");
            jsonStringer.object();
            jsonStringer.key("object");
            jsonStringer.value("this is a another JSON object");
            jsonStringer.endObject();

            jsonStringer.endObject();

            System.out.println(jsonStringer.toString());
            System.out.println(jsonStr.equals(jsonStringer.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
