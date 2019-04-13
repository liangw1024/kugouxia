package com.liangwei.kugouxia.ui.activity;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pgyersdk.feedback.PgyFeedback;

import butterknife.BindView;
import butterknife.OnClick;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.CustomView.CircleImageView;
import com.liangwei.kugouxia.frame.CustomView.PrinterTextView;
import com.liangwei.kugouxia.frame.utils.QqUtils;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.about_activity_toolbar) Toolbar toolbar;
    @BindView(R.id.about_activity_img_head) CircleImageView img_head;
    @BindView(R.id.about_activity_printerTextview) PrinterTextView printerTextView;
    @BindView(R.id.about_activity_btn_feedback) Button btn_feedback;
    @BindView(R.id.about_activity_btn_CheckUpdate) Button btn_checkUpdate;
    @BindView(R.id.about_activity_btn_contactMe) Button btn_contactMe;
    @BindView(R.id.about_activity_btn_joinQQGroup) Button btn_joinWe;
    @OnClick(R.id.about_activity_btn_feedback) public void feedback(){
        PgyFeedback.getInstance().showDialog(this);

// 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
// 打开沉浸式,默认为false
// FeedbackActivity.setBarImmersive(true);
        PgyFeedback.getInstance().showActivity(this);
    }
    @OnClick(R.id.about_activity_btn_CheckUpdate) public void checkUpdate(){
        checkUpdate();
    }
    @OnClick(R.id.about_activity_btn_contactMe) public void contactMe(){
        QqUtils.forceChat(AboutActivity.this,"2038542046");
    }
    @OnClick(R.id.about_activity_btn_joinQQGroup) public void joinWe(){
        QqUtils.joinQGroup(getApplicationContext(),"5ks1zwX-816483831-l_mumFrN");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    public void mOncreate() {

        printerTextView.startPrintText("因为有你，所以精彩.我一直用心在做  \n\t网络这条路，认真你输了" +
                " \n\t @Copynight 千百度live   QQ:1947504030 ",100);
    }
    @Override
    public void initToolbar() {
       setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
