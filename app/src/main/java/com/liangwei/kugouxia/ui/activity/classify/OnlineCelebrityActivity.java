package com.liangwei.kugouxia.ui.activity.classify;


import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.liangwei.kugouxia.frame.ShareUtils;
import com.liangwei.studio.ui.btn.FloatingButtonsItem;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.CustomView.CircleImageView;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.kugouxia.ui.activity.tools.AnalogVideoCall.AnalogVideoActivity;
import com.liangwei.kugouxia.ui.activity.tools.led.MakeLedActivity;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class OnlineCelebrityActivity extends BaseActivity {
    @BindView(R.id.activity_online_celebrity_iv_logo)
    CircleImageView iv_logo;
    @BindView(R.id.activity_online_celebrity_fbi_flashLight)
    FloatingButtonsItem fbi_openFlashLight;

    //led word
    @OnClick(R.id.activity_online_celebrity_btn_led)
    public void led() {
        startAct(MakeLedActivity.class, null);
    }

    //情头匹配
    @OnClick(R.id.activity_online_celebrity_btn_loversHeadMatch)
    public void loverHeadMatch() {
        BaseActivity.openUrl(this, "http://qq.okjike.com/#/");
    }

    //模拟video
    @OnClick(R.id.activity_online_celebrity_btn_analogVideo)
    public void analogVideo() {
        startAct(AnalogVideoActivity.class, null);
    }

    //character draw
    @OnClick(R.id.activity_online_celebrity_btn_character)
    public void character() {
        View view = findViewById(R.id.activity_online_celebrity_btn_led);
        BaseActivity.developing(view);
    }

    //writer code
    @OnClick(R.id.activity_online_celebrity_btn_writeCode)
    public void writer() {
        BaseActivity.openUrl(this, "http://tool.uixsj.cn/code-blast/");
    }

    //hacker number rain
    @OnClick(R.id.activity_online_celebrity_btn_numRain)
    public void numRain() {
        BaseActivity.openUrl(this, "http://tool.uixsj.cn/hack-html5/");
    }

    //装逼神器
    @OnClick(R.id.activity_online_celebrity_card_zhuangbi)
    public void zhuangbi() {
        BaseActivity.openUrl(this, "http://wx.deepba.com/");
    }

    //zxing
    @OnClick(R.id.activity_online_celebrity_card_zxing)
    public void zxing() {
        BaseActivity.openUrl(this, "http://tool.uixsj.cn/ewm/");
    }

    //express
    @OnClick(R.id.activity_online_celebrity_fbi_express)
    public void express() {
        BaseActivity.openUrl(this, "http://www.51bbw.cn");
    }
    //FlashLight
    @OnClick(R.id.activity_online_celebrity_fbi_flashLight)
    public void flashLight() {
        if (isFlashLightRunning) {
            stopLoopFlashLight();
            fbi_openFlashLight.setText("极速闪光");
            fbi_openFlashLight.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OnlineCelebrityActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            dialogBuilder.setTitle("请输入闪光速度");
            View viewSetFlashLightSpeed = LayoutInflater.from(this).inflate(R.layout.dialog_set_flash_light_speed, null);
            dialogBuilder.setView(viewSetFlashLightSpeed);
            MaterialEditText met_speed = viewSetFlashLightSpeed.findViewById(R.id.dialog_set_flash_light_speed_et_value);
            dialogBuilder.setPositiveButton("开始", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fbi_openFlashLight.setText("运行中 点我关闭");
                    fbi_openFlashLight.setTextColor(Color.RED);
                    startLoopFlashLight(Integer.valueOf(met_speed.getText().toString()));
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();

        }

    }
    //spoof
    @OnClick(R.id.activity_online_celebrity_fbi_spoof)
    public void spoof() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("警告：请不要恶搞心脏病患者");
        builder.setNegativeButton("恶搞他人", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showSelectSpoof();
            }
        });
        builder.setPositiveButton("测试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openUrl(OnlineCelebrityActivity.this,"http://pay.lwstudio.top/spoof.html");
            }       
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void showSelectSpoof(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("请选择你要恶搞的类型");
        builder.setMessage("警告：请不要恶搞心脏病患者");
        builder.setNegativeButton("娇喘恶搞", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShareUtils shareUtil = new ShareUtils();
                shareUtil.regToQQ(context);//向QQ终端注册appID

                shareUtil.shareToQQ(OnlineCelebrityActivity.this, "我给你送了一个惊喜快来领取吧", "请领取你的svip","https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=725054159,3754779053&fm=58&bpow=800&bpoh=1003","http://pay.lwstudio.top/ng/", new IUiListener() {
                    @Override
                    public void onComplete(Object o) {

                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        builder.setPositiveButton("女鬼恶搞", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShareUtils shareUtil = new ShareUtils();
                shareUtil.regToQQ(context);//向QQ终端注册appID
                shareUtil.shareToQQ(OnlineCelebrityActivity.this, "我给你送了一个惊喜快来领取吧", "请领取你的svip","https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=725054159,3754779053&fm=58&bpow=800&bpoh=1003","http://pay.lwstudio.top/jc", new IUiListener() {
                    @Override
                    public void onComplete(Object o) {

                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //android 5.0使用
    Camera camera = null;
    //android 5.0上使用
    CameraManager cameraManager = null;
    Timer openTimer = null;
    TimerTask openFlashLightTimerTask = null;
    //快速闪光是否在运行
    boolean isFlashLightRunning = false;

    @Override
    public int getContentView() {
        return R.layout.activity_online_celebrity;
    }

    @Override
    public void mOncreate() {
        Glide.with(getApplicationContext()).load("http://q2.qlogo.cn/headimg_dl?bs=1947504030&dst_uin=1947504030&dst_uin=1947504030&;dst_uin=1947504030&spec=100&url_enc=0&referer=bu_interface&term_type=PC").into(iv_logo);

    }

    @Override
    public void initToolbar() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLoopFlashLight();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLoopFlashLight();
    }

    //开启快速闪光
    private void startLoopFlashLight(int speed) {
        isFlashLightRunning = true;
        openTimer = new Timer();
        openFlashLightTimerTask = new TimerTask() {
            @Override
            public void run() {
                openLight();
            }
        };

        openTimer.schedule(openFlashLightTimerTask, 0, speed);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            openTimer.schedule(new TimerTask(){
                @Override
                public void run() {
                    closeLight();
                }
            },0,speed+500);
        }


    }

    //停止快速闪光
    private void stopLoopFlashLight() {
        isFlashLightRunning = false;
        closeLight();
        if (openTimer != null & openFlashLightTimerTask != null) {
            openTimer.purge();
            openTimer.cancel();
            openFlashLightTimerTask.cancel();
        }




    }

    private void openLight() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            try {
                camera = android.hardware.Camera.open();
                camera.startPreview();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
            } catch (Exception e) {
                ToastUtils.ShowToast(getApplicationContext(), "手机不支持闪光灯：" + e.toString());
            }
        } else {

            try {

                cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                cameraManager.setTorchMode("0", true);

            } catch (CameraAccessException e) {
                ToastUtils.ShowToast(getApplicationContext(), "手机不支持闪光灯：" + e.toString());
            }
        }


    }

    private void closeLight() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
          
            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.release();
                camera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (cameraManager != null) {
                try {
                    cameraManager.setTorchMode("0", false);
                    cameraManager = null;
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
