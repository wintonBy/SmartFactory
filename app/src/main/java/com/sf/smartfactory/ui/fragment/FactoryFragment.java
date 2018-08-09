package com.sf.smartfactory.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.DeviceClockListAdapter;
import com.sf.smartfactory.adapter.IndexViewPagerAdapter;
import com.sf.smartfactory.adapter.MachineProcessAdapter;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.DeviceClock;
import com.sf.smartfactory.network.bean.MachineProcess;
import com.sf.smartfactory.network.response.DeviceClockResponse;
import com.sf.smartfactory.network.response.MachineProcessListResponse;
import com.sf.smartfactory.utils.DrawableUtils;
import com.sf.smartfactory.view.DiyScrollViewPager;
import com.sf.smartfactory.view.ListItemDecoration;
import com.sf.smartfactory.view.stateview.StateView;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/3/27 17:55
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 加工信息页
 */
public class FactoryFragment extends BaseFragment{

    private View rootView;

    @BindView(R.id.dvp)
    DiyScrollViewPager mVP;
    @BindView(R.id.tv_number)
    TextView mTVNumber;
    @BindView(R.id.tv_number_daily)
    TextView mTVDaily;
    @BindView(R.id.tv_clock)
    TextView mTVClock;



    private View checkView;
    Drawable leftTabCheck = null;

    Drawable rightTabCheck = null;
    Drawable centerTabCheck = null;

    private List<Fragment> fragments;
    private IndexViewPagerAdapter mAdapter;


    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static FactoryFragment newInstance(Bundle params){
        FactoryFragment instance = new FactoryFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_machine,null);
        ButterKnife.bind(this,rootView);
        initView();
        initListener();
        initData();
        return rootView;
    }

    private void initView(){
        leftTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.machine_tab_left,android.R.color.white);
        rightTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.machine_tab_right,android.R.color.white);
        centerTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.machine_tab_center,android.R.color.white);
        mVP.setCanScroll(false);
    }

    private void initData(){
        fragments = new ArrayList<>();
        fragments.add(ProcessAllFragment.newInstance(null));
        fragments.add(ProcessDailyFragment.newInstance(null));
        fragments.add(ClockListFragment.newInstance(null));
        mAdapter = new IndexViewPagerAdapter(getChildFragmentManager(),fragments);
        mVP.setAdapter(mAdapter);
        checkNumberList();
    }
    private void initListener(){

        mTVNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkView != v){
                    checkNumberList();
                }
            }
        });
        mTVClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkView != v){
                    checkClockList();
                }
            }
        });
        mTVDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkView != v){
                    checkNumberDailyList();
                }
            }
        });

    }

    /**
     * 选中每日加工数量
     */
    private void checkNumberDailyList() {
        mVP.setCurrentItem(1);
        checkView = mTVDaily;
        mTVNumber.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.machine_tab_center));
        mTVNumber.setTextColor(Color.WHITE);
        mTVDaily.setBackground(centerTabCheck);
        mTVDaily.setTextColor(Color.BLACK);
        mTVClock.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.machine_tab_right));
        mTVClock.setTextColor(Color.WHITE);
    }

    /**
     * 点击加工数量
     */
    private void checkNumberList(){
        mVP.setCurrentItem(0);
        checkView = mTVNumber;
        mTVNumber.setBackground(leftTabCheck);
        mTVNumber.setTextColor(Color.BLACK);

        mTVDaily.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.machine_tab_center));
        mTVDaily.setTextColor(Color.WHITE);
        mTVClock.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.machine_tab_right));
        mTVClock.setTextColor(Color.WHITE);
    }

    /**
     * 点击加工节拍
     */
    private void checkClockList(){
        mVP.setCurrentItem(2);
        checkView = mTVClock;
        mTVNumber.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.machine_tab_left));
        mTVNumber.setTextColor(Color.WHITE);
        mTVDaily.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.machine_tab_center));
        mTVDaily.setTextColor(Color.WHITE);
        mTVClock.setBackground(rightTabCheck);
        mTVClock.setTextColor(Color.BLACK);
    }


}
