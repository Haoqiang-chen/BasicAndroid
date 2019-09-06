package cc.aiknow.basicandroid;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.aiknow.basicandroid.androidJson.GsonTest;
import cc.aiknow.basicandroid.androidJson.JsonTest;
import cc.aiknow.basicandroid.androidactivity.AndroidActivity;
import cc.aiknow.basicandroid.androidarch.ArchActivity;
import cc.aiknow.basicandroid.androidbroadcast.BroadcastActivity;
import cc.aiknow.basicandroid.androidfragment.MyFragmentActivity;
import cc.aiknow.basicandroid.androidproceethread.ProcessAndThreadActivity;
import cc.aiknow.basicandroid.androidrecyclerview.MainRecyclerViewAdapter;
import cc.aiknow.basicandroid.androidrecyclerview.MainRecyclerViewItemTouchHelper;
import cc.aiknow.basicandroid.androidrecyclerview.RecyclerActivity;
import cc.aiknow.basicandroid.androidrecyclerview.RecyclerViewItemClickListener;
import cc.aiknow.basicandroid.androidretrofit.RetrofitActivity;
import cc.aiknow.basicandroid.androidsaveprocess.JobSchedulerService;
import cc.aiknow.basicandroid.androidservice.ServiceActivity;
import cc.aiknow.basicandroid.androidstore.StoreActivity;
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

    private JobScheduler jobScheduler;

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

        // File实现了序列化接口可被intent传递
//        File file = new File("");
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("s", file);

        initJobScheduler();
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
            add("AndroidArch");
            add("AndroidStore");

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
            put("AndroidArch", ArchActivity.class);
            put("AndroidStore", StoreActivity.class);
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

    private void initJobScheduler() {
        // JobInfo用于设置任务调度的条件
        int jobId = 1;
        JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(getPackageName(), JobSchedulerService.class.getName()));
        builder.setPeriodic(1000);
        // job调度程序
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // 调度成功时返回被调度的job ID，失败则返回一个负数
        if (jobScheduler.schedule(builder.build()) >= 0) {
            Log.e("Job", "JonScheduler  success");
        } else {
            Log.e("Job", "JonScheduler  failure");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jobScheduler.cancel(1);
    }
}
