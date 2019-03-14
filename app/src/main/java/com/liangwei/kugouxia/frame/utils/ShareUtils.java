package com.liangwei.kugouxia.frame.utils;

import android.content.Context;
import android.content.Intent;

public class ShareUtils {
    public static void shareText(Context context,String shareText){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,shareText);
        context.startActivity(Intent.createChooser(intent,"酷狗侠-分享藏头诗"));
    }

}
