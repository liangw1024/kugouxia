package com.liangwei.kugouxia.ui.activity.tools.AnalogVideoCall;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.TextView;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.AnalogVideoBean;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.CustomView.VideoBackgroundView;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.kugouxia.frame.loading.WbLoadingDialog;

public class AnalogVideoCallActivity extends BaseActivity {
    private AnalogVideoBean analogVideoBean = null;
    @BindView(R.id.activity_analog_video_call_videobg) VideoBackgroundView bg_video;
    @BindView(R.id.activity_analog_video_call_tv_timer) TextView tv_timer;
    int timerStartWith = 1;
    @Override
    public int getContentView() {
        return R.layout.activity_analog_video_call;
    }

    @Override
    public void mOncreate() {

        //timer
        CountDownTimer countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {
                tv_timer.setText("00:"+(timerStartWith+=1));
            }

            @Override
            public void onFinish() {

            }
        }.start();
        //得到put过来的数据
        analogVideoBean = (AnalogVideoBean) getIntent().getSerializableExtra("analogVideoBean");
        WbLoadingDialog wbLoadingDialog = new WbLoadingDialog(this,"视频加载中",false);
        wbLoadingDialog.show();
        //开始播放
        bg_video.setVideoURI(Uri.parse(analogVideoBean.getVideoUrl()));
        bg_video.start();
        //取消加载dialog
        bg_video.setOnPreparedListener(listener->{
            wbLoadingDialog.dismiss();
        });
        //循环播放
        bg_video.setOnCompletionListener(completion->{
            bg_video.start();
        });
        bg_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                ToastUtils.ShowToast(context,"播放错误码:"+i);
                finish();
                return false;
            }
        });
    }

    @Override
    public void initToolbar() {

    }
    //返回重启加载
    @Override
    protected void onRestart() {

        super.onRestart();
    }

    //防止锁屏或者切出的时候，在播放
    @Override
    protected void onStop() {
        super.onStop();
        bg_video.resume();
    }
}
