package com.liangwei.kugouxia.frame;

import android.content.Context;
import android.util.Log;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;

/**
 * Created by weibao on 2018/4/7.
 */
public class AriaDownloadListenter extends Aria.DownloadSchedulerListener {
    private Context context;
    private boolean isAutoStart;
    private CallBack callBack;
    private String title;
    com.liangwei.kugouxia.frame.loading.ProgressDialog progressDialog;
    public interface CallBack{
        public void startDownload(DownloadTask task);
        public void fail(DownloadTask task);
        public void complete(DownloadTask task);
    }

    public AriaDownloadListenter(Context context,String title,CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.title = title;

    }

    public void onTaskStart(com.arialyy.aria.core.download.DownloadTask task) {
        Log.d("myapp", "fileSize" + task.getConvertFileSize());
        progressDialog = new com.liangwei.kugouxia.frame.loading.ProgressDialog(context);
        progressDialog.setTitle(title);
        callBack.startDownload(task);
    }

    public void onTaskStop(DownloadTask task) {

    }

    public void onTaskCancel(DownloadTask task) {

    }

    public void onTaskFail(com.arialyy.aria.core.download.DownloadTask task) {

        Aria.download(context).removeAllTask();
        callBack.fail(task);


    }

    public void onTaskComplete(com.arialyy.aria.core.download.DownloadTask task) {
        progressDialog.dismiss();
        callBack.complete(task);
    }

    public void onTaskRunning(final com.arialyy.aria.core.download.DownloadTask task) {
        //使用转换单位后的速度，需要在aria_config.xml配置文件中将单位转换开关打开
        //https://github.com/AriaLyy/Aria#配置文件设置参数
        // mSpeed.setText(task.getConvertSpeed());
        progressDialog.getTv_speed().setText(task.getConvertSpeed());
        progressDialog.getProgressBar().setProgress(task.getPercent());
        progressDialog.show();
    }
}