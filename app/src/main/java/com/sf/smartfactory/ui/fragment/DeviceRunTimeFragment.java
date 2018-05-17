package com.sf.smartfactory.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sf.smartfactory.R;
import com.sf.smartfactory.event.DeviceTimeEvent;
import com.sf.smartfactory.network.bean.RunTimeSummary;
import com.sf.smartfactory.network.response.TimeResponse;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/4/20 10:46
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 设备运行时序图
 */
public class DeviceRunTimeFragment extends BaseFragment {

    @BindView(R.id.pie_device_run)
    PieChart mPCRun;

    private Bundle startParams;
    private String deviceId;

    private List<PieEntry> deviceSummery;
    private List<Integer> mChartColors;
    private PieDataSet pieDataSet;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static DeviceRunTimeFragment newInstance(Bundle params){
        DeviceRunTimeFragment instance = new DeviceRunTimeFragment();
        if(params == null){
            throw new IllegalArgumentException("params must be not null");
        }
        instance.setArguments(params);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_device_run,null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }

    private void initData(){
        startParams = getArguments();
        deviceId = startParams.getString("deviceId");
        initPieSummary();
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
     * 对饼图进行初始化
     */
    private void initPieSummary(){
        deviceSummery = new ArrayList<>();
        mChartColors = new ArrayList<>();
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.working_color));
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.editing_color));
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.idle_color));
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.pause_color));
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.collect_error_color));
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.offline_color));
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.emergency_color));
        mChartColors.add(ContextCompat.getColor(getActivity(),R.color.overhaul_color));

        pieDataSet = new PieDataSet(deviceSummery,"");
        pieDataSet.setColors(mChartColors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);
        mPCRun.setData(pieData);
        initLegend();
        initDescription();

        mPCRun.setDrawCenterText(false);
        mPCRun.setUsePercentValues(true);
        mPCRun.setDrawEntryLabels(false);
        mPCRun.setRotationEnabled(false);
        mPCRun.setTouchEnabled(false);
        mPCRun.setTransparentCircleRadius(0);
        mPCRun.setHoleRadius(45f);
        mPCRun.setNoDataText("暂无数据");
    }

    private void initLegend(){
        Legend legend = mPCRun.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(12);
    }

    private void initDescription(){
        Description description = mPCRun.getDescription();
        description.setText("");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSummaryData(DeviceTimeEvent event){
        if(deviceId != event.getDeviceId()){
            return;
        }
        if(event.isError()){
            showError();
        }
       setRunSummary(event.getRunTimeSummary());
    }
    public void setRunSummary(RunTimeSummary summary) {
        deviceSummery.clear();
        if(ObjectUtils.isEmpty(summary)){
            return;
        }
        deviceSummery.add(new PieEntry(summary.getWorking(),"运行:% 2d%"));
        deviceSummery.add(new PieEntry(summary.getEditing(),"设置:% 2d%"));
        deviceSummery.add(new PieEntry(summary.getIdle(),"空闲:% 2d%"));
        deviceSummery.add(new PieEntry(summary.getPause(),"暂停:% 2d%"));
        deviceSummery.add(new PieEntry(summary.getCollect_err(),"采集异常:% 2d%"));
        deviceSummery.add(new PieEntry(summary.getOffline(),"离线:% 2d%"));
        deviceSummery.add(new PieEntry(summary.getEmergency(),"急停:% 2d%"));
        deviceSummery.add(new PieEntry(summary.getOverhaul(),"检修:% 2d%"));
        mPCRun.notifyDataSetChanged();
        fitLegend(computerPercent(summary));
        mPCRun.invalidate();
    }
    private void fitLegend(List<Double> values){
        Legend legend = mPCRun.getLegend();
        LegendEntry[] entries = legend.getEntries();
        entries[0].label = String.format("运行:% 3.1f%%",values.get(0));
        entries[1].label = String.format("设置:% 3.1f%%",values.get(1));
        entries[2].label = String.format("空闲:% 3.1f%%",values.get(2));
        entries[3].label = String.format("暂停:% 3.1f%%",values.get(3));
        entries[4].label = String.format("采集异常:% 3.1f%%",values.get(4));
        entries[5].label = String.format("离线:% 3.1f%%",values.get(5));
        entries[6].label = String.format("急停:% 3.1f%%",values.get(6));
        entries[7].label = String.format("检修:% 3.1f%%",values.get(7));

        legend.resetCustom();
    }

    /**
     * 计算出入值的百分比
     * @param summary
     * @return
     */
    private List<Double> computerPercent(RunTimeSummary summary){
        double working = summary.getWorking();
        double editing = summary.getEditing();
        double idle = summary.getIdle();
        double pause = summary.getPause();
        double error = summary.getCollect_err();
        double offline = summary.getOffline();
        double emergency = summary.getEmergency();
        double overHaul = summary.getOverhaul();

        double  sum = working + editing + idle + pause + error + offline + emergency + overHaul;
        List<Double> values = new ArrayList<>(8);
        double workingP = sum == 0 ? 0 : (working/sum * 100);
        values.add(workingP);
        double editingP = sum == 0 ? 0 : (editing/sum * 100 );
        values.add(editingP);
        double idleP  = sum == 0 ? 0 : (idle/sum * 100 );
        values.add(idleP);
        double pauseP = sum == 0 ? 0 : (pause/sum * 100);
        values.add(pauseP);
        double errorP = sum == 0 ? 0 : (error/sum * 100);
        values.add(errorP);
        double offlineP = sum == 0 ? 0 : (offline/sum * 100);
        values.add(offlineP);
        double emergencyP = sum == 0 ? 0 : (emergency/sum * 100);
        values.add(emergencyP);
        double overHaulP = sum == 0 ? 0 : (int)(overHaul/sum * 100);
        values.add(overHaulP);
        return values;
    }

    private void showError(){

    }
}
