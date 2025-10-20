package cc.aiknow.basicandroid.androidrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.aiknow.basicandroid.R;
import cc.aiknow.basicandroid.androidview.RecyclerViewEmptyView;
import cc.aiknow.basicandroid.androidview.RecyclerViewFooterView;

/**
 * 更新：添加头部选择菜单
 *
 * @Description: 用于学习RecyclerView的相关知识
 * @Author: chen
 * @Date: 2019/7/2
 */
public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initDataBase();
        initView();
    }

    private void initDataBase() {
        dataBase = new ArrayList<String>(){{
            add("第一种类型--1");
            add(null);
            add("第一种类型--2");
            add(null);
            add("第一种类型--3");
            add(null);
            add("第一种类型--4");
            add(null);
            add("第一种类型--5");
            add(null);
            add("第一种类型--6");
            add(null);
            add("第一种类型--7");
            add(null);
            add("第一种类型--8");
            add(null);
            add("第一种类型--9");
            add(null);
            add("第一种类型--10");
            add(null);
            add("第一种类型--11");
            add(null);
            add("第一种类型--12");
        }};
    }
    private void initView() {
        recyclerView = findViewById(R.id.recyclerActivityRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.main_recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        MultipleRecyclerViewAdapter multipleRecyclerViewAdapter = new MultipleRecyclerViewAdapter(this, dataBase);
        View header = LayoutInflater.from(this).inflate(R.layout.recycler_view_header, recyclerView, false);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecyclerActivity.this, RecyclerAnimationActivity.class));
            }
        });
        multipleRecyclerViewAdapter.setHeaderView(header);
        multipleRecyclerViewAdapter.setFooterView(new RecyclerViewFooterView(recyclerView.getContext()));
        multipleRecyclerViewAdapter.setEmptyView(new RecyclerViewEmptyView(recyclerView.getContext()));
        recyclerView.setAdapter(multipleRecyclerViewAdapter);
    }

    // 添加选择菜单


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
