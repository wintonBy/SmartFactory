package com.sf.smartfactory.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.sf.smartfactory.adapter.MachineProcessAdapter;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.DeviceClock;
import com.sf.smartfactory.network.bean.MachineProcess;
import com.sf.smartfactory.network.response.DeviceClockResponse;
import com.sf.smartfactory.network.response.MachineProcessListResponse;
import com.sf.smartfactory.utils.DrawableUtils;
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
    @BindView(R.id.rv)
    RecyclerView mRVOrderNumList;
    @BindView(R.id.tv_refresh_time)
    TextView mTVTime;
    @BindView(R.id.tv_today)
    TextView mTVToday;
    @BindView(R.id.lay_order_number_list)
    View orderLayout;
    @BindView(R.id.lay_device_clock_list)
    View clockLayout;
    @BindView(R.id.rv_clock_list)
    RecyclerView mRVClockList;
    @BindView(R.id.tv_number)
    TextView mTVNumber;
    @BindView(R.id.tv_clock)
    TextView mTVClock;

    private StateView mSVOrderNumber;
    private StateView mSVClock;
    private View checkView;
    Drawable leftTabCheck = null;
    Drawable rightTabCheck = null;

    private List<MachineProcess> mProcessList;
    private MachineProcessAdapter mProcessAdapter;
    private DeviceClockListAdapter mClockAdapter;
    private List<DeviceClock> mClockList;

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
        mSVOrderNumber = StateView.inject(mRVOrderNumList);
        mSVClock = StateView.inject(mRVClockList);
        initView();
        initListener();
        initData();
        return rootView;
    }

    private void initView(){
        leftTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_tab_left,R.color.colorPrimary);
        rightTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_tab_right,R.color.colorPrimary);
    }

    private void initData(){
        setTvToday();
        mProcessList = new ArrayList<>();
        mProcessAdapter = new MachineProcessAdapter(getActivity(),mProcessList);
        mRVOrderNumList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVOrderNumList.setAdapter(mProcessAdapter);
        mClockList = new ArrayList<>();
        mClockAdapter = new DeviceClockListAdapter(getActivity(),mClockList);
        mRVClockList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVClockList.setAdapter(mClockAdapter);
        loadList();
    }
    private void initListener(){
       mSVOrderNumber.setOnRetryClickListener(new StateView.OnRetryClickListener() {

           @Override
           public void onRetryClick() {
               loadProcessList();
           }
       });
       mSVClock.setOnRetryClickListener(new StateView.OnRetryClickListener() {
           @Override
           public void onRetryClick() {
               loadClockList();
           }
       });

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
        checkNumberList();
    }

    /**
     * 获取加工信息
     */
    private void loadList(){
        loadProcessList();
        loadClockList();
    }

    /**
     * 加载加工信息
     */
    private void loadProcessList(){
        RetrofitClient.getInstance().machineProcessList(new BaseSubscriber<MachineProcessListResponse>(){
            @Override
            public void onNext(MachineProcessListResponse machineProcessListResponse) {
                super.onNext(machineProcessListResponse);
                if( !ObjectUtils.isEmpty(machineProcessListResponse)
                        && machineProcessListResponse.isSuccess()
                        && !ObjectUtils.isEmpty(machineProcessListResponse.getData())){
                    setProcessList(machineProcessListResponse.getData().getList());
                    return;
                }
                ToastUtils.showLong("数据异常");
                mSVOrderNumber.showRetry();
                return;

            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    private void loadClockList(){
        RetrofitClient.getInstance().deviceClock(new BaseSubscriber<DeviceClockResponse>(){
            @Override
            public void onNext(DeviceClockResponse deviceClockResponse) {
                super.onNext(deviceClockResponse);
                if(deviceClockResponse.isSuccess() && deviceClockResponse.getData() != null){
                    setClocksList(deviceClockResponse.getData().getClocks());
                    return;
                }
                mSVClock.showRetry();
                ToastUtils.showShort(deviceClockResponse.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    /**
     * 设置数据的进度
     * @param list
     */
    private void setProcessList(List<MachineProcess> list){
        mProcessList.clear();
        if(list == null){
            mSVOrderNumber.showRetry();
            return;
        }
        if(list.isEmpty()){
            mSVOrderNumber.showEmpty();
            return;
        }
        mProcessList.addAll(list);
        mProcessAdapter.notifyDataSetChanged();
    }

    /**
     * 设置节拍数据
     * @param list
     */
    private void setClocksList(List<DeviceClock> list){
        mClockList.clear();
        if(list != null){
            mClockList.addAll(list);
        }
        mClockAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UpdateDataEvent event){
        loadList();
        setRefreshTime();
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 展示上次刷新的页面时间
     */
    private void setRefreshTime(){
        String time = TimeUtils.getString(System.currentTimeMillis(),0,0);
        mTVTime.setText(String.format(getString(R.string.last_refresh_f),time));
    }

    /**
     *
     */
    private void setTvToday(){
        String week = TimeUtils.getChineseWeek(System.currentTimeMillis());
        String date = TimeUtils.getNowString(new SimpleDateFormat("MM月dd日"));
        mTVToday.setText(date+"·"+week);
    }

    /**
     * 点击加工数量
     */
    private void checkNumberList(){
        clockLayout.setVisibility(View.INVISIBLE);
        orderLayout.setVisibility(View.VISIBLE);
        checkView = mTVNumber;
        mTVNumber.setBackground(leftTabCheck);
        mTVNumber.setTextColor(Color.WHITE);
        mTVClock.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_right));
        mTVClock.setTextColor(Color.BLACK);
    }

    /**
     * 点击加工节拍
     */
    private void checkClockList(){
        clockLayout.setVisibility(View.VISIBLE);
        orderLayout.setVisibility(View.INVISIBLE);
        checkView = mTVClock;
        mTVClock.setBackground(rightTabCheck);
        mTVClock.setTextColor(Color.WHITE);
        mTVNumber.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_left));
        mTVNumber.setTextColor(Color.BLACK);
    }
}
