package com.liangwei.kugouxia.ui.activity.classify.qqtools;

import android.content.Intent;
import android.view.View;

import com.tencent.qq.widget.QQDialog;

import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.utils.QqUtils;
import com.liangwei.kugouxia.ui.activity.tools.QqCardMessageActivity;

public class QqToolActivity extends BaseActivity {
    //QQ简约名片
    @OnClick(R.id.activity_qq_tool_btn_concise_visitingCard) public void conciseVisitingCard(){
        final QQDialog d=new QQDialog(this,R.style.CustomDialog);
        d.setViewLine(QQDialog.setLineColor.BLUE);
        d.setTitle("QQ简约名片"); //标题
        d.setNeutralButton("简约蓝", QQDialog.setColors.DEFAULT,new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                d.dismiss();
                QqUtils.setQqConciseVisitingCard(getApplicationContext(),QqUtils.CONCISE_VISITING_BLUE);
            }
        });
        d.setNegativeButton("简约粉", QQDialog.setColors.DEFAULT,new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                d.dismiss();
                QqUtils.setQqConciseVisitingCard(getApplicationContext(),QqUtils.CONCISE_VISITING_PINK);
            }
        });
        d.setPositiveButton("简约绿", QQDialog.setColors.DEFAULT, new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                d.dismiss();
                QqUtils.setQqConciseVisitingCard(getApplicationContext(),QqUtils.CONCISE_VISITING_GREEN);
            }
        });
        d.show();
    }
    //QQ恶搞消息
    @OnClick(R.id.activity_qq_tool_btn_qqcardmsg) public void qqCardMsg(){
        startActivity(new Intent(this, QqCardMessageActivity.class));
    }
    //QQ永久svip
    @OnClick(R.id.activity_qq_tool_btn_svip) public void svip(){
        openUrl(this,"http://www.wbdaishua.top");
    }
    //QQ强制聊天
    @OnClick(R.id.activity_qq_tool_btn_forceContact) public void forceContact(){
        QQDialog qqDialog = new QQDialog(this);
        qqDialog.setTitle("QQ强制聊天");
        qqDialog.setEditText("1947504030","请输入对方的QQ号码");
        qqDialog.setPositiveButton("开始聊天", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QqUtils.forceChat(getApplicationContext(),qqDialog.getEditText());
            }
        });
        qqDialog.show();
    }
    //QQ等级加速0.2天
    @OnClick(R.id.activity_qq_tool_btn_levelSpeedUp) public void levelSpeedUp(){
        QqUtils.levelSpeedUp(getApplicationContext());
    }
    //开关QQ空间
    @OnClick(R.id.activity_qq_tool_btn_switchQzon) public void switchQzone(){
        String url = null;
        final QQDialog b=new QQDialog(QqToolActivity.this,R.style.CustomDialog);
        b.setViewLine(QQDialog.setLineColor.BLUE);
        b.setTitle("QQ空间开关");
        b.setNeutralButton("关闭QQ空间", QQDialog.setColors.BLACK, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QqToolActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        b.dismiss();
                    }
                });
                Intent intent = new Intent();
                intent.putExtra("url","http://imgcache.qq.com/qzone/web/qzone_submit_close.html");
                intent.setClassName("com.liangwei.kugouxia",
                        "com.liangwei.kugouxia.frame.tbs.X5Activity");
                startActivity(intent);
            }
        });
        b.setPositiveButton("开启QQ空间", QQDialog.setColors.DEFAULT ,new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                QqToolActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        b.dismiss();
                    }
                });
                Intent intent = new Intent();
                intent.putExtra("url","http://imgcache.qq.com/qzone/web/load2.htm");
                intent.setClassName("com.liangwei.kugouxia",
                        "com.liangwei.kugouxia.frame.tbs.X5Activity");
                startActivity(intent);
            }
        });
        b.show();
    }
    @OnClick(R.id.activity_qq_tool_card_price) public void qNumPrice(){
        openUrl(this,"http://qnum.lwstudio.top");
    }
    @OnClick(R.id.activity_qq_tool_card_gift) public void gameGift(){
        openUrl(this,"http://www.coolapk.com/apk/com.mengtaowangluo");
    }



    @Override
    public int getContentView() {
        return R.layout.activity_qq_tool;
    }

    @Override
    public void mOncreate() {

    }

    @Override
    public void initToolbar() {

    }
}
