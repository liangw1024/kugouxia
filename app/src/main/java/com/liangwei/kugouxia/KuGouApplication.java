package com.liangwei.kugouxia;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;


import com.pgyersdk.crash.PgyCrashManager;

import com.tencent.smtt.sdk.QbSdk;


/**
 * Created by weibao on 2018/3/19.
 */

public class KuGouApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);

        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
                Log.d("application","x5已经载入");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                //BaseActivity.showToast(getApplicationContext(),"加载内核是否成功:"+b,"long");
                Log.d("application","x5:"+b);
            }
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);


    }



}
