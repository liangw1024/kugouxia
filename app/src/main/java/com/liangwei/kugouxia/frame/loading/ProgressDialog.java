package com.liangwei.kugouxia.frame.loading;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liangwei.kugouxia.R;

/**
 * Created by weibao on 2018/3/23.
 */

public class ProgressDialog extends AlertDialog {
    Context context;
    ProgressBar progressBar;
    TextView tv_speed;
    public ProgressDialog( Context context) {
        super(context);
        this.context = context ;
        create(context);
    }
    private void create(final Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_progress_dialog,null);
        progressBar = view.findViewById(R.id.layout_progress_dialog_progress);
        tv_speed = view.findViewById(R.id.layout_progress_dialog_speed);
        setCanceledOnTouchOutside(false);
        // 设置布局，设为全屏
        setView(view);
       // setContentView(view, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public TextView getTv_speed(){
        return  tv_speed;
    }
    public ProgressBar getProgressBar(){
        return progressBar;
    }

}
