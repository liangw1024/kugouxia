package com.liangwei.kugouxia.fragments;

/**
 * Created by weibao on 2018/3/17.
 */

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    private List<String> titles;
    public PagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }
    public PagerAdapter(FragmentManager fm, List<Fragment> list,List<String> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    /**
     * 得到current fragment
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}