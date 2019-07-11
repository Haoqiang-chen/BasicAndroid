package cc.aiknow.basicandroid.androidproceethread;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 异步任务类：
 * 主要用于在后台执行操作但是将结果显示在UI线程中
 * @author chen
 * @version 1.0
 * @since 2019-06-16 21:47
 */
public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

    private TextView textViewDes;
    private ProgressBar progressBar;

    public MyAsyncTask(TextView textView, ProgressBar progressBar) {
        this.textViewDes = textView;
        this.progressBar = progressBar;
    }
    // 在异步任务开始之前在UI线程中被调用
    // 主要用于初始化异步任务中的相关操作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textViewDes.setText("异步任务开始执行啦！！！");
    }

    // 用于在后台线程中执行操作，参数为使用异步任务时传入的参数
    // 在onPreExecute执行完成后立即在后台线程中执行
    @Override
    protected String doInBackground(Integer... integers) {

        Integer sum = integers[0];
        // 模拟耗时操作
        for (int i = 0; i < sum; i++) {
            // 为了保证后台任务及时被取消
            // 应在后台操作中不断检测是否被取消
            if (isCancelled()) {
                return null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 在doInBackground方法中调用用于通知onProgressUpdate对相关UI进行更新
            publishProgress(i);
        }
        return "后台操作完成！";
    }

    // 当publishProgress被调用时onProgressUpdate在UI线程中被调用
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    // 在后台操作完成后在UI线程中被调用
    // doInBackground方法返回的结果将传递给该方法
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textViewDes.setText(s);
    }

    // 当AsyncTask对象调用cancel方法取消任务后
    // 会使isCanceled()方法返回true，然后执行完doInBackground方法后
    // 调用onCancelled方法而不是onPostExecute方法
    @Override
    protected void onCancelled() {
        super.onCancelled();
        textViewDes.setText("后台操作被取消！！！");
    }
}
