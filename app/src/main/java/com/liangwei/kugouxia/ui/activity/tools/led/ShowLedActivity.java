package com.liangwei.kugouxia.ui.activity.tools.led;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.CustomView.MoveTextView;

public class ShowLedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_led);
        MoveTextView moveTextView = findViewById(R.id.activity_show_led_moveText);
        if(MakeLedActivity.makedText.equals("")){
            moveTextView.setText("很高兴认识你 by-伟宝");
        }else{
            moveTextView.setText(MakeLedActivity.makedText);
        }

        moveTextView.setTextSize(MakeLedActivity.textSize);
        moveTextView.setTextColor(MakeLedActivity.textColor);
        moveTextView.setSpeed(MakeLedActivity.moveSpeed);
        moveTextView.setContext(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        moveTextView.setIsstatus(false);
        moveTextView.start();
    }

}
