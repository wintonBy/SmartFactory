package com.sf.smartfactory.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
    private BarDataSet barDataSet;
    private BarData barData;

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
        barEntries = new ArrayList<>();
        mBCOEE.setFitBars(true);
        mBCOEE.setTouchEnabled(false);
        initDescription();
        initXAxis();
        initYAxis();
        initLegend();
        mBCOEE.setNoDataText("暂无数据");
    }
    private void initDescription(){
        Description description = mBCOEE.getDescription();
        description.setText("");
    }

    private void initLegend(){
        Legend legend = mBCOEE.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "得分";
        legendEntry.formColor = ContextCompat.getColor(getActivity(),R.color.oee_color);
        legend.setCustom(new LegendEntry[]{legendEntry});
    }

    /**
     * 初始化Y轴
     */
    private void initYAxis(){
        YAxis leftAxis = mBCOEE.getAxisLeft();
        YAxis rightAxis = mBCOEE.getAxisRight();

        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setGranularity(5f);

        rightAxis.setEnabled(false);
    }

    /**
     * 初始化x轴
     */
    private void initXAxis(){
        XAxis xAxis = mBCOEE.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value == 1f){
                    return "OEE";
                }
                if(value == 2f){
                    return "QE";
                }
                if(value == 3f){
                    return "PE";
                }
                if(value == 4f){
                    return "AE";
                }
                return "";
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
        if(oee != null){
            barEntries.add(new BarEntry(1,oee.getOee()));
            barEntries.add(new BarEntry(2,oee.getQe()));
            barEntries.add(new BarEntry(3,oee.getPe()));
            barEntries.add(new BarEntry(4,oee.getAe()));
        }
        barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColors(ContextCompat.getColor(getActivity(),R.color.oee_color));
        barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        mBCOEE.setData(barData);
        mBCOEE.invalidate();
    }
    private void showError(){

    }
}
