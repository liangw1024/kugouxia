package com.liangwei.kugouxia.ui.activity.tools;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import butterknife.BindView;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.ShareUtils;

public class QqCardMessageActivity extends BaseActivity{
    @BindView(R.id.activity_qq_card_message_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_qq_card_message_et_title) EditText et_title;
    @BindView(R.id.activity_qq_card_message_et_msg) EditText et_msg;
    @BindView(R.id.activity_qq_card_message_et_imgUrl) EditText et_imgUrl;
    @BindView(R.id.activity_qq_card_message_et_url) EditText et_url;
    @BindView(R.id.activity_qq_card_message_btn_send) Button btn_send;
    @BindView(R.id.activity_qq_card_message_btn_makeWangJIng) Button btn_wangJIn;
    @BindView(R.id.activity_qq_card_message_btn_makehongBao) Button btn_hongBao;
    @BindView(R.id.activity_qq_card_message_btn_makePianzan) Button btn_pianZan;
    @BindView(R.id.activity_qq_card_message_btn_makeJIaquan) Button btn_jiaQun;
    @OnClick(R.id.activity_qq_card_message_btn_makeWangJIng) public void clickWangJin(){
        et_title.setText("网警正在监控本群的聊天记录");
        et_msg.setText("编号1547 网络警察已监控本群,请谨慎发言");
        et_imgUrl.setText("http://i1.hdslb.com/bfs/face/85c1f30cdabfcb3869aa616bee13d52ee02af835.jpg");
        et_url.setText("http://kgx.luaapp.cn");
    }
    @OnClick(R.id.activity_qq_card_message_btn_makehongBao) public void clickHongbao(){
        et_title.setText("[QQ红包]发红包啦！");
        et_msg.setText("赶紧拆开吧！");
        et_imgUrl.setText("https://mqq-imgcache.gtimg.cn/res/mqq/hongbao/img/message_logo_100.png");
        et_url.setText("http://kgx.luaapp.cn");
    }
    @OnClick(R.id.activity_qq_card_message_btn_makeJIaquan) public void clickJiaqun(){
        et_title.setText("免费领取100QB");
        et_msg.setText("点击加群 免费领取100QB！");
        et_imgUrl.setText("http://cdnweb.b5m.com/web/cmsphp/article/201506/abac2829d13153036f766eb4f3b96f22.jpg");
        et_url.setText("https://jq.qq.com/?_wv=1027&k=5NvoSwz");
    }
    @OnClick(R.id.activity_qq_card_message_btn_makePianzan) public void clickPianZan(){
        et_title.setText("18岁少女去超市打工,没想到...");
        et_msg.setText("家住重庆的小丽初中辍学,经邻居介绍去超市打临时工,没想到...");
        et_imgUrl.setText("http://img1.imgtn.bdimg.com/it/u=859484734,1216815159&fm=23&gp=0.jpg");
        et_url.setText("http://kgx.luaapp.cn");
    }
    @OnClick(R.id.activity_qq_card_message_btn_send) public void clickSend(){
        //String url="mqqapi://share/to_fri?src_type=app&version=1&file_type=news&image_url=aHR0cDovL2kxLmhkc2xiLmNvbS9iZnMvZmFjZS84NWMxZjMwY2RhYmZjYjM4NjlhYTYxNmJlZTEzZDUyZWUwMmFmODM1LmpwZw==&title=572R6K2m5q2j5Zyo55uR5o6n5pys576k6IGK5aSp6K6w5b2V&description=57yW5Y+3MjEzNiDnvZHnu5zorablr5/lt7Lnm5HmjqfmnKznvqQs6K+36LCo5oWO5Y+R6KiA&share_id=100302037&url=aHR0cDovL3d3dy5iYWlkdS5jb20=&app_name=UVHlt6Xlhbflrp3nrrE=&req_type=MQ==&cflag=MA==";
//        String url="mqqapi://share/to_fri?src_type=app&version=1&file_type=news&image_url="+ Base64Utils.encodeData(et_imgUrl.getText().toString())+"&title="+Base64Utils.encodeData(et_title.getText().toString())+"&description="+Base64Utils.encodeData(et_msg.getText().toString())+"&share_id=1101070898&url="+Base64Utils.encodeData(et_url.getText().toString())+"=&app_name=UVHljaHniYfmtojmga/lj5HpgIE=&req_type=MQ==&cflag=MA==";
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        ShareUtils shareUtil = new ShareUtils();
        shareUtil.regToQQ(context);//向QQ终端注册appID
        shareUtil.shareToQQ(QqCardMessageActivity.this, et_title.getText().toString(), et_msg.getText().toString(), et_imgUrl.getText().toString(), et_url.getText().toString(), new IUiListener() {
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


    @Override
    public int getContentView() {
        return R.layout.activity_qq_card_message;
    }

    @Override
    public void mOncreate() {

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