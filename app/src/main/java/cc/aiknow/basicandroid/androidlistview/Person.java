package cc.aiknow.basicandroid.androidlistview;

/**
 * ListView的数据类
 * @author chen
 * @version 1.0
 * @since 2019-09-07 10:02
 */
public class Person {
    private String name;
    private String des;

    Person(String nane, String des) {
        this.name = nane;
        this.des =des;
    }

    public String getDes() {
        return des;
    }

    public String getName() {
        return name;
    }
}
