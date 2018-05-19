package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.OrderListAdapter;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.Order;
import com.sf.smartfactory.network.response.OrderListResponse;
import com.sf.smartfactory.view.ListItemDecoration;
import com.sf.smartfactory.view.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/5/19 21:07
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 订单列表碎片
 */
public class OrderListFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView mRV;

    private StateView mStateView;

    private List<Order> mOrders;
    private OrderListAdapter mAdapter;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static OrderListFragment newInstance(Bundle params){
        OrderListFragment instance = new OrderListFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_order_list,null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        mOrders = new ArrayList<>();
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
//                mStateView.showRetry();
                ToastUtils.showLong("数据异常");
            }
        });
    }

    private void setOrderList(List<Order> list){
        mOrders.clear();
//        if(list == null){
//            mStateView.showRetry();
//            return;
//        }
//        if(list.isEmpty()){
//            mStateView.showEmpty();
//            return;
//        }
        mOrders.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
