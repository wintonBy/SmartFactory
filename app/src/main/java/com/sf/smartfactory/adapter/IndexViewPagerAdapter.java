package com.sf.smartfactory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.sf.smartfactory.ui.fragment.BaseFragment;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/3/27 20:42
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 首页适配器
 */
public class IndexViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mSource;

    public IndexViewPagerAdapter(FragmentManager fm, List<Fragment> mSource) {
        super(fm);
        this.mSource = mSource;
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
