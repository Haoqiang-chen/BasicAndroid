package cc.aiknow.basicandroid.androidlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cc.aiknow.basicandroid.R;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdatpter listViewAdatpter;
    private List<Person> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initDataSet();
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.listView);
        listViewAdatpter = new ListViewAdatpter(dataSet, this);
        listView.setAdapter(listViewAdatpter);
    }
    private void initDataSet() {
        dataSet = new ArrayList<>();
        dataSet.add(new Person("1", "11"));
        dataSet.add(new Person("2", "22"));
        dataSet.add(new Person("3", "33"));
        dataSet.add(new Person("4", "44"));
        dataSet.add(new Person("5", "55"));
        dataSet.add(new Person("6", "66"));
        dataSet.add(new Person("7", "77"));
        dataSet.add(new Person("8", "88"));
        dataSet.add(new Person("9", "99"));
        dataSet.add(new Person("10", "1010"));
        dataSet.add(new Person("11", "1111"));
        dataSet.add(new Person("12", "1212"));
    }
}
