package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.OrderListAdapter;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.Order;
import com.sf.smartfactory.network.response.OrderListResponse;
import com.sf.smartfactory.view.ListItemDecoration;
import com.sf.smartfactory.view.stateview.StateView;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/3/27 18:01
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 订单页面
 */
public class OrderFragment extends BaseFragment {

    private View rootView;
    @BindView(R.id.rv)
    RecyclerView mRV;
    private StateView mStateView;
    @BindView(R.id.tv_refresh_time)
    TextView mTVTime;
    @BindView(R.id.tv_today)
    TextView mTVToday;

    private List<Order> mOrders;
    private OrderListAdapter mAdapter;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static OrderFragment newInstance(Bundle params){
        OrderFragment instance = new OrderFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_order,null);
        ButterKnife.bind(this,rootView);
        mStateView = StateView.inject(mRV);
        initData();
        return rootView;
    }
    private void initData(){
        mOrders = new ArrayList<>();
        setTvToday();
        mAdapter = new OrderListAdapter(getActivity(),mOrders);
        mRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRV.addItemDecoration(new ListItemDecoration());
        mRV.setAdapter(mAdapter);
        loadOrders();
    }
    private void loadOrders(){
        RetrofitClient.getInstance().orderList(new BaseSubscriber<OrderListResponse>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(OrderListResponse orderListResponse) {
                super.onNext(orderListResponse);
                if(!ObjectUtils.isEmpty(orderListResponse)
                        && orderListResponse.isSuccess()){
                    setOrderList(orderListResponse.getData().getList());
                    return;
                }
                mStateView.showRetry();
                ToastUtils.showLong("数据异常");
            }
        });
        setRefreshTime();
    }

    private void setOrderList(List<Order> list){
        mOrders.clear();
        if(list == null){
            mStateView.showRetry();
            return;
        }
        if(list.isEmpty()){
            mStateView.showEmpty();
            return;
        }
        mOrders.addAll(list);
        mAdapter.notifyDataSetChanged();
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
    @Subscribe
    public void refreshData(UpdateDataEvent event){
        loadOrders();
    }

    /**
     * 展示上次刷新的页面时间
     */
    private void setRefreshTime(){
        String time = TimeUtils.getString(System.currentTimeMillis(),0,0);
        mTVTime.setText(String.format(getString(R.string.last_refresh_f),time));
    }

    /**
     *
     */
    private void setTvToday(){
        String week = TimeUtils.getChineseWeek(System.currentTimeMillis());
        String date = TimeUtils.getNowString(new SimpleDateFormat("MM月dd日"));
        mTVToday.setText(date+"·"+week);
    }

}
