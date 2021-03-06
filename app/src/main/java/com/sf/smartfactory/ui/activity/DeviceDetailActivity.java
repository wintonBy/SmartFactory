package com.sf.smartfactory.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.ChartsAdapter;
import com.sf.smartfactory.contract.DeviceDetailContract;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.bean.LastStatus;
import com.sf.smartfactory.network.bean.Quantity;
import com.sf.smartfactory.presenter.DeviceDetailPresenter;
import com.sf.smartfactory.ui.fragment.DeviceOeeFragment;
import com.sf.smartfactory.ui.fragment.DeviceRateFragment;
import com.sf.smartfactory.ui.fragment.DeviceRunTimeFragment;
import com.sf.smartfactory.ui.fragment.DeviceStatusTimeFragment;
import com.sf.smartfactory.utils.DeviceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * @author: winton
 * @time: 2018/4/7 23:30
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 设备详情页
 */
public class DeviceDetailActivity extends BaseActivity<DeviceDetailPresenter> implements DeviceDetailContract.View {

    @BindView(R.id.tv_device_type)
    TextView mTVDeviceType;
    @BindView(R.id.tv_device_no)
    TextView mTVDeviceNo;
    @BindView(R.id.tv_device_status)
    TextView mTVStatusDec;
    @BindView(R.id.tv_device_status_value)
    TextView mTVStatus;
    @BindView(R.id.tv_device_process_num)
    TextView mTVProcessNum;
    @BindView(R.id.tv_status_time)
    TextView mTVStatusTime;
    @BindView(R.id.iv_device)
    ImageView mIVDevice;
    @BindView(R.id.tv_title)
    TextView mTVTitle;
    @BindView(R.id.vp_charts)
    ViewPager mVPCharts;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;

    private String deviceId;
    private Bundle startParams;
    private ChartsAdapter mAdapter;
    private List<Fragment> mChartsFrag;

    private static final int UPDATE_FREQUENCY = 10 * 1000;

    private MyHandler mUIHandler;

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
        mUIHandler = new MyHandler(this);
        startParams = getIntent().getExtras();
        deviceId = startParams.getString("deviceId");
        mTVTitle.setText("设备详情");
        mChartsFrag = new ArrayList<>();
        initChartsFrag();
        mAdapter = new ChartsAdapter(getSupportFragmentManager(),mChartsFrag);
        mVPCharts.setAdapter(mAdapter);
        mVPCharts.setOffscreenPageLimit(4);
        mIndicator.setViewPager(mVPCharts);
        loadData();
    }

    /**
     * 初始化图表的碎片
     */
    private void initChartsFrag(){
        Bundle commonParams = new Bundle();
        commonParams.putString("deviceId",deviceId);
        mChartsFrag.add(DeviceStatusTimeFragment.newInstance(commonParams));
        mChartsFrag.add(DeviceRunTimeFragment.newInstance(commonParams));
        mChartsFrag.add(DeviceRateFragment.newInstance(commonParams));
        mChartsFrag.add(DeviceOeeFragment.newInstance(commonParams));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUIHandler.sendEmptyMessageDelayed(MyHandler.UPDATE,UPDATE_FREQUENCY);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mUIHandler.removeMessages(MyHandler.UPDATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUIHandler != null){
            mUIHandler = null;
        }
    }

    private void loadData(){
        mPresenter.loadLastStatus(deviceId);
        mPresenter.loadOEE(deviceId);
        mPresenter.loadTime(deviceId);

//        mPresenter.loadRates(deviceId);
//        mPresenter.loadTimeSummary(deviceId);
//        mPresenter.loadTimeStatus(deviceId);
    }

    @Override
    protected DeviceDetailPresenter loadPresenter() {
        return new DeviceDetailPresenter();
    }

    @Override
    public void setDeviceInfo(LastStatus status) {
        String deviceId = status.getRecord().getDevice().getName();
        mTVDeviceNo.setText(deviceId);
        String deviceType = status.getRecord().getDevice().getDeviceType().getName();
        mTVDeviceType.setText(deviceType.toUpperCase());
        //设置设备状态
        String strStatus = DeviceUtils.INSTANCE.getStatusArrName(status.getRecord().getStatus());
        mTVStatus.setText(strStatus);

        if(status.getRecord().getExtend()!= null){
            Quantity quantity = status.getRecord().getExtend().getQuantity();
            mTVProcessNum.setText(quantity.getCurrentSum()+"");
        }
        long duration = status.getRecord().getDuration();
        String strDuration = TimeUtils.getFitTimeSpan(duration,0,4);
        mTVStatusTime.setText(strDuration);

        mTVStatusDec.setText(String.format(getResources().getString(R.string.device_status_desc_f),strStatus,strDuration));
        String imageUrl = status.getRecord().getDevice().getDeviceType().getImg();
        Glide.with(this).load(imageUrl).into(mIVDevice);
    }

    @Override
    public void showError(String msg) {
        if(StringUtils.isTrimEmpty(msg)){
            return;
        }
        SnackbarUtils.with(getWindow().getDecorView()).setMessage(msg).showError();
    }

    private static class MyHandler extends Handler{

        public static final int UPDATE = 0x0001;

        private WeakReference<DeviceDetailActivity> mActivity;

        public MyHandler(DeviceDetailActivity activity){
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE:
                    if(mActivity.get() != null && !mActivity.get().isFinishing()){
                        mActivity.get().loadData();
                        sendEmptyMessageDelayed(UPDATE,UPDATE_FREQUENCY);
                    }
                    break;
            }
        }
    }
}
