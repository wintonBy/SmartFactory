package com.sf.smartfactory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/21 17:05
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 设备详情页所有图表的ViewPager的适配器
 */
public class ChartsAdapter extends FragmentPagerAdapter {

    private List<Fragment> mSource;
    public ChartsAdapter(FragmentManager fm ,List<Fragment> fragments) {
        super(fm);
        mSource = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mSource.get(position);
    }

    @Override
    public int getCount() {
        return mSource == null? 0: mSource.size();
    }
}
