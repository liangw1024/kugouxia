package com.liangwei.kugouxia.ui.activity.tools;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liangwei.studio.frame.BaiDuTranslate;
import com.liangwei.studio.frame.LwTTS;
import com.liangwei.studio.net.INetCallback;
import com.liangwei.studio.other.TextUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.AppConfig;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.ShareUtils;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.kugouxia.frame.loading.WbLoadingDialog;

/**
 * 一个酷译act
 */
public class CoolTranslateActivity extends BaseActivity {
    @BindView(R.id.activity_cool_translate_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_cool_translate_et_BeforeTranslate) MaterialEditText et_beforeTranslate;
    @BindView(R.id.activity_cool_translate_btn_BeforeTranslateLanguage) Button btn_before;
    @BindView(R.id.activity_cool_translate_btn_BehideTranslateLanguage) Button btn_behide;
    @BindView(R.id.activity_cool_translate_tv_translateOutput) TextView tv_translateOutput;
    private BaiDuTranslate baiDuTranslate = new BaiDuTranslate(AppConfig.BAIDU_APP_ID,AppConfig.BAIDU_APP_KEY);

    String[] languages = {"中文", "英语", "粤语", "文言文", "日语", "韩文", "法语", "西班牙文", "泰语","阿拉伯语","俄语","葡萄牙语","德语","荷兰语","越南语"};
    String[] languageCode = {"zh","en","yue","wyw","jp","kor","far","spa","th","ara","ru","pt","de","nl","vie"};
    private WbLoadingDialog loadingDialog = null;
    //源语言
    public String formLanguage = "auto";
    //目标语言
    public String toLanguage = "zh";
    public LwTTS lwTTS = null;
    public String translateText = null;
    //翻译回调接口
    private INetCallback iNetCallback = new INetCallback() {
        @Override
        public void success(String content) {
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
                JSONObject jsonTransResult = (JSONObject) jsonArray.get(0);
                String value = jsonTransResult.getString("dst");
                translateText = value;
                        loadingDialog.dismiss();
                        tv_translateOutput.setText("翻译结果:"+value);
                        tv_translateOutput.setTextColor(Color.parseColor("#545454"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void fail(Exception e, String detail) {
            loadingDialog.dismiss();
            ToastUtils.ShowToast(getApplicationContext(),"翻译失败:"+detail);
        }
    };

    /*choose target language*/
    @OnClick(R.id.activity_cool_translate_btn_BehideTranslateLanguage) public void chooseBehindTranslateLanguage() {
        chooseLanguage();
    }
    //翻译tab
    @OnClick(R.id.activity_cool_translate_fab_translate) public void translate() {
        loadingDialog = new WbLoadingDialog(CoolTranslateActivity.this);
        loadingDialog.setLoadingText("请求中...");
        loadingDialog.setTouchOutSide(false);
        loadingDialog.show();
        String query = et_beforeTranslate.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                baiDuTranslate.translate(CoolTranslateActivity.this,query,formLanguage,toLanguage,iNetCallback);

            }
        }).start();

    }
    //复制
    @OnClick(R.id.activity_cool_translate_iv_copy) public void copy(){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(tv_translateOutput.getText().toString().replace("翻译结果:",""));
        ToastUtils.ShowToast(getApplicationContext(),"复制成功");
    }
    //speech
    @OnClick(R.id.activity_cool_translate_iv_pronunciation) public void pronunciation(){
        lwTTS.read(tv_translateOutput.getText().toString().replace("翻译结果:",""));
    }
    //倒换文本
    @OnClick(R.id.activity_cool_translate_iv_opposite) public void opposite(){
        tv_translateOutput.setText("倒换文字:"+ TextUtils.oppsite(translateText));
    }
    //share text
    @OnClick(R.id.activity_cool_translate_iv_share) public void share(){
        ShareUtils shareUtil = new ShareUtils();
        shareUtil.regToQQ(context);//向QQ终端注册appID
        shareUtil.shareToQQ(CoolTranslateActivity.this, "酷狗侠", et_beforeTranslate.getText().toString()+"   翻译结果："+translateText, "http://cdnweb.b5m.com/web/cmsphp/article/201506/abac2829d13153036f766eb4f3b96f22.jpg","http://www.baidu.com", new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {
                ToastUtils.ShowToast(getApplicationContext(),"fail:"+uiError.errorMessage);
            }

            @Override
            public void onCancel() {

            }
        });
    }
    @Override
    public int getContentView() {
        return R.layout.activity_cool_translate;
    }

    @Override
    public void mOncreate() {
       lwTTS = LwTTS.getInstance(getApplicationContext(), Locale.CHINESE);
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

    public void chooseLanguage() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CoolTranslateActivity.this);
        alertDialog.setTitle("请选择翻译目标语言");
        alertDialog.setSingleChoiceItems(languages, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toLanguage = languageCode[i];
                btn_behide.setText(languages[i]);
                dialogInterface.dismiss();
            }
        });

        alertDialog.create().show();
    }


}
