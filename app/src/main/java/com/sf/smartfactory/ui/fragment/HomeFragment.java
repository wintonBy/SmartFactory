package com.sf.smartfactory.ui.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.DevicesListAdapter;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.DeviceStatus;
import com.sf.smartfactory.network.response.DeviceListResponse;
import com.sf.smartfactory.network.response.DeviceSummaryResponse;
import com.sf.smartfactory.ui.activity.DeviceDetailActivity;
import com.sf.smartfactory.ui.activity.DeviceListActivity;
import com.sf.smartfactory.utils.DrawableUtils;
import com.sf.smartfactory.view.DevicePieValueFormatter;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sf.smartfactory.view.ListItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.github.mikephil.charting.charts.Chart.PAINT_INFO;

/**
 * @author: winton
 * @time: 2018/3/27 17:37
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 主页
 */
public class HomeFragment extends BaseFragment{

    @BindView(R.id.tv_normal)
    TextView mTVNormal;
    @BindView(R.id.tv_error)
    TextView mTVError;
    @BindView(R.id.tv_offline)
    TextView mTVOffline;
    @BindView(R.id.tv_all)
    TextView mTVAll;
    @BindView(R.id.rv_device_list)
    RecyclerView mRVDeviceList;
    @BindView(R.id.tv_fac_pic)
    TextView mTVPic;
    @BindView(R.id.tv_device_list)
    TextView mTVDeviceList;
    @BindView(R.id.lay_fac_pic)
    View mFacPic;
    @BindView(R.id.iv_pic_fac)
    ImageView mIVFac;
    @BindView(R.id.tv_refresh_time)
    TextView mTVTime;
    @BindView(R.id.tv_today)
    TextView mTVToday;

    private View checkView = mTVPic;
    Drawable leftTabCheck = null;
    Drawable rightTabCheck = null;



    private View rootView;
    private DevicesListAdapter mAdapter;
    private List<DeviceStatus> mDevices;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static HomeFragment newInstance(Bundle params){
        HomeFragment instance = new HomeFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.frag_home,null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }

