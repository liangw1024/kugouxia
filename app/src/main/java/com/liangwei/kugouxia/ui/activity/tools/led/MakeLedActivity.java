package com.liangwei.kugouxia.ui.activity.tools.led;

import android.graphics.Color;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.CustomView.MoveTextView;

public class MakeLedActivity extends BaseActivity {
    public static String makedText;
    public static int textSize,textColor,moveSpeed;
    @BindView(R.id.activity_make_led_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_make_Led_tv_showText)
    MoveTextView tv_showText;
    @BindView(R.id.activity_make_led_et_text)
    MaterialEditText et_text;
    @BindView(R.id.activity_make_Led_seekbar_textSize)
    AppCompatSeekBar seekBar_textSize;
    @BindView(R.id.activity_make_Led_tv_textSize)
    TextView tv_value_textSize;
    @BindView(R.id.activity_make_Led_seekbar_textMoveSpeed)
    AppCompatSeekBar seekBar_moveSpeed;
    @BindView(R.id.activity_make_Led_tv_textMovespeed)
    TextView tv_value_moveSpeed;
    @BindView(R.id.activity_make_Led_btn_setColor)
    Button btn_setColor;
    @BindView(R.id.activity_make_Led_btn_make)
    Button btn_make;

    @OnClick(R.id.activity_make_Led_btn_setColor)
    public void chooseColor() {

//传入的默认color
       ColorPickerDialog colorPickerDialog = ColorPickerDialog.newBuilder().setColor(Color.RED)
                .setDialogTitle(R.string.choose_color)
//设置dialog标题
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
//设置为自定义模式
                .setShowAlphaSlider(true)
//设置有透明度模式，默认没有透明度
                .setDialogId(0)
//设置Id,回调时传回用于判断
                .setAllowPresets(false)
//不显示预知模式
                .create();
//Buider创建
        colorPickerDialog.setColorPickerDialogListener(pickerDialogListener);
//设置回调，用于获取选择的颜色
        colorPickerDialog.show(getFragmentManager(),"color-picker-dialog");

    }
    @OnClick(R.id.activity_make_Led_btn_make) public void makeLed(){
        makedText = et_text.getText().toString();
        textSize = seekBar_textSize.getProgress();
        moveSpeed = seekBar_moveSpeed.getProgress()/10;
        startActivity(MakeLedActivity.this,ShowLedActivity.class,null,0);
    }

    private ColorPickerDialogListener pickerDialogListener = new ColorPickerDialogListener() {
        @Override
        public void onColorSelected(int dialogId, int color) {
            tv_showText.setTextColor(color);
            textColor = color;
        }

        @Override
        public void onDialogDismissed(int dialogId) {

        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_make_led;
    }

    @Override
    public void mOncreate() {
        tv_showText.setContext(this);
        tv_showText.start();
        tv_value_textSize.setText("当前字体大小:"+seekBar_textSize.getProgress());
        tv_value_moveSpeed.setText("当前减速:"+seekBar_moveSpeed.getProgress());

        init();
    }

    @Override
    public void initToolbar() {

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void init(){
        seekBar_textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_value_textSize.setText("当前字体大小:"+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar_moveSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_value_moveSpeed.setText("当前减速:"+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



}
