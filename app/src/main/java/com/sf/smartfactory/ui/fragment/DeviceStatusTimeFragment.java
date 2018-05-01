package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.TimeUtils;
import com.jaygoo.widget.RangeSeekBar;
import com.sf.smartfactory.R;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.event.DeviceTimeStatusEvent;
import com.sf.smartfactory.network.bean.Status;
import com.sf.smartfactory.utils.DateUtils;
import com.sf.smartfactory.view.DeviceTimeStateView;

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

    private List<Status> allData;
    private List<Status> showData;
    private Bundle startParams;
    private String deviceId;
    long start = DateUtils.INSTANCE.getTodayStart();
    long end = System.currentTimeMillis();

    float fs = 0;
    float fe = 100;

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
        allData = new ArrayList<>();
        showData = new ArrayList<>();
        setTimePickData();
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
        allData = event.getStatusList();
        filterData();
    }


    private void setTimePickData(){
        timePick.setValue(0.0f,100.0f);
        timePick.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                fs = min;
                fe = max;
                filterData();
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

    }

    /**
     * 过滤数据
     */
    private void filterData(){
        showData.clear();
        if(fs <= 0){
            fs = 0;
        }
        if(fe >= 100){
            fe = 100;
        }
        long s =(long) (start + (end - start) * (fs/100f));
        long e =(long) (end - (end - start) * ((100 - fe)/100f));
        for(int i=0;i<allData.size();i++){
            Status status = allData.get(i);
            long dt = TimeUtils.getMillis(status.getDt(), Constant.SERVER_SDF,0,0);
            long dur = status.getDuration();
            if(dt >=s && dt + dur <= e){
                //两端均在时间范围内的
                showData.add(status);
            }else if(dt < s && dt + dur > s && dt + dur <= e){
                String newDt = TimeUtils.getString(s,Constant.SERVER_SDF,0,0);
                long newDur = dt + dur -s;
                Status newStatus = status.copy(status);
                newStatus.setDt(newDt);
                newStatus.setDuration(newDur);
                showData.add(newStatus);

            }else  if(dt <s && dt+dur >e){
                String newDt = TimeUtils.getString(s,Constant.SERVER_SDF,0,0);
                long newDur = e -s;
                Status newStatus = status.copy(status);
                newStatus.setDt(newDt);
                newStatus.setDuration(newDur);
                showData.add(newStatus);
            }else  if(dt >= s && dt <e && dt +dur >e){
                long newDur = e - dt;
                Status newStatus = status.copy(status);
                newStatus.setDuration(newDur);
                showData.add(newStatus);
            }
        }
        timeStateView.setData(showData);
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
