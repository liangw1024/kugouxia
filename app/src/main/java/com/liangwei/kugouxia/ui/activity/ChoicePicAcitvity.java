package com.liangwei.kugouxia.ui.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.fragments.FragmentChoice;
import com.liangwei.kugouxia.fragments.FragmentPic;
import com.liangwei.kugouxia.fragments.PagerAdapter;
import com.liangwei.kugouxia.frame.BaseActivity;

public class ChoicePicAcitvity extends BaseActivity {
    @BindView(R.id.activity_choice_tablayout) TabLayout tabLayout;
    @BindView(R.id.activity_choice_viewPager) ViewPager viewPager;
    int downX = 0;
    int downY = 0;
    boolean viewPagerScroll;
    int upX = 0;
    int upY = 0;
    List<Fragment> fragments = new ArrayList<>();

    FragmentChoice fragmentChoice = new FragmentChoice();
    FragmentPic fragmentPic = new FragmentPic();
    @Override
    public int getContentView() {
        return R.layout.activity_choice_pic;
    }

    @Override
    public void mOncreate() {
        tabLayout.addTab(tabLayout.newTab().setText("精选图片"));
        tabLayout.addTab(tabLayout.newTab().setText("清新女头"));
        tabLayout.addTab(tabLayout.newTab().setText("清新男头"));
        tabLayout.addTab(tabLayout.newTab().setText("真人情头"));
        tabLayout.addTab(tabLayout.newTab().setText("动漫头像"));
        tabLayout.addTab(tabLayout.newTab().setText("动漫情头"));
        tabLayout.addTab(tabLayout.newTab().setText("另类情头"));
        tabLayout.addTab(tabLayout.newTab().setText("欧美头像"));
        tabLayout.addTab(tabLayout.newTab().setText("闺蜜头像"));
        tabLayout.addTab(tabLayout.newTab().setText("萌娃头像"));
        tabLayout.addTab(tabLayout.newTab().setText("萌宠头像"));
        tabLayout.addTab(tabLayout.newTab().setText("基友头像"));
        tabLayout.addTab(tabLayout.newTab().setText("古风头像"));
        tabLayout.addTab(tabLayout.newTab().setText("搞怪有趣"));
        tabLayout.addTab(tabLayout.newTab().setText("背景图"));

        fragments.add(fragmentChoice);
        fragments.add(fragmentPic);
        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager,fragments);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(tabLayout.getSelectedTabPosition()>1){

                }else{
                    switch (position){
                        case 0://精选头像
                            tabLayout.getTabAt(0).select();
                            break;
                        case 1://清新女头
                            viewPager.setCurrentItem(1);
                            tabLayout.getTabAt(1).select();
                            fragmentPic.setType(15);
                            break;
                        default:
                            break;
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("kgx",tab.getPosition()+"") ;
                switch(tab.getPosition()){
                    case 0://精选图片
                        viewPager.setCurrentItem(0);
                        break;

                    case 1://清新女头
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(15);
                        break;
                    case 2://清新男头
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(14);
                        break;
                    case 3://真人情头
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(12);
                        break;
                    case 4://动漫avatar
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(16);
                        break;
                    case 5://动漫情头
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(11);
                        break;
                    case 6://另类情头
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(21);
                        break;
                    case 7://欧美avatar
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(25);
                        break;
                    case 8://闺蜜avatar
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(13);
                        break;
                    case 9://萌娃avatar
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(17);
                        break;
                    case 10://萌宠avatar
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(27);
                        break;
                    case 11://基友avatar
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(20);
                        break;
                    case 12://古风
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(22);
                        break;
                    case 13://搞怪
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(27);
                        break;
                    case 14://背景
                        viewPager.setCurrentItem(1);
                        fragmentPic.setType(26);
                        break;
                        default:

                            break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getX();
                        downY = (int) event.getY();
                        Log.i("kgx","downX:"+downX+"\t"+"downY:"+downY);
                        break;
                    case MotionEvent.ACTION_UP:
                        upX = (int) event.getX();
                        upY = (int) event.getY();
                        if(upX-downX>150) {
                                tabLayout.getTabAt(0).select();
                                viewPager.setCurrentItem(0);
//                            if(tabLayout.getSelectedTabPosition()==0){
//                                tabLayout.getTabAt(1).select();
//                                viewPager.setCurrentItem(1);
//                            }
                        }
                        if(downX -upX>200) {
                        }
             /*           //向右滑动
                        if(upX-downX>250){
                            if(tabLayout.getSelectedTabPosition()!=0){//当前tab不在第一个
                                if (tabLayout.getSelectedTabPosition()==1){
                                    tabLayout.getTabAt(tabLayout.getSelectedTabPosition()-1).select();
                                    viewPager.setCurrentItem(0);
                                }
                                    tabLayout.getTabAt(tabLayout.getSelectedTabPosition()-1).select();
                                    viewPager.setCurrentItem(1);
                            }

                        }
                        //向左滑动
                        if(downX -upX>250){
                            if(tabLayout.getSelectedTabPosition()!=0) {//当前tab不在第一个
                                if(tabLayout.getSelectedTabPosition()!=tabLayout.getTabCount()-1)
                                    if (tabLayout.getSelectedTabPosition()!=1){
                                        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()+1).select();
                                        viewPager.setCurrentItem(1);
                                    }

                            }


                        }
                        Log.i("kgx","d=upX:"+upX+"\t"+"upY:"+upY);*/
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void initToolbar() {

    }

}
