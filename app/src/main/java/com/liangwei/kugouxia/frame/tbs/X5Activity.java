package com.liangwei.kugouxia.frame.tbs;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.tencent.qq.widget.QQToast;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

import butterknife.OnClick;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.kugouxia.frame.loading.WbLoadingDialog;
import com.liangwei.kugouxia.frame.utils.BitmapUtils;

/**
 *  ┏┛ ┻━━━━━┛ ┻┓
 *┃　　　　　　 ┃
 *┃　　　━　　　┃
 *┃　┳┛　  ┗┳　┃
 *┃　　　　　　 ┃
 *┃　　　┻　　　┃
 *┃　　　　　　 ┃
 *┗━┓　　　┏━━━┛
 *┃　　　┃   神兽保佑
 *┃　　　┃   代码无BUG！
 *┃　　　┗━━━━━━━━━┓
 *┃　　　　　　　    ┣┓
 *┃　　　　         ┏┛
 *┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
 *┃ ┫ ┫   ┃ ┫ ┫
 *┗━┻━┛   ┗━┻━┛
 */

public class X5Activity extends BaseActivity {
    //intent 传进来的url
    public String url = null;
    //是否已经显示了 提示dialog
    boolean isShowDialog = false;

    //低版本选取回来的是Uri
    private ValueCallback<Uri> uploadFile;
    //高版本选取回来的是Uri数组
    private ValueCallback<Uri[]> uploadFileAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 9999;

    @BindView(R.id.activity_x5_webview) WebView webView;
    @BindView(R.id.activity_x5_progressbar) ProgressBar progressBar;
    @BindView(R.id.activity_x5_layout_navigation) LinearLayout layout_navigation;
    @OnClick(R.id.activity_x5_iv_navigation_back) public void back(){
        if(webView.canGoBack()){
            webView.goBack();
        }else{

        }
    }
    @OnClick(R.id.activity_x5_iv_navigation_next) public void next(){
        if(webView.canGoForward()){
            webView.goForward();
        }else{

        }
    }
    @OnClick(R.id.activity_x5_iv_navigation_home) public void home(){
        webView.loadUrl("http://www.lwstudio.top");
    }
    //刷新webview
    @OnClick(R.id.activity_x5_iv_navigation_refresh) public void refresh(){
        webView.reload();
    }
    //退出
    @OnClick(R.id.activity_x5_iv_navigation_exit) public void exit(){
        finish();
    }
    @Override
    public int getContentView() {
        return R.layout.activity_x5;
    }

    @Override
    public void mOncreate() {
        ToastUtils.ShowToast(getApplicationContext(),"长按图片即可保存图片" );
        url = getIntent().getExtras().getString("url");
        webView.loadUrl(url);
        initwebview();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void initToolbar() { }


    /**
     *
     */
    private class X5WebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }

        //24 800开始WebView的shouldOverrideUrlLoading返回值是false才会自动重定向，并且无需调用loadUrl
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(Build.VERSION.SDK_INT<24) {
                view.loadUrl(url);
                if(url.startsWith("http")){
                    view.loadUrl(url);
                    //QQ空白资料js
                    if(url.contains("http://activeqq.3g.qq.com")){
                        String js = "javascript:"+"document.getElementByName('nickname').value=''; " +
                                "document.getElementByName('gender').value='-';" +
                                "document.getElementByName('realname').value='';" +
                                "document.getElementByName('birthYear').value='0';" +
                                "document.getElementByName('birthMonth').value='0';" +
                                "document.getElementByName('birthDate').value='0';" +
                                "document.getElementByName('school').value='';" +
                                "document.getElementByName('shengxiao').value='0';" +
                                "document.getElementByName('constellation').value='';" +
                                "document.getElementByName('desc').value='';" +
                                "document.getElementByName('nickname').parentNode.parentNode.click();";
                        webView.loadUrl(js);
                    }
                }else{
                    try {
                        final Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }catch (Exception e){
                        view.loadUrl(url);
                    }

                }
                return true;
            }else{
                view.loadUrl(url);
                if(url.startsWith("http")){
                    //view.loadUrl(url);

                }else{
                    try {
                        final Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }catch (Exception e){
                        view.loadUrl(url);
                    }

                }
                return false;
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);
           // changeStatusColor();

