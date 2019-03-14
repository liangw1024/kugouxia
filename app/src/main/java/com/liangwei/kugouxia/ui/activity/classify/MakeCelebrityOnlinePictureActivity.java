package com.liangwei.kugouxia.ui.activity.classify;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.studio.frame.BitmapUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;


import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MakeCelebrityOnlinePictureActivity extends BaseActivity {
    List<Uri> mSelected;
    public int requestCodeHead = 100;
    public int requestCodeBg = 200;
    public static int QQ_BG_WIDTH = 480;
    public static int QQ_BG_HEIGHT = 800;
    public static final int SEEKBAR_ACTION_HEAD = 0;
    public static final int SEEKBAR_ACTION_TEXT = 1;


    private int seekbarAction = 0;

    private Bitmap bitmapOutput = null;
    private Bitmap bitmapBackground = null;
    private Bitmap bitmapHead = null;
    //防止放大缩小丢像素
    private Bitmap bitmapSourceHead = null;



    private int headX = 0;
    private int headY = 0;
    private int textX = 0;
    private int textY = 0;
    public int textSize = 30;


    @BindView(R.id.activity_make_celebrity_online_picture_iv_preview) ImageView iv_preview;
    @BindView(R.id.activity_make_celebrity_online_radio_head) AppCompatRadioButton radio_head;
    @BindView(R.id.activity_make_celebrity_online_radio_text) AppCompatRadioButton radio_text;
    @BindView(R.id.activity_make_celebrity_online_et) MaterialEditText et_text;

    @BindView(R.id.activity_make_celebrity_online_picture_seekbar_scale) AppCompatSeekBar seekbar_scale;
    @BindView(R.id.activity_make_celebrity_online_picture_seekbar_x) AppCompatSeekBar seekbar_x;
    @BindView(R.id.activity_make_celebrity_online_picture_seekbar_y) AppCompatSeekBar seekbar_y;
    @BindView(R.id.activity_make_celebrity_online_picture_seekbar_textsize) AppCompatSeekBar seekbar_textsize;

    @OnClick(R.id.activity_make_celebrity_online_btn_setBg) public void bg(){
        Matisse.from(MakeCelebrityOnlinePictureActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(300)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.5f)
                .imageEngine(new GlideEngine())
                .forResult(requestCodeBg);

    }
    @OnClick(R.id.activity_make_celebrity_online_btn_setHead) public void head(){
        Matisse.from(MakeCelebrityOnlinePictureActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(300)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.5f)
                .imageEngine(new GlideEngine())
                .forResult(requestCodeHead);


    }

    @OnClick(R.id.activity_make_celebrity_online_btn_save) public void save(){

        com.liangwei.kugouxia.frame.utils.BitmapUtils.SaveBitmap(((BitmapDrawable)iv_preview.getDrawable()).getBitmap(), getApplicationContext());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_make_celebrity_online_picture;
    }

    @Override
    public void mOncreate() {
        init();
    }

    @Override
    public void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.activity_make_celebrity_online_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void init(){
        bitmapHead = BitmapFactory.decodeResource(getResources(), R.mipmap.qqhead);

        bitmapBackground = Bitmap.createBitmap( QQ_BG_WIDTH, QQ_BG_HEIGHT ,Bitmap.Config.ARGB_8888);
        textX = 50;textY=50;headX=100;headY=100;
        headX = seekbar_x.getProgress();
        headY = seekbar_y.getProgress();
        textSize=40;
        refreshOutput();
        //单选按钮控制seekbar滑动控制的对象
            radio_head.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        seekbarAction = SEEKBAR_ACTION_HEAD;
                    }

                }
            });
            radio_text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        seekbarAction = SEEKBAR_ACTION_TEXT;
                    }

                }
            });

            et_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    refreshOutput();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



            seekbar_scale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(progress!=0){
                        if(bitmapSourceHead==null){
                            bitmapSourceHead = bitmapHead;
                            bitmapHead = BitmapUtils.scaleBitmap(   bitmapSourceHead , progress,progress);
                        }else{
                            bitmapHead = BitmapUtils.scaleBitmap(   bitmapSourceHead , progress,progress);
                        }

                        refreshOutput();
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            //x seekbar
            seekbar_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    switch (seekbarAction){
                        case SEEKBAR_ACTION_HEAD:

                                headX = progress;

                            break;
                        case SEEKBAR_ACTION_TEXT:
                            textX = progress;
                            break;
                    }
                    refreshOutput();

                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            seekbar_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    switch (seekbarAction){
                        case SEEKBAR_ACTION_HEAD:
                            headY = progress;
                            break;
                        case SEEKBAR_ACTION_TEXT:
                            textY = progress;
                            break;
                    }
                    refreshOutput();

                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            seekbar_textsize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize = progress;
                refreshOutput();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeHead && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            try {

                this.bitmapHead = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Matisse.obtainResult(data).get(0));
                bitmapSourceHead = bitmapHead;
                refreshOutput();
            } catch (IOException e) {
                ToastUtils.ShowToast(getApplicationContext(),e. toString());
                e.printStackTrace();
            }
            ToastUtils.ShowToast(getApplicationContext(),mSelected. toString());
            //Log.d("Matisse", "mSelected: " + mSelected);
        }else if (requestCode == requestCodeBg && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);

            //Log.d("Matisse", "mSelected: " + mSelected);
        }

        if (requestCode == requestCodeBg && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            try {

                bitmapBackground = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Matisse.obtainResult(data).get(0));
                refreshOutput();
            } catch (IOException e) {
                ToastUtils.ShowToast(getApplicationContext(),e. toString());
                e.printStackTrace();
            }
            ToastUtils.ShowToast(getApplicationContext(),mSelected. toString());
            //Log.d("Matisse", "mSelected: " + mSelected);
        }else if (requestCode == requestCodeBg && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);

        }
    }


    private void refreshOutput(){
        bitmapOutput = Bitmap.createBitmap(QQ_BG_WIDTH,QQ_BG_HEIGHT,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapOutput);
        canvas.drawBitmap(bitmapBackground,0,0 ,null );
        canvas.drawBitmap(bitmapHead, headX,headY,null );

        iv_preview.setImageBitmap(BitmapUtils.createTextWatermask(bitmapOutput, et_text.getText().toString(),textSize,Color.WHITE,textX,textY));
    }

}
