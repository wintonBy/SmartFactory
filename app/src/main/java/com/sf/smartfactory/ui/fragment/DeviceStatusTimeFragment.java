package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaygoo.widget.RangeSeekBar;
import com.sf.smartfactory.R;
import com.sf.smartfactory.event.DeviceTimeStatusEvent;
import com.sf.smartfactory.network.bean.Status;
import com.sf.smartfactory.view.DeviceTimeStateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/4/27 18:05
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 设备时间状态图
 */
public class DeviceStatusTimeFragment extends BaseFragment {

    @BindView(R.id.dtv_device_time)
    DeviceTimeStateView timeStateView;
    @BindView(R.id.sb_time_pick)
    RangeSeekBar timePick;

    private Bundle startParams;
    private String deviceId;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static DeviceStatusTimeFragment newInstance(Bundle params){
        DeviceStatusTimeFragment instance = new DeviceStatusTimeFragment();
        if(params == null){
            throw new IllegalArgumentException("params must be not null");
        }
        instance.setArguments(params);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_time,null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }
    private void initData(){
        startParams = getArguments();
        deviceId = startParams.getString("deviceId");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onData(DeviceTimeStatusEvent event){
        if(!event.getDeviceId().equals(deviceId)){
            return;
        }
        if(event.isError()){
            showError();
            return;
        }
        timeStateView.setData(event.getStatusList());
    }


    private void setTimePickData(List<Status> list){
        if(list == null){
            return;
        }

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

    private void showError(){

    }
}