            if(url.contains("http://fanyi.baidu.com/?aldtype=16047&tpltype=singma#zh/en/")){
                Log.i("kgx","播放js注入到百度翻译");
                String js = "javascript:"+" document.getElementById('single-sound').click(); ";
                webView.loadUrl(js);
            }
            //QQ空白资料js
            if(url.contains("http://activeqq.3g.qq.com")){
                QQToast.makeText(getApplicationContext(),"请把资料改为空白 提交即可", QQToast.setBackgroundColors.BLUE).show();
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            if(isShowDialog==false){
                if (url.contains("http://wx.deepba.com/")){
                    String js = "alert(\"长按图片即可保存哟\")";
                    webView.loadUrl("javascript:"+js);
                    isShowDialog=true;
                }
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }
    }

    private void initwebview(){
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new X5WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setAllowFileAccess(true);
// 设置支持缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
// webSettings.setDatabaseEnabled(true);
// 使用localStorage则必须打开
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_DOWN){

                }
                return false;
            }
        });
        webView.setDownloadListener(new WebviewDownloadListener());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if(progress==100){
                   progressBar.setVisibility(View.GONE);
                   //toolbar.setTitle(webView.getTitle());
                    changeStatusColor();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }

            }
            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadFile = valueCallback;
                openImageChooserActivity();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadFile = valueCallback;
                openImageChooserActivity();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadFile = valueCallback;
                openImageChooserActivity();
            }
            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.sdk.ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                uploadFileAboveL = valueCallback;
                openImageChooserActivity();
                return true;
            }
        });

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /*WebView.HitTestResult.UNKNOWN_TYPE    未知类型
                WebView.HitTestResult.PHONE_TYPE    电话类型
                WebView.HitTestResult.EMAIL_TYPE    电子邮件类型
                WebView.HitTestResult.GEO_TYPE    地图类型
                WebView.HitTestResult.SRC_ANCHOR_TYPE    超链接类型
                WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE    带有链接的图片类型
                WebView.HitTestResult.IMAGE_TYPE    单纯的图片类型
                WebView.HitTestResult.EDIT_TEXT_TYPE    选中的文字类型*/
                final WebView.HitTestResult result = webView.getHitTestResult();
                int type = result.getType();
                // 如果是图片类型或者是带有图片链接的类型
                if(type== WebView.HitTestResult.IMAGE_TYPE|| type== WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // 弹出保存图片的对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(X5Activity.this);
                    builder.setTitle("提示");
                    builder.setMessage("保存图片到本地");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //通过getExtra()获取额外信息，图片这里就是图片地址
                            final WbLoadingDialog wbLoadingDialog = new WbLoadingDialog(X5Activity.this,"下载中",false);
                            wbLoadingDialog.show();
                            String imgurl = result.getExtra();
                            Log.i("kgx","ImageUrl:"+ imgurl);
                            BitmapUtils.getBitmapByUrl(X5Activity.this, imgurl, new BitmapUtils.IGetBimap() {
                                @Override
                                public void success(Bitmap bitmap) {
                                    wbLoadingDialog.dismiss();
                                    BitmapUtils.SaveBitmap(bitmap,X5Activity.this);
                                }
                                @Override
                                public void fail() {
                                    wbLoadingDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"下载失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return false;
            }
        });
        }
    //打开选取的方法
    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    //选取回执
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadFile && null == uploadFileAboveL) return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            if (uploadFileAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadFile != null) {
                uploadFile.onReceiveValue(result);
                uploadFile = null;
            }
        }
    }

    //这里intent.getClipData()方法需要在api16以上才能使用这个
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadFileAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadFileAboveL.onReceiveValue(results);
        uploadFileAboveL = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(getIntent().getExtras().getBoolean("isShowMenu")){
            getMenuInflater().inflate(R.menu.menu_x5_webview,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_x5_webview_openUrl:
                Intent intent = new Intent();
//Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.loadUrl("");
        webView.destroy();
    }
    /**
     * 根据10,10的点颜色 set status color
     */
    public void changeStatusColor(){
        Bitmap topBit = BitmapUtils.getBitmapFromView(webView);
        int topColor = topBit.getPixel(30,30);
        screenManager.setStatusBarColor(this,topColor);
        layout_navigation.setBackgroundColor(topColor);


    }
    /**
     * 处理tbs下载
     */
    public class WebviewDownloadListener implements DownloadListener{
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Log.i("kgx", "url="+url);
            Log.i("kgx", "userAgent="+userAgent);
            Log.i("kgx", "contentDisposition="+contentDisposition);
            Log.i("jgx", "mimetype="+mimetype);
            Log.i("kgx", "contentLength="+contentLength);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
