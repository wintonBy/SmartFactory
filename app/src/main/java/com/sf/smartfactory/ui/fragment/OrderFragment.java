package com.sf.smartfactory.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.IndexViewPagerAdapter;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.Order;
import com.sf.smartfactory.network.response.OrderListResponse;
import com.sf.smartfactory.utils.DrawableUtils;
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
import butterknife.OnClick;

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
    @BindView(R.id.tv_hold_up)
    TextView mTVHoldUp;
    @BindView(R.id.tv_all)
    TextView mTVAll;
    @BindView(R.id.tv_ing)
    TextView mTVIng;
    @BindView(R.id.tv_complete)
    TextView mTVComplete;
    @BindView(R.id.tv_title)
    TextView mTVTitle;
    private View checkView;

    private List<Fragment> fragments;
    private IndexViewPagerAdapter mAdapter;
    private Drawable centerTabCheck;

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
        mTVTitle.setText("订单监控");
        centerTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_tab_center,R.color.colorPrimary);
        fragments = new ArrayList<>();
        initFragment();
        mAdapter = new IndexViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        mVP.setAdapter(mAdapter);
        checkAll();
    }

    private void initFragment(){
        fragments.add(OrderListFragment.newInstance(OrderUtils.HOLD_UP));
        fragments.add(OrderListFragment.newInstance(null));
        fragments.add(OrderListFragment.newInstance(OrderUtils.ING));
        fragments.add(OrderListFragment.newInstance(OrderUtils.OVER));
    }

    @OnClick(R.id.tv_hold_up)
    public void clickHoldUp(){
        if(checkView == mTVHoldUp){
            return;
        }
        checkHoldUp();
    }
    @OnClick(R.id.tv_all)
    public void clickAll(){
        if(checkView == mTVAll){
            return;
        }
        checkAll();
    }
    @OnClick(R.id.tv_ing)
    public void clickIng(){
        if(checkView == mTVIng){
            return;
        }
        checkIng();
    }
    @OnClick(R.id.tv_complete)
    public void clickComplete(){
        if(checkView == mTVComplete){
            return;
        }
        checkComplete();
    }


    /**
     * 选中挂起
     */
    private void checkHoldUp() {
        mVP.setCurrentItem(0);
        checkView = mTVHoldUp;
        mTVHoldUp.setBackground(centerTabCheck);
        mTVHoldUp.setTextColor(Color.WHITE);
        mTVAll.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVAll.setTextColor(Color.BLACK);
        mTVIng.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVIng.setTextColor(Color.BLACK);
        mTVComplete.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVComplete.setTextColor(Color.BLACK);
    }

    /**
     * 选中全部
     */
    private void checkAll(){
        mVP.setCurrentItem(1);
        checkView = mTVAll;
        mTVAll.setBackground(centerTabCheck);
        mTVAll.setTextColor(Color.WHITE);
        mTVHoldUp.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVHoldUp.setTextColor(Color.BLACK);
        mTVIng.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVIng.setTextColor(Color.BLACK);
        mTVComplete.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVComplete.setTextColor(Color.BLACK);
    }

    /**
     * 选中进行中
     */
    private void checkIng(){
        mVP.setCurrentItem(2);
        checkView = mTVIng;
        mTVIng.setBackground(centerTabCheck);
        mTVIng.setTextColor(Color.WHITE);
        mTVHoldUp.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVHoldUp.setTextColor(Color.BLACK);
        mTVAll.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVAll.setTextColor(Color.BLACK);
        mTVComplete.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVComplete.setTextColor(Color.BLACK);
    }

    /**
     * 选中进行中
     */
    private void checkComplete(){
        mVP.setCurrentItem(3);
        checkView = mTVComplete;
        mTVComplete.setBackground(centerTabCheck);
        mTVComplete.setTextColor(Color.WHITE);
        mTVHoldUp.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVHoldUp.setTextColor(Color.BLACK);
        mTVAll.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVAll.setTextColor(Color.BLACK);
        mTVIng.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_center));
        mTVIng.setTextColor(Color.BLACK);
    }

}
