package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.sf.smartfactory.R;
import com.sf.smartfactory.contract.DeviceDetailContract;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.bean.DeviceStatus;
import com.sf.smartfactory.network.bean.LastStatus;
import com.sf.smartfactory.network.bean.OEE;
import com.sf.smartfactory.network.response.TimeResponse;
import com.sf.smartfactory.presenter.DeviceDetailPresenter;
import com.sf.smartfactory.utils.DateUtils;
import com.sf.smartfactory.utils.DeviceUtils;
import com.sf.smartfactory.view.LineChartManager;
import com.wasu.iutils.LogUtils;
import com.wasu.iutils.ObjectUtils;
import com.wasu.iutils.SnackbarUtils;
import com.wasu.iutils.StringUtils;
import com.wasu.iutils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/4/7 23:30
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class DeviceDetailActivity extends BaseActivity<DeviceDetailPresenter> implements DeviceDetailContract.View {

    @BindView(R.id.tv_device_type)
    TextView mTVDeviceType;
    @BindView(R.id.tv_device_no)
    TextView mTVDeviceNo;
    @BindView(R.id.tv_device_status)
    TextView mTVStatus;
    @BindView(R.id.iv_device)
    ImageView mIVDevice;
    @BindView(R.id.oee)
    ArcProgress mAPOEE;
    @BindView(R.id.ae)
    ArcProgress mAPAE;
    @BindView(R.id.pe)
    ArcProgress mAPPE;
    @BindView(R.id.qe)
    ArcProgress mAPQE;
    @BindView(R.id.tv_title)
    TextView mTVTitle;
    @BindView(R.id.pie)
    PieChart mPCSummary;
    @BindView(R.id.line_chart)
    LineChart mLineChart;

    private String deviceId;
    private long start;
    private long end;
    private Bundle startParams;
    private List<PieEntry> deviceSummery;
    private List<Integer> mChartColors;
    private PieDataSet pieDataSet;
    private List<Entry> mAxisRate,mFastRate,mFeedRate;
    private List<String> ratesName;
    private List<Integer> lineColors;
    private LineChartManager lineChartManager;
    private List<String> mTimes;

    /**
     * 进入详情页的方法
     * @param context
     * @param params
     */
    public static void start(Context context, Bundle params) {
        if (context == null) {
            throw new IllegalArgumentException("context should not null");
        }
        Intent intent = new Intent(context, DeviceDetailActivity.class);
        if (params != null) {
            intent.putExtras(params);
        }
        context.startActivity(intent);
    }
    @OnClick(R.id.iv_back)
    public void clickBack(View view){
        this.finish();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_device_detail);
        ButterKnife.bind(this);
        findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        mTVTitle.setText("设备详情");
        String strStart = TimeUtils.millis2String(System.currentTimeMillis(),new SimpleDateFormat("yyyy-MM-dd"));
        start = TimeUtils.getMillis(strStart,new SimpleDateFormat("yyyy-MM-dd"),0,0);
        initPieSummary();
        initDeviceRate();
        startParams = getIntent().getExtras();
        deviceId = startParams.getString("deviceId");
        loadData();
    }
    private void initPieSummary(){
        deviceSummery = new ArrayList<>();
        mChartColors = new ArrayList<>();
        mChartColors.add(ContextCompat.getColor(this,R.color.working_color));
        mChartColors.add(ContextCompat.getColor(this,R.color.editing_color));
        mChartColors.add(ContextCompat.getColor(this,R.color.idle_color));
        mChartColors.add(ContextCompat.getColor(this,R.color.pause_color));
        mChartColors.add(ContextCompat.getColor(this,R.color.collect_error_color));
        mChartColors.add(ContextCompat.getColor(this,R.color.offline_color));
        mChartColors.add(ContextCompat.getColor(this,R.color.emergency_color));
        mChartColors.add(ContextCompat.getColor(this,R.color.overhaul_color));

        pieDataSet = new PieDataSet(deviceSummery,"");
        pieDataSet.setColors(mChartColors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(14);
        mPCSummary.setData(pieData);
        Legend legend = mPCSummary.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        Description description = new Description();
        description.setText("");
        mPCSummary.setDescription(description);
        mPCSummary.setDrawCenterText(false);
        mPCSummary.setUsePercentValues(true);
        mPCSummary.setDrawEntryLabels(false);
    }

    private void initDeviceRate(){
        ratesName = new ArrayList<>();
        ratesName.add("主轴倍率");
        ratesName.add("快速倍率");
        ratesName.add("供给倍率");
        lineColors = new ArrayList<>();
        mTimes = new ArrayList<>();
        lineColors.add(Color.parseColor("#C23531"));
        lineColors.add(Color.parseColor("#2F4554"));
        lineColors.add(Color.parseColor("#61A0A8"));
        mAxisRate = new ArrayList<>();
        mFastRate = new ArrayList<>();
        mFeedRate = new ArrayList<>();
        lineChartManager = new LineChartManager(mLineChart);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void loadData(){
        end = System.currentTimeMillis();
        mPresenter.loadLastStatus(deviceId);
        mPresenter.loadOEE(deviceId,start,end);
        mPresenter.loadTimes(deviceId,start,end);
    }

    @Override
    protected DeviceDetailPresenter loadPresenter() {
        return new DeviceDetailPresenter();
    }

    @Override
    public void setDeviceInfo(LastStatus status) {
        String deviceId = status.getRecord().getDevice().getId();
        mTVDeviceNo.setText(String.format(getResources().getString(R.string.device_no_f),deviceId));
        String deviceType = status.getRecord().getDevice().getType();
        mTVDeviceType.setText(String.format(getResources().getString(R.string.type_f),deviceType.toUpperCase()));
        //设置设备状态
        long duration = status.getRecord().getDuration();
        String strDuration = TimeUtils.getFitTimeSpan(duration,0,4);
        String strStatus = DeviceUtils.INSTANCE.getStatusArrName(status.getRecord().getStatus());
        mTVStatus.setText(String.format(getResources().getString(R.string.device_status_desc_f),strStatus,strDuration));
        String imageUrl = DeviceUtils.INSTANCE.getImageByType(deviceType);
        Glide.with(this).load(imageUrl).into(mIVDevice);
    }

    @Override
    public void setOEEInfo(OEE oeeInfo) {
        if(ObjectUtils.isEmpty(oeeInfo)){
            return;
        }
        mAPOEE.setProgress(oeeInfo.getOee());
        mAPAE.setProgress(oeeInfo.getAe());
        mAPPE.setProgress(oeeInfo.getPe());
        mAPQE.setProgress(oeeInfo.getQe());
    }

    @Override
    public void showError(String msg) {
        if(StringUtils.isTrimEmpty(msg)){
            return;
        }
        SnackbarUtils.with(getWindow().getDecorView()).setMessage(msg).showError();
    }

    @Override
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
        mPCSummary.notifyDataSetChanged();
        mPCSummary.invalidate();
    }

    @Override
    public void setDeviceRate(List<TimeResponse.Device> devices) {
        mAxisRate.clear();
        mFeedRate.clear();
        mFastRate.clear();
        mTimes.clear();
        int j = 0;
        for(int i =0;i<devices.size();i+=20){
            TimeResponse.Device device = devices.get(i);
            if(device != null && device.getData() != null && device.getData().getParams() != null){
                int axis = device.getData().getParams().getAxis_rate();
                int fast = device.getData().getParams().getFast_rate();
                int feed = device.getData().getParams().getFeed_rate();
                LogUtils.dTag("LineChart","时间转换："+device.getCreateDt());
                mTimes.add(device.getCreateDt());
                mAxisRate.add(new Entry(j,axis));
                mFastRate.add(new Entry(j,fast));
                mFeedRate.add(new Entry(j,feed));
                j++;
            }
        }
        LineDataSet line1 = new LineDataSet(mAxisRate,ratesName.get(0));
        LineDataSet line2 = new LineDataSet(mFastRate,ratesName.get(1));
        LineDataSet line3 = new LineDataSet(mFeedRate,ratesName.get(2));
        line1.setColor(lineColors.get(0));
        line2.setColor(lineColors.get(1));
        line3.setColor(lineColors.get(2));
        line1.setCircleRadius(1f);
        line2.setCircleRadius(1f);
        line3.setCircleRadius(2f);
        List<ILineDataSet> dataSets = new ArrayList<>(3);
        dataSets.add(line1);
        dataSets.add(line2);
        dataSets.add(line3);
        lineChartManager.setTimes(mTimes);
        lineChartManager.setXAxis(mTimes.size()-1,0,6);
        lineChartManager.showLineChart(dataSets);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(UpdateDataEvent event){
        loadData();
    }
}
