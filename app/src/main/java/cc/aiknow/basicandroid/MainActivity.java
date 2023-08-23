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
import cc.aiknow.basicandroid.androidX2C.X2CActivity;
import cc.aiknow.basicandroid.androidactivity.AndroidActivity;
import cc.aiknow.basicandroid.androidapt.AptActivity;
import cc.aiknow.basicandroid.androidarch.ArchActivity;
import cc.aiknow.basicandroid.androidbroadcast.BroadcastActivity;
import cc.aiknow.basicandroid.androidcontentprovider.ContentProviderActivity;
import cc.aiknow.basicandroid.androidfragment.MyFragmentActivity;
import cc.aiknow.basicandroid.androidimage.BigImageViewActivity;
import cc.aiknow.basicandroid.androidlayout.LayoutActivity;
import cc.aiknow.basicandroid.androidlistview.ListViewActivity;
import cc.aiknow.basicandroid.androidnested.NestedActivity;
import cc.aiknow.basicandroid.androidnested.NestedScrollActivity;
import cc.aiknow.basicandroid.androidnested.ScrollerActivity;
import cc.aiknow.basicandroid.androidproceethread.ProcessAndThreadActivity;
import cc.aiknow.basicandroid.androidrecyclerview.MainRecyclerViewAdapter;
import cc.aiknow.basicandroid.androidrecyclerview.MainRecyclerViewItemTouchHelper;
import cc.aiknow.basicandroid.androidrecyclerview.RecyclerActivity;
import cc.aiknow.basicandroid.androidrecyclerview.RecyclerListAdapterActivity;
import cc.aiknow.basicandroid.androidrecyclerview.RecyclerViewItemClickListener;
import cc.aiknow.basicandroid.androidretrofit.RetrofitActivity;
import cc.aiknow.basicandroid.androidsaveprocess.JobSchedulerService;
import cc.aiknow.basicandroid.androidservice.ServiceActivity;
import cc.aiknow.basicandroid.androidso.NativeLog;
import cc.aiknow.basicandroid.androidstore.StoreActivity;
import cc.aiknow.basicandroid.androidview.LearnViewActivity;
import cc.aiknow.basicandroid.androidviewbinding.ViewBindingActivity;
import cc.aiknow.basicandroid.androidvieweventandanima.ViewEventActivity;
import cc.aiknow.basicandroid.androidviewpager.ViewPagerActivity;
import cc.aiknow.basicandroid.androidwindow.WindowActivity;
import cc.aiknow.basicandroid.androidbinder.BinderActivity;
import cc.aiknow.basicandroid.androidcustomview.CustomViewActivity;
import cc.aiknow.basicandroid.androidmultipleprocess.MultipleProcessActivity;
import cc.aiknow.basicandroid.textview.TextViewActivity;
import cc.aiknow.basicandroid.util.ScreenSizeAdapter;
import cc.aiknow.basicandroid.util.Utils;
import cc.aiknow.basicandroid.viewevent.ViewClickEventActivity;
import cc.aiknow.basicandroid.androidwebview.WebViewActivity;

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
        NativeLog.log("start order", "MainActivity onCreate start");
        super.onCreate(savedInstanceState);
        Log.e("chenhaoqiang", Utils.getProcessName(this, android.os.Process.myPid()));
        ScreenSizeAdapter.changeTargetDeviceDensity(this, getApplication(), 360);
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
        String str = "";
        Utils.isJSONObject(str);

        // File实现了序列化接口可被intent传递
//        File file = new File("");
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("s", file);

        initJobScheduler();
        // 调用变体中的代码
        new DemoTest();
        NativeLog.log("start order", "MainActivity onCreate end");
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
            add("ListView");
            add("Image");
            add("Layout");
            add("viewEvent");
            add("textView");
            add("multipleProcess");
            add("WebView");
            add("Apt");
            add("RecyclerListAdapterActivity");
            add("CustomViewActivity");
            add("ViewPagerActivity");
            add("ContentProviderActivity");
            add("X2CActivity");
            add("window");
            add("ViewBindingActivity");
            add("BinderActivity");
            add("ViewClickEventActivity");
            add("NestedScrollActivity");
            add("NestedActivity");
            add("ScrollerActivity");

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
            put("ListView", ListViewActivity.class);
            put("Image", BigImageViewActivity.class);
            put("Layout", LayoutActivity.class);
            put("viewEvent", ViewEventActivity.class);
            put("textView", TextViewActivity.class);
            put("multipleProcess", MultipleProcessActivity.class);
            put("WebView", WebViewActivity.class);
            put("Apt", AptActivity.class);
            put("RecyclerListAdapterActivity", RecyclerListAdapterActivity.class);
            put("CustomViewActivity", CustomViewActivity.class);
            put("ViewPagerActivity", ViewPagerActivity.class);
            put("ContentProviderActivity", ContentProviderActivity.class);
            put("X2CActivity", X2CActivity.class);
            put("window", WindowActivity.class);
            put("ViewBindingActivity", ViewBindingActivity.class);
            put("BinderActivity", BinderActivity.class);
            put("ViewClickEventActivity", ViewClickEventActivity.class);
            put("NestedScrollActivity", NestedScrollActivity.class);
            put("NestedActivity", NestedActivity.class);
            put("ScrollerActivity", ScrollerActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}
