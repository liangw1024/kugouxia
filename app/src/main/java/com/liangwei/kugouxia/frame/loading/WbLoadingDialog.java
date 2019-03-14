package com.liangwei.kugouxia.frame.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.liangwei.kugouxia.R;

/**
 * Created by 20385 on 2018/3/10.
 */

public class WbLoadingDialog extends Dialog {
    TextView tv_loadText = null;
    private boolean isTouchOutSide = false;
    private boolean cancelable = false;
    private boolean isShow =false;
    public WbLoadingDialog(Context context) {
        super(context);
        //对话框背景透明
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.wb_loading);
        tv_loadText = (TextView) findViewById(R.id.wb_loading_text);
        setCanceledOnTouchOutside(isTouchOutSide);
        setCancelable(cancelable);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if(i== KeyEvent.KEYCODE_BACK){
                    return true;
                }else{
                    return false;
                }
            }
        });
    }
    public WbLoadingDialog(Context context,String loadingText,boolean isCanceledTouchOutside){
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.wb_loading);
        tv_loadText = (TextView) findViewById(R.id.wb_loading_text);
        tv_loadText.setText(loadingText);
        setCanceledOnTouchOutside(isCanceledTouchOutside);
        setCancelable(cancelable);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if(i== KeyEvent.KEYCODE_BACK){
                    return true;
                }else{
                    return false;
                }
            }
        });
    }
    public void setLoadingText(String text){
        tv_loadText.setText(text);
    }
    public void setTouchOutSide(boolean touchOutSide) {
        isTouchOutSide = touchOutSide;
    }
    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }


}
