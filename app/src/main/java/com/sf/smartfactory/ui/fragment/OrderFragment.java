package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.IndexViewPagerAdapter;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.Order;
import com.sf.smartfactory.network.response.OrderListResponse;
import com.sf.smartfactory.utils.OrderUtils;
import com.sf.smartfactory.view.ListItemDecoration;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/3/27 18:01
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 订单页面
 */
public class OrderFragment extends BaseFragment {

    private View rootView;
    @BindView(R.id.vp)
    ViewPager mVP;
    @BindView(R.id.nts)
    NavigationTabStrip mNTS;

    private List<Fragment> fragments;
    private IndexViewPagerAdapter mAdapter;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static OrderFragment newInstance(Bundle params){
        OrderFragment instance = new OrderFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_order,null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }
    private void initData(){
        fragments = new ArrayList<>();
        initFragment();
        mAdapter = new IndexViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        mVP.setAdapter(mAdapter);
        mNTS.setViewPager(mVP,1);
    }

    private void initFragment(){
        fragments.add(OrderListFragment.newInstance(OrderUtils.HOLD_UP));
        fragments.add(OrderListFragment.newInstance(null));
        fragments.add(OrderListFragment.newInstance(OrderUtils.ING));
        fragments.add(OrderListFragment.newInstance(OrderUtils.OVER));
    }


}
