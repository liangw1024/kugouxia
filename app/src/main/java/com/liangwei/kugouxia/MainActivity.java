package com.liangwei.kugouxia;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liangwei.kugouxia.frame.AppConfig;
import com.liangwei.kugouxia.frame.BaseActivity;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import cn.bmob.v3.Bmob;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 *启动act
 */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    /*需要动态申请的permission*/
    public static String PERMISSION[]= {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
    @BindView(R.id.activity_main_tv_timer) TextView tv_timer;
    @Override
    public int getContentView() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(Color.WHITE);
        }
        return R.layout.activity_main;
    }
    @Override
    public void mOncreate() {
        Bmob.initialize(this, AppConfig.BMOB_KEY);
        if(isHasPermission(PERMISSION)){

                timer(KuGouMainActivity.class);
        }else{
            //没有权限 开始请求权限
            EasyPermissions.requestPermissions(this,"软件需要获取权限才才可以正常运行 请允许",0,PERMISSION);
        }

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //下面两个方法是实现EasyPermissions的EasyPermissions.PermissionCallbacks接口
    //分别返回授权成功和失败的权限
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i(TAG, "获取成功的权限" + perms);
        if(perms.size()==PERMISSION.length){
            startActivity(new Intent(MainActivity.this,KuGouMainActivity.class));
        }else{
            new AppSettingsDialog.Builder(this).setRationale("点击确定后找到 权限.. 打开权限开关即可 ").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    showToast(getApplicationContext(),"软件没有权限 ，请重新打开 确定","long");
                }
            }).setTitle("请允许所有的权限 才正常运行").build().show();
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i(TAG, "获取失败的权限" + perms);
            //显示dialog来提示用户去设置

            new AppSettingsDialog.Builder(this).setRationale("点击确定后找到 权限.. 打开权限开关即可 ").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    showToast(getApplicationContext(),"软件没有权限 ，请重新打开 确定","long");
                }
            }).setTitle("请允许所有的权限 才正常运行").build().show();
    }

    /**
     *判断是否有权限
     * @param permission
     * @return
     */
    public boolean isHasPermission(Object permission){
        boolean result = false;
        if(EasyPermissions.hasPermissions(this,PERMISSION)){
            result = true;
        }else{
            result = false;
        }
        return result;
    }
    private void timer(Class<?> target){
        tv_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.this,target,null,0);
                finish();
            }
        });
        /*第一个millisInFuture是指要倒计时的总时间，单位是long ms。第二个參数countDownInterval是指倒计时的频率，是一次倒计时1s 还是一次倒计时2s*/
        CountDownTimer timer = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long l) {
                tv_timer.post(new Runnable() {
                    @Override
                    public void run() {
                        int temp = (int) (l/1000-1);
                        tv_timer.setText("跳过:"+temp+"s");
                        if(temp == 0){
                            startActivity(MainActivity.this,target,null,0);
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


}
