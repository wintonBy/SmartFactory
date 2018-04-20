package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.DevicesListAdapter;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.contract.DeviceListContract;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.bean.Device;
import com.sf.smartfactory.network.bean.DeviceStatus;
import com.sf.smartfactory.presenter.DeviceListPresenter;
import com.sf.smartfactory.view.TypePopWindow;
import com.sf.smartfactory.view.stateview.StateView;
import com.wasu.iutils.SnackbarUtils;
import com.wasu.iutils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/3/29 23:38
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 设备列表页
 */
public class DeviceListActivity extends BaseActivity<DeviceListPresenter> implements DeviceListContract.View {



    @BindView(R.id.rv_device_list)
    RecyclerView mRVDevicesList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_title)
    TextView mTVTitle;
    @BindView(R.id.iv_more)
    ImageView mIVMore;

    private View popView;
    private PopupWindow popupWindow;
    private StateView mStateView;

    private DevicesListAdapter mAdapter;
    private List<DeviceStatus> mDevices;
    private String type = LIST_ALL;

    public static final String LIST_ALL = "all";
    public static final String LIST_NORMAL = "normal";
    public static final String LIST_ERROR = "error";
    public static final String LIST_OFFLINE = "offline";

    /**
     * 启动设备列表页的方法
     * @param context
     * @param params
     */
    public static void start(Context context, Bundle params) {
        if (context == null) {
            throw new IllegalArgumentException("context should not null");
        }
        Intent intent = new Intent(context, DeviceListActivity.class);
        if (params != null) {
            intent.putExtras(params);
        }
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_device_list);
        ButterKnife.bind(this);
        findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        mIVMore.setVisibility(View.VISIBLE);
        popView = LayoutInflater.from(this).inflate(R.layout.layout_device_list_pop,null,false);
        mStateView = StateView.inject(mRefreshLayout);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        mTVTitle.setText("设备列表");
    }

    @Override
    public void showDevicesList(List<DeviceStatus> list) {
        if(mRefreshLayout != null ){
            mRefreshLayout.finishRefresh();
        }
        if(list == null){
            mStateView.showRetry();
            return;
        }
        if(list.isEmpty()){
            mStateView.showEmpty();
            return;
        }
        mStateView.showContent();
        mDevices.clear();
        mDevices.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        mStateView.showLoading();
    }

    @Override
    protected void initData() {
        super.initData();
        type = getIntent().getExtras().getString("type",LIST_ALL);
        mRVDevicesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mDevices = new ArrayList<>();
        mAdapter = new DevicesListAdapter(this,mDevices);
        mAdapter.setListener(new DevicesListAdapter.ItemClickListener() {
            @Override
            public void clickItem(DeviceStatus item) {
                Bundle  params = new Bundle();
                params.putString("deviceId",item.getDevice().getId());
                DeviceDetailActivity.start(DeviceListActivity.this,params);
            }
        });
        mRVDevicesList.setAdapter(mAdapter);
        mStateView.showLoading();
        refreshData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               refreshData();
            }
        });
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                refreshData();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void clickBack(View view){
        this.finish();
    }
    @OnClick(R.id.iv_more)
    public void clickMore(View view){
        if(popupWindow == null){
            initPopListener();
            popupWindow = new PopupWindow(this);
            popupWindow.setContentView(popView);
            popupWindow.setWidth((int)getResources().getDimension(R.dimen.type_pop_width));
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setHeight((int)getResources().getDimension(R.dimen.type_pop_height));
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setClippingEnabled(false);
            popupWindow.update();
        }
        popupWindow.showAsDropDown(mIVMore,-(int)getResources().getDimension(R.dimen.type_pop_width)/3*2,0);
    }

    private void initPopListener(){
        popView.findViewById(R.id.tv_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = LIST_NORMAL;
                refreshData();
                popupWindow.dismiss();
            }
        });
        popView.findViewById(R.id.tv_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = LIST_ERROR;
                refreshData();
                popupWindow.dismiss();
            }
        });
        popView.findViewById(R.id.tv_offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = LIST_OFFLINE;
                refreshData();
                popupWindow.dismiss();
            }
        });
        popView.findViewById(R.id.tv_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = LIST_ALL;
                refreshData();
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected DeviceListPresenter loadPresenter() {
        return new DeviceListPresenter();
    }

    @Override
    public void showError(String msg) {
        if(StringUtils.isTrimEmpty(msg)){
            return;
        }
        SnackbarUtils.with(getWindow().getDecorView()).setMessage(msg).showError();
        mStateView.showRetry();
    }

    /**
     * 刷新页面数据
     */
    private void refreshData(){
        mPresenter.loadDeviceList(type);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(UpdateDataEvent event){
        refreshData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
