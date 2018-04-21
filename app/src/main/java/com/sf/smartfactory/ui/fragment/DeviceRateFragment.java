package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.sf.smartfactory.R;
import com.sf.smartfactory.event.DeviceRateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/4/21 16:16
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 设备倍率图
 */
public class DeviceRateFragment extends BaseFragment {

    @BindView(R.id.lc_rate)
    LineChart mRateLineChart;

    private Bundle startParams;
    private String deviceId;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static DeviceRateFragment newInstance(Bundle params){
        DeviceRateFragment instance = new DeviceRateFragment();
        if(params == null){
            throw new IllegalArgumentException("params must be not null");
        }
        instance.setArguments(params);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_device_rate,null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }

    private void initData(){
        startParams = getArguments();
        deviceId = startParams.getString("deviceId");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRate(DeviceRateEvent event){
        if(!deviceId.equals(event.getDeviceId())){
            return;
        }
        if(event.isError()){
            showError();
            return;
        }

    }


    private void showError(){

    }
}
