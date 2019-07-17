package cc.aiknow.basicandroid.androidproceethread;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import cc.aiknow.basicandroid.R;
/**
 * @Description: 用于学习进程和线程的相关知识
 * @Author: chen
 * @Date: 2019/6/16
 */
public class ProcessAndThreadActivity extends AppCompatActivity {

    private ImageView showImageByInternet, showImageByInternet2;
    private Context context = this;
    private Button buttonProggressBar;
    private ProgressBar progressBar;
    private TextView progressBarDes;
    private MyAsyncTask myAsyncTask;
    // 采用Handler进行线程之间的消息传递
    private Handler myHandler;
    private ImageView imageJump;
    private Map<Integer, Integer> jumpDrawableMap = new HashMap<Integer, Integer>() {
        {
            put(0, R.drawable.jump1);
            put(1, R.drawable.jump2);
            put(2, R.drawable.jump3);
            put(3, R.drawable.jump4);
            put(4, R.drawable.jump5);
            put(5, R.drawable.jump6);
            put(6, R.drawable.jump7);
            put(7, R.drawable.jump8);
            put(8, R.drawable.jump9);
        }
    };
    private int start = 0;
    private MyThreadToSendMessageToMainThread myThreadToSendMessageToMainThread;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_and_thread);
        initView();
        initListener();
        myHandler = new Handler(){
            // 从消息队列中取出该Handler在其它线程发送的消息
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 311) {
                    imageJump.setImageResource(jumpDrawableMap.get(start%9));
                    start++;

                }
            }
        };
        myThreadToSendMessageToMainThread = new MyThreadToSendMessageToMainThread(myHandler);
        myThreadToSendMessageToMainThread.start();

    }

    private void initView() {
        showImageByInternet = findViewById(R.id.showImageFromInternet);
        showImageByInternet2 = findViewById(R.id.showImageFromInternet2);
        buttonProggressBar = findViewById(R.id.progressBarButton);
        progressBar = findViewById(R.id.progressBar);
        progressBarDes = findViewById(R.id.progressBarDes);
        imageJump = findViewById(R.id.imageJump);
    }

    private void initListener() {
        showImageByInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // UI工具包是非线程安全的工具包，所以不要在UI线程之外访问UI工具包
                // 在工作线程中有以下三种方法可以从工作线程中访问UI线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 第一种方法：使用View.post或者postDelayed方法
                        showImageByInternet.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "在View.post()中更新UI", Toast.LENGTH_SHORT).show();
                                Glide.with(context)
                                       .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560698938601&di=3793e9f12b7082ddec9f7ad60f2822de&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F9678463fe652730cd52d1285d8874782f428b0ed1f9db-JevoUv_fw658")
                                       .into(showImageByInternet);
                            }
                        }, 100);
                    }
                }).start();
            }
        });

        showImageByInternet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 第二种方法 使用Activity.runOnUiThread方法
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "在Activity.runOnUIThread()中更新UI", Toast.LENGTH_SHORT).show();
                                Glide.with(context)
                                        .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560700187014&di=dafec76570525d0e79185df0119e4ae8&imgtype=0&src=http%3A%2F%2Fs8.sinaimg.cn%2Fmw690%2F006y3Cvyzy74iN1EzEr87%26690")
                                        .into(showImageByInternet2);
                            }
                        });
                    }
                }).start();
            }
        });

        // 开始执行异步任务
        buttonProggressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 异步任务的使用规则
                // 必须在UI线程中加载异步任务类、创建异步任务类的实例以及执行execute方法
                // 异步任务类只执行一次
                if (myAsyncTask == null) {
                    myAsyncTask = new MyAsyncTask(progressBarDes, progressBar);
                    myAsyncTask.execute(100);
                    buttonProggressBar.setText("停止后台操作");
                } else {
                    myAsyncTask.cancel(true);
                    myAsyncTask = null;
                    buttonProggressBar.setText("启动后台操作");
                }
            }
        });
    }

}
