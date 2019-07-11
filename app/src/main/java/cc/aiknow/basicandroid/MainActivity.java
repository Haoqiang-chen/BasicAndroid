package cc.aiknow.basicandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.aiknow.basicandroid.androidJson.GsonTest;
import cc.aiknow.basicandroid.androidJson.JsonTest;
import cc.aiknow.basicandroid.androidactivity.AndroidActivity;
import cc.aiknow.basicandroid.androidbroadcast.BroadcastActivity;
import cc.aiknow.basicandroid.androidfragment.MyFragmentActivity;
import cc.aiknow.basicandroid.androidproceethread.ProcessAndThreadActivity;
import cc.aiknow.basicandroid.androidrecyclerview.MainRecyclerViewAdapter;
import cc.aiknow.basicandroid.androidrecyclerview.MainRecyclerViewItemTouchHelper;
import cc.aiknow.basicandroid.androidrecyclerview.RecyclerActivity;
import cc.aiknow.basicandroid.androidrecyclerview.RecyclerViewItemClickListener;
import cc.aiknow.basicandroid.androidretrofit.RetrofitActivity;
import cc.aiknow.basicandroid.androidservice.ServiceActivity;
import cc.aiknow.basicandroid.androidview.LearnViewActivity;

/**
 * @Description: 主界面
 * @Author: chen
 * @Date: 2019/6/2
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    private Map<String, Class<?>> itemDataBase;
    private List<String> itemNames;
    private RecyclerView recyclerView;
    private MainRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDataBase();
        findView();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainRecyclerViewAdapter();
        adapter.setData(itemDataBase, itemNames, this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.main_recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MainRecyclerViewItemTouchHelper(adapter, itemNames));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        JsonTest.creatJSON();
        JsonTest.jsonTokener();
        JsonTest.jsonStringer();

        GsonTest.serialization();
        GsonTest.deserialization();
    }

    private void initDataBase() {
        itemNames = new ArrayList<String>(){{
            add("Activity");
            add("Service");
            add("ProcessAndThread");
            add("Broadcast");
            add("learnView");
            add("Fragment");
            add("RecyclerView");
            add("Retrofit");

        }};
        itemDataBase = new HashMap<String, Class<?>>(){{
            put("Activity", AndroidActivity.class);
            put("Service", ServiceActivity.class);
            put("ProcessAndThread", ProcessAndThreadActivity.class);
            put("Broadcast", BroadcastActivity.class);
            put("learnView", LearnViewActivity.class);
            put("Fragment", MyFragmentActivity.class);
            put("RecyclerView", RecyclerActivity.class);
            put("Retrofit", RetrofitActivity.class);
        }};
    }

    private void findView() {
        recyclerView = findViewById(R.id.mainRecyclerView);
    }

    @Override
    public void onItemClicked(int position) {
        Log.i("Mypostion", String.valueOf(position));
        Intent i = new Intent(this, itemDataBase.get(itemNames.get(position)));
        startActivity(i);
    }
}
