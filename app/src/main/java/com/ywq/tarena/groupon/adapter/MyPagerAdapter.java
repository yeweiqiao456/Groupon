package com.ywq.tarena.groupon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarena on 2017/6/15.
 */

public class MyPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
