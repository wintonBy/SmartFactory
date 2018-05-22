package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.TimedText;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.OrderPartAdapter;
import com.sf.smartfactory.contract.OrderDetailContract;
import com.sf.smartfactory.network.bean.Order;
import com.sf.smartfactory.presenter.OrderDetailPresenter;
import com.sf.smartfactory.utils.OrderUtils;
import com.sf.smartfactory.view.ListItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/5/22 17:41
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 订单详情页
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {

    @BindView(R.id.tv_title)
    TextView mTVTitle;
    @BindView(R.id.tv_order_name)
    TextView mTVOrderName;
    @BindView(R.id.tv_client_name)
    TextView mTVClientName;
    @BindView(R.id.tv_status)
    TextView mTVStatus;
    @BindView(R.id.tv_start)
    TextView mTVStart;
    @BindView(R.id.tv_end)
    TextView mTVEnd;
    @BindView(R.id.part_list)
    RecyclerView mRV;
    @BindView(R.id.tv_all_process)
    TextView mTVPercent;

    private OrderPartAdapter mAdapter;
    private Order order;

    /**
     * 进入详情页的方法
     * @param context
     */
    public static void start(Context context, Order order) {
        if (context == null || order == null) {
            throw new IllegalArgumentException("context should not null");
        }
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order",order);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.frag_order_detail);
        ButterKnife.bind(this);
        findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        mTVTitle.setText("订单详情");
        order = (Order) getIntent().getSerializableExtra("order");
        mAdapter = new OrderPartAdapter(this,order.getRelations());
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.addItemDecoration(new ListItemDecoration());
        mRV.setAdapter(mAdapter);
        mTVOrderName.setText(getOrderName(order));
        mTVStatus.setText(getStatus(order));
        mTVStart.setText(getStart(order));
        mTVEnd.setText(getEnd(order));
        mTVClientName.setText(getClient(order));
        mTVPercent.setText(getPercent(order));
    }

    private String getOrderName(Order order){
        return TextUtils.isEmpty(order.getName())? "--":order.getName();
    }

    private String getStatus(Order order){
        return OrderUtils.INSTANCE.getStatusName(order.getStatus());
    }
    private String getStart(Order order){
        return "计划开始："+ order.getStartDt();
    }
    private String getEnd(Order order){
        return "计划结束："+ order.getEndDt();
    }
    private String getClient(Order order){
        String clientName = order.getExtend() == null? "":order.getExtend().getClientName();
        return String.format(getResources().getString(R.string.client_name_f), ObjectUtils.isEmpty(clientName)? "未知":clientName);
    }

    private String getPercent(Order order){
        return "总进度"+order.getExtend().getPercent()+"%";
    }

    @OnClick(R.id.iv_back)
    public void clickBack(View view){
        this.finish();
    }

    @Override
    protected OrderDetailPresenter loadPresenter() {
        return new OrderDetailPresenter();
    }
}
