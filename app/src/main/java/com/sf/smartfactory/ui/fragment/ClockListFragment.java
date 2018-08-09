package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.DeviceClockListAdapter;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.DeviceClock;
import com.sf.smartfactory.network.response.DeviceClockResponse;
import com.sf.smartfactory.view.ListItemDecoration;
import com.sf.smartfactory.view.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/8/9 23:57
 * @package: com.sf.smartfactory.ui.fragment
 * @project: Factory
 * @mail:
 * @describe: 一句话描述
 */
public class ClockListFragment extends BaseFragment {
    @BindView(R.id.rv_clock_list)
    RecyclerView mRVClockList;
    private StateView mSVClock;
    private DeviceClockListAdapter mClockAdapter;
    private List<DeviceClock> mClockList;


    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static ClockListFragment newInstance(Bundle params){
        ClockListFragment instance = new ClockListFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.layout_device_clock,container,false);
        ButterKnife.bind(this,rootView);
        mSVClock = StateView.inject(mRVClockList);
        initListener();
        initData();
        return rootView;
    }

    private void initData() {
        mClockList = new ArrayList<>();
        mClockAdapter = new DeviceClockListAdapter(getActivity(),mClockList);
        mRVClockList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVClockList.addItemDecoration(new ListItemDecoration());
        mRVClockList.setAdapter(mClockAdapter);
        loadClockList();
    }

    private void initListener() {
        mSVClock.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                loadClockList();
            }
        });
    }

    private void loadClockList(){
        RetrofitClient.getInstance().deviceClock(new BaseSubscriber<DeviceClockResponse>(){

            @Override
            public void success(DeviceClockResponse deviceClockResponse) {
                if(deviceClockResponse.getData() != null){
                    setClocksList(deviceClockResponse.getData().getClocks());
                    return;
                }
                mSVClock.showRetry();
                ToastUtils.showShort(deviceClockResponse.getMessage());
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }
    /**
     * 设置节拍数据
     * @param list
     */
    private void setClocksList(List<DeviceClock> list){
        mClockList.clear();
        if(list == null){
            mSVClock.showRetry();
            return;
        }
        if(list.isEmpty()){
            mSVClock.showEmpty();
            return;
        }
        mSVClock.showContent();
        mClockList.addAll(list);
        mClockAdapter.notifyDataSetChanged();
    }
}