    private void initData(){
        mDevices = new ArrayList<>();
        setTvToday();
        leftTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_tab_left,R.color.colorPrimary);
        rightTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_tab_right,R.color.colorPrimary);
        checkFacPic();
        mRVDeviceList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRVDeviceList.addItemDecoration(new ListItemDecoration());
        mAdapter = new DevicesListAdapter(getActivity(),mDevices);
        mAdapter.setListener(new DevicesListAdapter.ItemClickListener() {
            @Override
            public void clickItem(DeviceStatus item) {
                Bundle  params = new Bundle();
                params.putString("deviceId",item.getDevice().getId());
                DeviceDetailActivity.start(getActivity(),params);
            }
        });
        mRVDeviceList.setAdapter(mAdapter);
        loadData();
        loadFacImg();

    }

    /**
     * 加载工厂图片
     */
    private void loadFacImg(){
        if(MyApplication.mFactoryInfo != null && MyApplication.mFactoryInfo.getWorkshopImgApp() != null){
            Glide.with(this).load(MyApplication.mFactoryInfo.getWorkshopImgApp()).into(mIVFac);
        }else {

        }
    }

    private void showDevicesList(List<DeviceStatus> list) {
        if(list == null){
            return;
        }
        if(list.isEmpty()){
            return;
        }
        mDevices.clear();
        mDevices.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 获取页面上设备简况
     */
    private void loadDeviceSummary(){
        RetrofitClient.getInstance().deviceSummary(new BaseSubscriber<DeviceSummaryResponse>(){

            @Override
            public void success(DeviceSummaryResponse deviceSummaryResponse) {
                if( !ObjectUtils.isEmpty(deviceSummaryResponse.getData())){
                    DeviceSummaryResponse.Summary summary = deviceSummaryResponse.getData();
                    setNormal(summary.getNormal());
                    setError(summary.getAbnormal());
                    setOffline(summary.getOffline());
                    setALL(summary.getNormal()+summary.getAbnormal()+summary.getOffline());
                }
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }

    private void loadDeviceList(){
        DeviceListResponse.loadList(new BaseSubscriber<DeviceListResponse>(){

            @Override
            public void success(DeviceListResponse deviceListResponse) {
                if( deviceListResponse.getData() == null){
                    ToastUtils.showLong("数据异常");
                    return;
                }
                showDevicesList(deviceListResponse.getData().getList());
            }

            @Override
            public void failed(Throwable e) {
                ToastUtils.showLong(e.getMessage());
            }
        });
    }

    /**
     * 设置正常的数量
     * @param num
     */
    private void setNormal(int num){
        if(mTVNormal != null){
            mTVNormal.setText(String.valueOf(num));
        }
    }
    /**
     * 设置异常设备的数量
     * @param num
     */
    private void setError(int num){
        if(mTVError != null){
            mTVError.setText(String.valueOf(num));
        }
    }

    private void setOffline(int num){
        if(mTVOffline != null){
            mTVOffline.setText(String.valueOf(num));
        }
    }

    private void setALL(int all){
        if(mTVAll != null){
            mTVAll.setText(String.valueOf(all));
        }
    }


    @OnClick(R.id.cv_normal)
    public void clickNormal(View v){
        Bundle bundle = new Bundle();
        bundle.putString("type",DeviceListActivity.LIST_NORMAL);
        DeviceListActivity.start(getActivity(),bundle);
    }

    @OnClick(R.id.cv_error)
    public void clickError(View v){
        Bundle bundle = new Bundle();
        bundle.putString("type",DeviceListActivity.LIST_ERROR);
        DeviceListActivity.start(getActivity(),bundle);
    }

    @OnClick(R.id.cv_offline)
    public void clickOffline(View v){
        Bundle bundle = new Bundle();
        bundle.putString("type",DeviceListActivity.LIST_OFFLINE);
        DeviceListActivity.start(getActivity(),bundle);
    }
    @OnClick(R.id.cv_all)
    public void clickAll(View v){
        Bundle bundle = new Bundle();
        bundle.putString("type",DeviceListActivity.LIST_ALL);
        DeviceListActivity.start(getActivity(),bundle);
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
    public void onUpdateData(UpdateDataEvent event){
        loadData();
    }

    private void loadData(){
        loadDeviceSummary();
        loadDeviceList();
        setRefreshTime();
    }

    @OnClick(R.id.tv_fac_pic)
    public void clickFactPic(View view){
        if(view == checkView){
            return;
        }
        checkFacPic();

    }
    @OnClick(R.id.tv_device_list)
    public void clickDeviceList(View view){
        if(view == checkView){
            return;
        }
        checkDeviceList();
    }
    /**
     * 展示上次刷新的页面时间
     */
    private void setRefreshTime(){
        String time = TimeUtils.getString(System.currentTimeMillis(),0,0);
        mTVTime.setText(String.format(getString(R.string.last_refresh_f),time));
    }

    /**
     *显示今天日期
     */
    private void setTvToday(){
        String week = TimeUtils.getChineseWeek(System.currentTimeMillis());
        String date = TimeUtils.getNowString(new SimpleDateFormat("MM月dd日"));
        mTVToday.setText(date+"·"+week);
    }

    /**
     * 选择工厂视图
     */
    private void checkFacPic(){
        checkView = mTVPic;
        mTVPic.setBackground(leftTabCheck);
        mTVPic.setTextColor(Color.WHITE);
        mTVDeviceList.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_right));
        mTVDeviceList.setTextColor(Color.BLACK);
        mFacPic.setVisibility(View.VISIBLE);
        mRVDeviceList.setVisibility(View.INVISIBLE);
    }

    /**
     * 选择设备列表
     */
    private void checkDeviceList(){
        checkView = mTVDeviceList;
        mTVPic.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_left));
        mTVDeviceList.setBackground(rightTabCheck);
        mTVDeviceList.setTextColor(Color.WHITE);
        mTVPic.setTextColor(Color.BLACK);
        mFacPic.setVisibility(View.INVISIBLE);
        mRVDeviceList.setVisibility(View.VISIBLE);
    }

}
