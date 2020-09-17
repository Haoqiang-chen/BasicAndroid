package cc.aiknow.basicandroid.androidlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cc.aiknow.basicandroid.R;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BottomAdapter bottomAdapter;
    private List<String> dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        recyclerView = findViewById(R.id.bottom_recyclerview);
        initDatabse();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        bottomAdapter = new BottomAdapter(this, dataBase);
        recyclerView.setAdapter(bottomAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.main_recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void initDatabse() {
        dataBase = new ArrayList<String>(){{
            for (int i = 0; i < 100; i++) {
                add("" + i);
            }
        }};
    }
}