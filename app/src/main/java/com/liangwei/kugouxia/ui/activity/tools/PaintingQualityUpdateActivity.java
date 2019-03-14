package com.liangwei.kugouxia.ui.activity.tools;

import android.os.Build;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.kugouxia.frame.utils.FileUtils;
import com.liangwei.kugouxia.frame.utils.QqUtils;

/**
 * 绝地求生画质修改activity
 */
public class PaintingQualityUpdateActivity extends BaseActivity{
    String sourcePath = "/storage/emulated/0/Android/data/com.tencent.tmgp.pubgmhd/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Config/Android/UserCustom.ini";
    @BindView(R.id.activity_painting_quality_update_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_painting_quality_update_img_gua) ImageView img_gua;
    @BindView(R.id.activity_painting_quality_update_spinner) AppCompatSpinner spinner;
    @BindView(R.id.activity_painting_quality_update_btn_update) Button btn_update;
    @BindView(R.id.activity_painting_quality_update_tv_modelInfo) TextView tv_modelInfo;
    @OnClick(R.id.activity_painting_quality_update_btn_update) public void update(){
        /*绝地求生的源画质代码*/
        String sourceCode = FileUtils.readTxt(sourcePath);
        /*从sourceCode里面获取backupCode*/
        String backupCode =  sourceCode.substring(sourceCode.indexOf("[BackUp"),sourceCode.length()-1);
        /*目标代码*/
        String targetCode = null;
        /*获取spinner的值  并给targetCode 赋值*/
       switch (spinner.getSelectedItemPosition()){
           case 0:
               try {
                   targetCode = FileUtils.readTxtFormAssets(getApplicationContext(),"paintingQuality/painting_quality_low_end.txt");
               } catch (IOException e) {
                   e.printStackTrace();
                   ToastUtils.ShowToast(getApplicationContext(),e.toString());
               }
               break;

           case 1:
               try {
                   targetCode = FileUtils.readTxtFormAssets(getApplicationContext(),"paintingQuality/painting_quality_middle_end.txt");
               } catch (IOException e) {
                   e.printStackTrace();
                   ToastUtils.ShowToast(getApplicationContext(),e.toString());
               }
               break;
           case 2:
               try {
                   targetCode = FileUtils.readTxtFormAssets(getApplicationContext(),"paintingQuality/painting_quality_high_end.txt");
               } catch (IOException e) {
                   e.printStackTrace();
                   ToastUtils.ShowToast(getApplicationContext(),e.toString());
               }
               break;
       }
        /*替换代码*/
        try {
            FileUtils.writerTxt(targetCode+"\n"+backupCode,sourcePath);
            ToastUtils.ShowToast(getApplicationContext(),"修改成功 快去体验吧");
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.ShowToast(getApplicationContext(),"修改失败"+e.toString());
        }

    }
    @Override
    public int getContentView() {
        return R.layout.activity_painting_quality_update;
    }

    @Override
    public void mOncreate() {
        String cpuAbi = "CPU架构："+ Build.CPU_ABI+"\t"+Build.CPU_ABI2+"\n";
        String cpuName = "CPU: "+Build.HARDWARE+"\n";
        String phoneModel = "手机型号: "+ Build.MODEL+"\n";//手机型号
        String phoneReleaseVersion = "系统发布版本: Android"+Build.VERSION.RELEASE+"\n";//发布版本
        String vender = "Vender: "+Build.MANUFACTURER+"\n";//厂家
        String advice = "修改前请关闭游戏 \n处理器为骁龙625以下的建议开低端画质 以上的开中高画质或者高端画质\n MTK的处理器建议中端画质 \n 如果是华为手机并且支持GPU Turbo 建议全开极限画质";
        tv_modelInfo.setText(phoneModel+cpuName+cpuAbi+phoneReleaseVersion+vender+"\n"+advice);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                ToastUtils.ShowToast(getApplicationContext(),tv.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        img_gua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QqUtils.joinQGroup(getApplicationContext(),"816483831");
            }
        });
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

}