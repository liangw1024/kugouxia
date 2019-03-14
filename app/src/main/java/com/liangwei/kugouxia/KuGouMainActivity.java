package com.liangwei.kugouxia;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.tencent.qq.widget.QQDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.liangwei.kugouxia.fragments.QqTaoTuFragment;
import com.liangwei.kugouxia.fragments.ToolsFragment;
import com.liangwei.kugouxia.fragments.HomeFragment;
import com.liangwei.kugouxia.fragments.PagerAdapter;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.utils.QqUtils;

/**
 * 首页act
 * Created by weibao on 2018/3/17.
 */

public class KuGouMainActivity extends BaseActivity {
    @Titles private static final String[] mTitles = {"主页","QQ套图","工具大全"};
    @SeleIcons private static final int[] normalIcons = {R.mipmap.ic_home,R.mipmap.ic_qq_taotu,R.mipmap.ic_tools_normal};
    @NorIcons private static final int[] selectedIcons = {R.mipmap.ic_home_selected, R.mipmap.ic_qq_taotu_selected,R.mipmap.ic_tools_select};
    private List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.activity_kugou_jptabbar) JPTabBar jpTabBar;
    @BindView(R.id.activity_kugou_viewpager) ViewPager viewPager;

    @Override
    public int getContentView() {
        return R.layout.activity_kugou;
    }

    @Override
    public void mOncreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.WHITE);
        }
        checkUpdate(true);
        QQDialog qqDialog = new QQDialog(KuGouMainActivity.this);
        qqDialog.setTitle("请加入软件bug反馈群  防止软件失效");
        qqDialog.setViewLine(QQDialog.setLineColor.BLUE);
        //qqDialog.setNegativeButton("取消",null);
        qqDialog.setPositiveButton("加入反馈群", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QqUtils.joinQQGroupByKey(getApplicationContext(),null);
            }
        });
        qqDialog.show();

        jpTabBar.setTitles(mTitles).setNormalIcons(normalIcons).setSelectedIcons(selectedIcons);
        fragments.add(new HomeFragment());
        fragments.add(new QqTaoTuFragment());
        fragments.add(new ToolsFragment());
//        fragments.add(new MyFragment());
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),fragments));
        jpTabBar.setContainer(viewPager);
        jpTabBar.setTabListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int index) {

            }

            @Override
            public boolean onInterruptSelect(int index) {
                return false;
            }
        });
    }

    @Override
    public void initToolbar() {

    }

    /**
     * 播放logo music
     * @throws IOException
     */
    public void playMuisic() throws IOException {
        AssetManager assetManager = getApplicationContext().getAssets();
        MediaPlayer mediaPlayer = new MediaPlayer();
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd("logo.mp3");
        mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
}
