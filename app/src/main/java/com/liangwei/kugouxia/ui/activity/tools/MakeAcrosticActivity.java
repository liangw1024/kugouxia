package com.liangwei.kugouxia.ui.activity.tools;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.fragments.FragmentAcrostic;
import com.liangwei.kugouxia.fragments.FragmentAcrosticCollection;
import com.liangwei.kugouxia.frame.BaseActivity;

/**
 * 藏头诗制作 acrostic
 */
public class MakeAcrosticActivity extends BaseActivity {
    @BindView(R.id.activity_make_acrostic_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_make_acrostic_tablayout) TabLayout tablayout;
    @BindView(R.id.activity_make_acrostic_viewpager) ViewPager viewPager;
    List<String> tabNames = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_make_acrostic;
    }

    @Override
    public void mOncreate() {
        configTablayout();
        configViewpager();
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
    private void configTablayout(){
        tabNames.add("制作");
        tabNames.add("收藏");

        tablayout.setupWithViewPager(viewPager);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });

    }
    private void configViewpager(){
        fragments.add(new FragmentAcrostic());
        fragments.add(new FragmentAcrosticCollection());
        com.liangwei.kugouxia.fragments.PagerAdapter pagerAdapter = new com.liangwei.kugouxia.fragments.PagerAdapter(getSupportFragmentManager(),fragments,tabNames);
        viewPager.setAdapter(pagerAdapter);
    }

}
