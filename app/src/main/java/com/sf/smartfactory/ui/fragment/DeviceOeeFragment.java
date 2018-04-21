package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.sf.smartfactory.R;
import com.sf.smartfactory.event.DeviceOeeEvent;
import com.sf.smartfactory.network.bean.OEE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/4/21 14:51
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 设备OEE碎片
 */
public class DeviceOeeFragment extends BaseFragment {

    private Bundle startParams;
    private String deviceId;
    private BarChart mBCOEE;


    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static DeviceOeeFragment newInstance(Bundle params){
        DeviceOeeFragment instance = new DeviceOeeFragment();
        if(params == null){
            throw new IllegalArgumentException("params must be not null");
        }
        instance.setArguments(params);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_device_oee,null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }

    private void initData(){
        startParams = getArguments();
        deviceId = startParams.getString("deviceId");
        initOEEChart();
    }
    private void initOEEChart(){

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOEE(DeviceOeeEvent event){
        if(!event.getDeviceId().equals(deviceId)){
            return;
        }
        if(event.isError()){
            showError();
            return;
        }
        showOEE(event.getOee());
    }

    private void showOEE(OEE oee){

    }
    private void showError(){

    }
}
