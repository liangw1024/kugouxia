package com.liangwei.kugouxia.frame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.liangwei.kugouxia.BuildConfig;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.tbs.X5Activity;
import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.tencent.qq.widget.QQDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * activity 基类
 * Created by weibao on 2018/3/3.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context context;
    public ScreenManager screenManager = ScreenManager.getInstance();
    int downX = 0;
    int downY = 0;
    int upX = 0;
    int upY = 0;

    public static final String TAG = "kugouxia";
    public boolean isRotateScreen =true; //是否屏幕旋转
    public abstract int getContentView();
    public abstract void mOncreate();
    public abstract void initToolbar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//解决软键盘顶view
        screenManager.setStatusBarTranslucmnt(this);

        /*如果是kugouMainactivity 就设置状态栏字体白色 if 黑色*/
        if(getClass().toString().contains("KuGouMainActivity")|getClass().toString().contains("X5Activity")){
            /*状态栏黑底文字\full screen not hide status*/
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
        }else{
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
        }
        setContentView(getContentView());
        Log.i(TAG,"current act:"+getClass().toString());
        context = this;

        ButterKnife.bind(this);//this指代子activity
        mOncreate();
        initToolbar();
        screenManager.setRotateScreen(isRotateScreen, this);


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        screenManager = ScreenManager.getInstance();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    public void setIsRotateScreen(boolean b){
        this.isRotateScreen = b;
    }


    public void startAct(Class<?> act,Bundle bundle){
        Intent intent = new Intent(this,act);
        if(bundle!=null){
            intent.putExtras(bundle);
        }

        startActivity(intent);
    }

    /**
     * 启动activity
     * @param activity 跳转目标
     * @param bundle 传递参数
     * @param time 跳转间隔时间 毫秒为单位
     */
    public void startActivity(final BaseActivity currentActivity, final Class<?> activity, Bundle bundle, int time){
        final Intent intent =new Intent();
        intent.setClass(this,activity);
        if(bundle!=null) {
            intent.putExtras(bundle);
        }
        if(time==0){
            startActivity(intent);
        }else{
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                    currentActivity.finish();
                }
            };
            new Timer().schedule(timerTask,time);
        }
    }




    /**
     *
     * @param context
     * @param text
     * @param time 可以long short
     */
    public static void showToast(Context context,String text,String time){
        switch (time){
            case "long":
                Toast.makeText(context,text,Toast.LENGTH_LONG).show();
            case "short":
                Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
            default:
                Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
        }
    }
    public static void showLoading(){

    }

    /**
     * 检测更新
     */
    public void checkUpdate(){
            String currentVersionName = BuildConfig.VERSION_NAME;
            HttpRequestUtils.getNeedUi(this, AppConfig.check_update_url, new INetCallback() {
                @Override
                public void success(String content) {
                    try {
                        JSONObject json = new JSONObject(content);
                        String codeName = json.getString("versionName");
                        if (codeName.equals(currentVersionName)){
                            ToastUtils.ShowToast(getApplicationContext(),"已经是最新版本");
                        }else{
                            String url = json.getString("url");
                            openUrl(BaseActivity.this,url);
                            ToastUtils.ShowToast(getApplicationContext(),"检测到有新版本，请更新");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void fail(Exception e, String detail) {
                    showToast(getApplicationContext(),"检查更新失败:"+detail,"long");
                }
            });
        }





    /**
     * 显示dialog 带view
     * @param context
     * @param title
     * @param message
     * @param negativeText
     * @param neutralText
     * @param positiveText
     * @param view
     * @param iDialogClick 点击事件的回调
     */
    public static AlertDialog showDialog(Context context, String title, String message, String neutralText, String negativeText, final String positiveText, final View view, final IDialogClick iDialogClick){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        if(!(view==null)){
            alertDialogBuilder.setView(view);
        }
        if (title!=null) {
            alertDialogBuilder.setTitle(title);
        }
        if (message!=null) {
            alertDialogBuilder.setMessage(message);
        }
        if (negativeText!=null){
            alertDialogBuilder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(!(iDialogClick==null)){
                        iDialogClick.negativeClick(view);
                    }

                }
            });
        }
        if(neutralText!=null){
            alertDialogBuilder.setNeutralButton(neutralText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(!(iDialogClick==null)){
                        iDialogClick.neutralClick(view);
                    }
                }
            });
        }
        if(positiveText!=null){
            alertDialogBuilder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(!(iDialogClick==null)){
                        iDialogClick.positiveClick(view);
                    }

                }
            });
        }
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
       return alertDialog;
    }
    public static QQDialog showQqDialogForEditText(Context context,String title, String hint, String negativeText, String posiviveText, final IDialog iDialog){
        final QQDialog f=new QQDialog(context,R.style.CustomDialog);
        f.setViewLine(QQDialog.setLineColor.BLUE);
        f.setTitle(title);
        f.setEditText("", hint); //默认文字     提示文字
        f.setNegativeButton(negativeText, QQDialog.setColors.DEFAULT, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f.dismiss();
                iDialog.negativeOnclick(f.getEditText());
            }
        });
        f.setPositiveButton(posiviveText, QQDialog.setColors.DEFAULT, new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                f.dismiss();
                iDialog.positiveOnclick(f.getEditText());
            }
        });
        f.show();
        return f;
    }
    /**
     * 打开url
     * @param url
     */
    public static void openUrl(Context c,String url){
        Intent intent = new Intent();
        intent.putExtra("url",url);
        intent.setClass(c, X5Activity.class);
        c.startActivity(intent);
    }
    /**
     * qq dialog 点击回调
     */
    public interface IDialog{
        public void positiveOnclick(String param);
        public void negativeOnclick(String param);
    }
    /**
     * dialog 点击事件的回调接口
     */
    public interface IDialogClick{
        public void neutralClick(View view);
        public void negativeClick(View view);
        public void positiveClick(View view);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("kgx","downX:"+downX+"\t"+"downY:"+downY);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                Log.i("kgx","downX:"+downX+"\t"+"downY:"+downY);
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) event.getX();
                upY = (int) event.getY();
                //向右滑动
                if(upX-downX>200){

                }
                //向左滑动
                if(downX -upY>200){

                }
                Log.i("kgx","d=upX:"+upX+"\t"+"upY:"+upY);
                break;
        }
        return super.onTouchEvent(event);
    }

    public static void developing(View view){
        Snackbar snackbar = Snackbar.make(view,"该功能开发者正在日以继夜的开发中ε≡٩(๑>₃<)۶",3000);
        snackbar.setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.setActionTextColor(Color.parseColor("#5ca2ff"));
        snackbar.show();
    }
}
