package cc.aiknow.basicandroid.androidsaveprocess;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * JobService:是JobScheduler调度的对象
 * 用于实现自己的Job业务逻辑，并且JobService运行于主线程中
 * 若想执行耗时操作应该新起线程执行
 * @author chen
 * @version 1.0
 * @since 2019-09-04 14:30
 */
public class JobSchedulerService extends JobService {

    /**
     *  用于实现自己的Job业务逻辑
     * @param params
     * @return 返回值为 true 或者 false
     * true表示正在执行的job还在运行(如在启动新的线程中执行某些操作)，此时需要手动调用jobFinished()以通知job调度程序该job已经完成
     * false表示job已经完成
     */
    @Override
    public boolean onStartJob(final JobParameters params) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.e("Job", "JonService onStartJob");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jobFinished(params, false);
            }
        }.start();
        return true;
    }

    /**
     * 当系统取消该Job时，并且onStartJon返回true时该方法会被触发
     * @param params
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("Job", "JonService onStopJob");
        return false;
    }
}
