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
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        leftTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_tab_left,R.color.colorPrimary);
        rightTabCheck = DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_tab_right,R.color.colorPrimary);
        checkFacPic();
        mRVDeviceList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
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
        loadDeviceSummary();
        loadDeviceList();
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
            public void onNext(DeviceSummaryResponse deviceSummaryResponse) {
                super.onNext(deviceSummaryResponse);
                if(deviceSummaryResponse == null){
                    ToastUtils.showLong("数据异常");
                    return;
                }
                if( !ObjectUtils.isEmpty(deviceSummaryResponse)
                    && deviceSummaryResponse.isSuccess()
                    && !ObjectUtils.isEmpty(deviceSummaryResponse.getData())){
                    DeviceSummaryResponse.Summary summary = deviceSummaryResponse.getData();
                    setNormal(summary.getNormal());
                    setError(summary.getAbnormal());
                    setOffline(summary.getOffline());
                    setALL(summary.getNormal()+summary.getAbnormal()+summary.getOffline());
                }
            }
        });
    }

    private void loadDeviceList(){
        RetrofitClient.getInstance().monitorDeviceList(new BaseSubscriber<DeviceListResponse>(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(DeviceListResponse deviceListResponse) {
                super.onNext(deviceListResponse);
                if(deviceListResponse == null || deviceListResponse.getData() == null){
                    ToastUtils.showLong("数据异常");
                    return;
                }
                showDevicesList(deviceListResponse.getData().getList());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showLong("服务异常");
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
        loadDeviceSummary();
        loadDeviceList();
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

    private void checkFacPic(){
        checkView = mTVPic;
        mTVPic.setBackground(leftTabCheck);
        mTVPic.setTextColor(Color.WHITE);
        mTVDeviceList.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_right));
        mTVDeviceList.setTextColor(Color.BLACK);
        mFacPic.setVisibility(View.VISIBLE);
        mRVDeviceList.setVisibility(View.GONE);
    }
    private void checkDeviceList(){
        checkView = mTVDeviceList;
        mTVPic.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_tab_left));
        mTVDeviceList.setBackground(rightTabCheck);
        mTVDeviceList.setTextColor(Color.WHITE);
        mTVPic.setTextColor(Color.BLACK);
        mFacPic.setVisibility(View.GONE);
        mRVDeviceList.setVisibility(View.VISIBLE);
    }

}
