package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.sf.smartfactory.R;
import com.sf.smartfactory.event.DeviceOeeEvent;
import com.sf.smartfactory.network.bean.OEE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

    @BindView(R.id.bc_oee)
    BarChart mBCOEE;


    private Bundle startParams;
    private String deviceId;
    private List<BarEntry> barEntries;

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
        barEntries = new ArrayList<>(4);
        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        BarData barData = new BarData(barDataSet);
        mBCOEE.setData(barData);
        XAxis xAxis = mBCOEE.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value <1){
                    return "OEE";
                }
                if(value <2){
                    return "QE";
                }
                if(value <3){
                    return "PE";
                }
                if(value <4){
                    return "AE";
                }
                return "未知";
            }
        });
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
        barEntries.clear();
        barEntries.add(new BarEntry(0,oee.getOee()));
        barEntries.add(new BarEntry(1,oee.getQe()));
        barEntries.add(new BarEntry(2,oee.getPe()));
        barEntries.add(new BarEntry(3,oee.getAe()));
        mBCOEE.notifyDataSetChanged();
        mBCOEE.invalidate();
    }
    private void showError(){

    }
}
