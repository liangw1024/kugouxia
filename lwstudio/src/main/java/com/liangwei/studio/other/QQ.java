package com.liangwei.studio.other;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;

public class QQ
{

    /**
     * 通过qq群号 加群
     *@param context
     *@param num 目标QQ群号
     */
    public static void joinQGroupByNum(Context context,String num){
        Uri uri = Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin="+num+"&card_type=group&source=qrcode");
        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    /**
     * 联系QQ用户
     * @param qqNum 对方的QQ号码
     */
    public static void contactQq(Context context,String qqNum){
        String url="mqqwpa://im/chat?chat_type=wpa&uin="+qqNum;
        Uri uri = Uri.parse(url);
        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    /**
     *跳转到指定的QQ名片
     *@param context
     *@param num 目标QQ
     */
    public static void goVisitingCard(Context context,String num){
        context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("mqqapi://card/show_pslcard?src_type=internal&source=sharecard&version=1&uin="+num)));
    }






}
