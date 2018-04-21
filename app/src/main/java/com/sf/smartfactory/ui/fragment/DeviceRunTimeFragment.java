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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sf.smartfactory.R;
import com.sf.smartfactory.event.DeviceTimeEvent;
import com.sf.smartfactory.network.response.TimeResponse;
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
    public void onDestroy() {
        super.onDestroy();
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
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(14);
        mPCRun.setData(pieData);
        Legend legend = mPCRun.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        Description description = new Description();
        description.setText("");
        mPCRun.setDescription(description);
        mPCRun.setDrawCenterText(false);
        mPCRun.setUsePercentValues(true);
        mPCRun.setDrawEntryLabels(false);
        mPCRun.setRotationEnabled(false);
        mPCRun.setHoleRadius(0f);
        mPCRun.setNoDataText("暂无数据");
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
    public void setRunSummary(TimeResponse.Summary summary) {
        deviceSummery.clear();
        if(ObjectUtils.isEmpty(summary)){
            return;
        }
        deviceSummery.add(new PieEntry(summary.getWorking(),"运行"));
        deviceSummery.add(new PieEntry(summary.getEditing(),"设置"));
        deviceSummery.add(new PieEntry(summary.getIdle(),"空闲"));
        deviceSummery.add(new PieEntry(summary.getPause(),"暂停"));
        deviceSummery.add(new PieEntry(summary.getCollect_err(),"采集异常"));
        deviceSummery.add(new PieEntry(summary.getOffline(),"离线"));
        deviceSummery.add(new PieEntry(summary.getEmergency(),"急停"));
        deviceSummery.add(new PieEntry(summary.getOverhaul(),"检修"));
        mPCRun.notifyDataSetChanged();
        mPCRun.invalidate();
    }

    private void showError(){

    }
}
