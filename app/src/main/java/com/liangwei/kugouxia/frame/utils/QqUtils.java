package com.liangwei.kugouxia.frame.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 *QQ工具
 */
public class QqUtils
{

    //简约名片蓝
    public final static int CONCISE_VISITING_BLUE = 0;
    ///简约名片粉
    public final static int CONCISE_VISITING_PINK = 1;
    //简约名片green
    public final static int CONCISE_VISITING_GREEN = 2;

    /**
     * 通过key加群 获取key 请到QQ群官网
     * 发起添加群流程。群号：软件反馈资源群(816483831) 的 key 为： HwlGJlOvELOM0iTx3bnG76FsKJX9yCfi
     * 调用 joinQQGroup(HwlGJlOvELOM0iTx3bnG76FsKJX9yCfi) 即可发起手Q客户端申请加群 软件反馈资源群(816483831)
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     */
    public static boolean joinQQGroupByKey(Context context,String key) {
        Intent intent = new Intent();
        if(key==null){
            intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "HwlGJlOvELOM0iTx3bnG76FsKJX9yCfi"));
        }else{
            intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        }

        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(context,"没有安装QQ 或者qq版本过低",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     *QQ加群
     *@param context
     *@param num 目标QQ群号
     */
    public static void joinQGroup(Context context,String num){
        Uri uri = Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin="+num+"&card_type=group&source=qrcode");
        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    /**
     *QQ强制聊天
     *@param context
     *@param num 目标QQ
     */
    public static void forceChat(Context context,String num){
        String url="mqqwpa://im/chat?chat_type=wpa&uin="+num;
        Uri uri = Uri.parse(url);
        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
    /**
     *跳转到QQ名片
     *@param context
     *@param num 目标QQ
     */
    public static void goVisitingCard(Context context,String num){
        context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("mqqapi://card/show_pslcard?src_type=internal&source=sharecard&version=1&uin="+num)));
    }

    /**
     * 设置QQ简约名片
     * @param context
     * @param colorValue 常量
     */
    public static void setQqConciseVisitingCard(Context context,int colorValue){
        String uri = null;
        switch (colorValue){
            case CONCISE_VISITING_BLUE:
                uri="mqqapi://forward/url?url_prefix=aHR0cHM6Ly9neGgudmlwLnFxLmNvbS9jbHViL3RoZW1lcy9tb2JpbGUvY2FyZC9odG1sL2RldGFpbC5odG1sP2Zyb209c2hhcmUmY2FyZEl0ZW1JZD00NiZfYmlkPXVuZGVmaW5lZCZfd3Y9MTY3ODIzMzc";
                break;
            case CONCISE_VISITING_PINK:
                uri="mqqapi://forward/url?url_prefix=aHR0cHM6Ly9neGgudmlwLnFxLmNvbS9jbHViL3RoZW1lcy9tb2JpbGUvY2FyZC9odG1sL2RldGFpbC5odG1sP2Zyb209c2hhcmUmY2FyZEl0ZW1JZD00Jl9iaWQ9dW5kZWZpbmVkJl93dj0xNjc4MjMzNw";
                break;
            case CONCISE_VISITING_GREEN:
                uri="mqqapi://forward/url?url_prefix=aHR0cHM6Ly9neGgudmlwLnFxLmNvbS9jbHViL3RoZW1lcy9tb2JpbGUvY2FyZC9odG1sL2RldGFpbC5odG1sP25ld1Zlcj0xJnJlcG9ydF9wYWdlX3R5cGU9bXljYXJkJmNhcmRJdGVtSWQ9MTEmX3d2PTE2NzgyMzM3";
                break;
        }
        try{
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
        }catch(Exception e){
            Log.e("kgx","设置QQ concise 名片失败"+e.toString());
            Toast.makeText(context, "设置QQ名片失败 检查到你没有安装QQ:"+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * QQ等级加速
     * @param context
     */
    public static void levelSpeedUp(Context context){
        String url="mqqapi://forward/url?url_prefix=aHR0cDovL3JlYWRlci5zaC52aXAucXEuY29tL2NnaS1iaW4vY29tbW9uX2FzeW5jX2NnaT9nX3RrPTUzODEmcGxhdD0xJnZlcnNpb249Ni42LjYmcGFyYW09JTdCJTIya2V5MCUyMiUzQSU3QiUyMnBhcmFtJTIyJTNBJTdCJTIyYmlkJTIyJTNBMTM3OTI2MDUlN0QlMkMlMjJtb2R1bGUlMjIlM0ElMjJyZWFkZXJfY29tbWVudF9yZWFkX3N2ciUyMiUyQyUyMm1ldGhvZCUyMiUzQSUyMkdldFJlYWRBbGxFbmRQYWdlTXNnJTIyJTdEJTdE";
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
    /**
     *QQ请求接口
     */
    public static class QqRequestInterface{
        public static void getQqHead(Context context, String num, CallBack callback){

        }
        public static void getQqName(Context context,String num,CallBack callback){

        }
        public static void getQqVisitingCard(Context context,String num,CallBack callback){

        }

    }
    public static interface CallBack{
        public void loading();
        public void success(List<Object> result);
        public void fail(String errMsg);

    }

}
