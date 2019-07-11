package cc.aiknow.basicandroid.androidJson;

/**
 * @author chen
 * @version 1.0
 * @since 2019-07-10 18:38
 */
public class GsonObject {
    private int value1 = 1;
    private String value2 = "str";
    private boolean value3 = true;
    private int[] array = new int[]{1, 2, 3};
    private InnerClass innerClass = new InnerClass();

    private class InnerClass {
        private int innerValue1 = 11;
        private String innerValue2 = "innerClass";
    }

    public String getValue2() {
        return value2;
    }
}
