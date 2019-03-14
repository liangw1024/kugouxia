package com.liangwei.kugouxia.frame;

/**
 * Created by weibao on 2018/5/6.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/**
 *
 * @author _H_JY
 * 2015-8-27下午4:26:35
 *
 * 分享工具类：可以分享到微信好友、微信收藏、微信朋友圈、QQ好友、QQ空间、短信
 */
public class ShareUtils {

    public static final String QQ_APP_ID = "1106836060";//改成你在QQ开放平台审核通过的appID
    private Tencent tencent;
    /**要分享必须先注册(QQ)*/
    public void regToQQ(Context context) {
        tencent = Tencent.createInstance(QQ_APP_ID, context);
    }

    public Tencent getTencent() {
        return tencent;
    }

    public void setTencent(Tencent tencent) {
        this.tencent = tencent;
    }


    public String getQqAppId() {
        return QQ_APP_ID;
    }



    /**分享到短信*/
    public Intent sendSMS(String description) {

        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra("sms_body", description);
        sendIntent.setType("vnd.android-dir/mms-sms");

        return sendIntent;

    }




    /**分享到QQ好友*/
    public void shareToQQ(Activity activity, String title, String description, String imgUrl,String url, IUiListener uiListener){
        Bundle qqParams = new Bundle();
        qqParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        if (!title.isEmpty()){
            qqParams.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        }
        if (!description.isEmpty()){
            qqParams.putString(QQShare.SHARE_TO_QQ_SUMMARY,  description);
        }
        if (!imgUrl.isEmpty()){
            qqParams.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imgUrl);
        }
        if (!url.isEmpty()){
            qqParams.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  url);
        }
        qqParams.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "酷狗侠");
        tencent.shareToQQ(activity, qqParams, uiListener);

    }

    /**
     * 发送消息
     */
    public static void sendQqMsg(Activity activity,String msg){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setClassName("com.tencent.mobileqq","com.tencent.mobileqq.activity.JumpActivity");
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        intent.putExtra("chooser","分享");
        activity.startActivity(intent);
    }
    /**分享到QQ空间*/
    public void shareToQzone(Activity activity,String url,String imageUrl,String shareTitle,String description,IUiListener uiListener){

        Bundle qzoneParams = new Bundle();
        qzoneParams.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareTitle);//必填
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,  description);
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        ArrayList<String> imageUrlList =new ArrayList<String>();
        imageUrlList.add(imageUrl);
        qzoneParams.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrlList);
        tencent.shareToQzone(activity, qzoneParams, uiListener);

    }
}