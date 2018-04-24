package com.sf.smartfactory.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sf.smartfactory.R;
import com.sf.smartfactory.event.DeviceRateEvent;
import com.sf.smartfactory.network.response.TimeResponse;
import com.sf.smartfactory.view.LineChartManager;
import com.wasu.iutils.LogUtils;
import com.wasu.iutils.ObjectUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    private List<Entry> mAxisRate,mFastRate,mFeedRate;
    private List<String> ratesName;
    private List<Integer> lineColors;
    private List<String> mTimes;
    private LineChartManager lineChartManager;

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
        initDeviceRate();
    }
    private void initDeviceRate(){
        ratesName = new ArrayList<>();
        ratesName.add("主轴倍率");
        ratesName.add("快速倍率");
        ratesName.add("供给倍率");
        lineColors = new ArrayList<>();
        mTimes = new ArrayList<>();
        lineColors.add(ContextCompat.getColor(getActivity(),R.color.axis_color));
        lineColors.add(ContextCompat.getColor(getActivity(),R.color.fast_color));
        lineColors.add(ContextCompat.getColor(getActivity(),R.color.feed_color));
        mAxisRate = new ArrayList<>();
        mFastRate = new ArrayList<>();
        mFeedRate = new ArrayList<>();
        lineChartManager = new LineChartManager(mRateLineChart);
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
        if(ObjectUtils.isEmpty(event.getDevices())){
            return;
        }

        setDeviceRate(event.getDevices());
    }

    private void setDeviceRate(List<TimeResponse.Device> devices) {
        mAxisRate.clear();
        mFeedRate.clear();
        mFastRate.clear();
        mTimes.clear();
        int j = 0;
        for(int i =0;i<devices.size();i+=5){
            TimeResponse.Device device = devices.get(i);
            if(device != null && device.getData() != null && device.getData().getParams() != null){
                int axis = device.getData().getParams().getAxis_rate();
                int fast = device.getData().getParams().getFast_rate();
                int feed = device.getData().getParams().getFeed_rate();
                mTimes.add(device.getCreateDt());
                mAxisRate.add(new Entry(j,axis));
                mFastRate.add(new Entry(j,fast));
                mFeedRate.add(new Entry(j,feed));
                j++;
            }
        }
        if(j ==0){
            LogUtils.dTag(TAG,"设备倍率数据为空");
            return;
        }
        lineChartManager.setTimes(mTimes);
        lineChartManager.setXAxis(mTimes.size()-1,0,6);
        lineChartManager.showLineChart(formatLines());
    }

    /**
     * 初始化每条线
     * @return
     */
    private List<ILineDataSet>  formatLines(){
        LineDataSet line1 = new LineDataSet(mAxisRate,ratesName.get(0));
        LineDataSet line2 = new LineDataSet(mFastRate,ratesName.get(1));
        LineDataSet line3 = new LineDataSet(mFeedRate,ratesName.get(2));
        /*axisRate 样式*/
        line1.setColor(lineColors.get(0));
        line1.setDrawValues(false);
        line1.setDrawCircles(false);
        line1.setLineWidth(2f);
        /*fastRate 样式*/
        line2.setColor(lineColors.get(1));
        line2.setDrawCircles(false);
        line2.setDrawValues(false);
        line2.setLineWidth(2f);
        /*feedRate 样式*/
        line3.setColor(lineColors.get(2));
        line3.setDrawCircles(false);
        line3.setDrawValues(false);
        line3.setLineWidth(2f);

        List<ILineDataSet> dataSets = new ArrayList<>(3);
        dataSets.add(line1);
        dataSets.add(line2);
        dataSets.add(line3);
        return dataSets;
    }

    private void showError(){

    }
}
