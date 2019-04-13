package com.liangwei.studio.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangwei.studio.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IOSDialog {
    public Context context;
    private Dialog dialog;
    private View view;
    /**
     * icon
     **/
    public ImageView iv_icon;
    /**
     * 标题 描述
     **/
    public TextView tv_title, tv_description;
    /**
     * 左按钮 中间的按钮 右边的按钮
     **/
    public Button btn_left, btn_center, btn_right;
    private DisplayMetrics displayMetrics;
    private IOnLeftBtnClick iOnLeftBtnClick;
    private IOnCenterBtnClick iOnCenterBtnClick;
    private IOnRightBtnClick iOnRightBtnClick;

    public IOSDialog(@NonNull Context context) {

        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);


    }

    public IOSDialog builder() {
        view = View.inflate(context, R.layout.dialog_ios, null);
        iv_icon = view.findViewById(R.id.dialog_ios_iv_head);
        tv_title = view.findViewById(R.id.dialog_ios_tv_title);
        tv_description = view.findViewById(R.id.dialog_ios_tv_description);
        btn_left = view.findViewById(R.id.dialog_ios_btn_left);
        btn_center = view.findViewById(R.id.dialog_ios_btn_center);
        btn_right = view.findViewById(R.id.dialog_ios_btn_right);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnLeftBtnClick!=null){
                    iOnLeftBtnClick.click(btn_left);
                }
            }
        });
        btn_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnCenterBtnClick!=null){
                    iOnCenterBtnClick.click(btn_center);
                }
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnRightBtnClick!=null){
                    iOnRightBtnClick.click(btn_right);
                }
            }
        });
        dialog = new Dialog(context,R.style.IosDialog);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();

        layoutParams.width = (int) (displayMetrics.widthPixels/1.4);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        dialogWindow.setAttributes(layoutParams);
//        view.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//                .getWidth() * 0.80), WindowManager.LayoutParams.WRAP_CONTENT));

        return this;

    }

    public IOSDialog setImage(Bitmap bitmap){
        if (bitmap != null) {
            iv_icon.setVisibility(View.VISIBLE);
            iv_icon.setImageBitmap(bitmap);
        }
        return this;
    }
    /**
     * 设置标题的文本
     *
     * @param text
     * @return
     */
    public IOSDialog setTitle(String text) {
        if (text != null) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(text);
        }
        return this;
    }

    /**
     * 设置描述的文本
     *
     * @param text
     * @return
     */
    public IOSDialog setDescription(String text) {
        if (text != null) {
            tv_description.setVisibility(View.VISIBLE);
            tv_description.setText(text);
        }
        return this;
    }

    /**
     * 设置左边按钮的文本
     *
     * @param text
     * @return
     */
    public IOSDialog setBtnLeftText(String text) {
        if (text != null) {
            btn_left.setVisibility(View.VISIBLE);
            btn_left.setText(text);
        }
        return this;
    }

    /**
     * 设置center按钮的文本
     *
     * @param text
     * @return
     */
    public IOSDialog setBtnCenterText(String text) {
        if (text != null) {
            btn_center.setVisibility(View.VISIBLE);
            btn_center.setText(text);
        }
        return this;
    }

    /**
     * 设置right按钮的文本
     *
     * @param text
     * @return
     */
    public IOSDialog setBtnRightText(String text) {
        if (text != null) {
            btn_right.setVisibility(View.VISIBLE);
            btn_right.setText(text);

        }
        return this;
    }

    /**
     * 自定义按钮
     *
     * @param btn1
     * @param btn2
     * @param btn3
     * @return
     */
    public IOSDialog setCustomBtn(@Nullable View btn1, @Nullable View btn2, @Nullable View btn3) {
        if (btn1 != null) {
            btn_left = (Button) btn1;
        } else {

            return null;
        }
        if (btn2 != null) {
            btn_center = (Button) btn2;
        } else {
            return null;
        }

        if (btn3 != null) {
            btn_right = (Button) btn3;
        } else {
            return null;
        }
        return this;
    }

    public void show() {
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }

    public void setiOnLeftBtnClick(IOnLeftBtnClick iOnLeftBtnClick) {
        this.iOnLeftBtnClick = iOnLeftBtnClick;
    }

    public void setiOnCenterBtnClick(IOnCenterBtnClick iOnCenterBtnClick) {
        this.iOnCenterBtnClick = iOnCenterBtnClick;
    }

    public void setiOnRightBtnClick(IOnRightBtnClick iOnRightBtnClick) {
        this.iOnRightBtnClick = iOnRightBtnClick;
    }

    public interface IOnLeftBtnClick{
        public void click(View v);
    }
    public interface IOnCenterBtnClick{
        public void click(View v);
    }
    public interface IOnRightBtnClick{
        public void click(View v);
    }
}
