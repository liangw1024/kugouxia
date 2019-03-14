package com.liangwei.kugouxia.ui.activity.tools;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liangwei.studio.other.TextUtils;

import butterknife.BindView;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.CustomView.ClickCopyTextView;
import com.liangwei.kugouxia.frame.ToastUtils;


public class MakeSpecialTextActivity extends BaseActivity{
    @BindView(R.id.activity_make_special_text_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_make_special_text_et_src) EditText et_src;
    @BindView(R.id.activity_make_special_text_btn_make) Button btn_make;
    @BindView(R.id.activity_make_special_text_tv_ban) ClickCopyTextView tv_ban;
    @BindView(R.id.activity_make_special_text_tv_capsule) ClickCopyTextView tv_capsule;
    @BindView(R.id.activity_make_special_text_tv_circle) ClickCopyTextView tv_circle;
    @BindView(R.id.activity_make_special_text_tv_daiCi) ClickCopyTextView tv_daici;
    @BindView(R.id.activity_make_special_text_tv_delete) ClickCopyTextView tv_delete;
    @BindView(R.id.activity_make_special_text_tv_diamond) ClickCopyTextView tv_diamond;
    @BindView(R.id.activity_make_special_text_tv_huaten) ClickCopyTextView tv_huaten;
    @BindView(R.id.activity_make_special_text_tv_juhua) ClickCopyTextView tv_juhua;
    @BindView(R.id.activity_make_special_text_tv_me) ClickCopyTextView tv_me;
    @BindView(R.id.activity_make_special_text_tv_xin) ClickCopyTextView tv_xin;
    @BindView(R.id.activity_make_special_text_tv_stick) ClickCopyTextView tv_stick;
    @BindView(R.id.activity_make_special_text_tv_king) ClickCopyTextView tv_king;
    @BindView(R.id.activity_make_special_text_tv_border) ClickCopyTextView tv_border;
    @BindView(R.id.activity_make_special_text_tv_wing) ClickCopyTextView tv_wing;
    @OnClick(R.id.activity_make_special_text_btn_make) public void makeText(){
        String text = et_src.getText().toString();
        tv_capsule.setText(TextUtils.makeCapsuleText(text));
        tv_circle.setText(TextUtils.makeCirclesText(text));
        tv_daici.setText(TextUtils.makeDaiCiText(text));
        tv_ban.setText(TextUtils.makeBanText(text));
        tv_delete.setText(TextUtils.makeDeleteText(text));
        tv_diamond.setText(TextUtils.makeDiamondText(text));
        tv_huaten.setText(TextUtils.makeHuaTengText(text));
        tv_juhua.setText(TextUtils.makeJuHuaText(text));
        tv_stick.setText(TextUtils.makeStickText(text));
        tv_xin.setText(TextUtils.makeXinText(text));
        tv_me.setText(TextUtils.makeMeText(text));
        tv_king.setText(TextUtils.makeKingText(text));
        tv_border.setText(TextUtils.makeBorderText(text));
        tv_wing.setText(TextUtils.makeWingText(text));
        ToastUtils.ShowToast(getApplicationContext(),"制作成功 点击文字即可复制");

    }
    @Override
    public int getContentView() {
        return R.layout.activity_make_special_text;
    }

    @Override
    public void mOncreate() {
        String text = "良伟";
        tv_capsule.setText(TextUtils.makeCapsuleText(text));
        tv_circle.setText(TextUtils.makeCirclesText(text));
        tv_daici.setText(TextUtils.makeDaiCiText(text));
        tv_ban.setText(TextUtils.makeBanText(text));
        tv_delete.setText(TextUtils.makeDeleteText(text));
        tv_diamond.setText(TextUtils.makeDiamondText(text));
        tv_huaten.setText(TextUtils.makeHuaTengText(text));
        tv_juhua.setText(TextUtils.makeJuHuaText(text));
        tv_stick.setText(TextUtils.makeStickText(text));
        tv_xin.setText(TextUtils.makeXinText(text));
        tv_me.setText(TextUtils.makeMeText(text));
        tv_king.setText(TextUtils.makeKingText(text));
        tv_border.setText(TextUtils.makeBorderText(text));
        tv_wing.setText(TextUtils.makeWingText(text));
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