package com.sf.smartfactory.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.Order;
import com.sf.smartfactory.utils.OrderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/4/3 17:49
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 订单列表适配器
 */
public class OrderListAdapter extends IRVBaseAdapter<Order,OrderListAdapter.OrderViewHolder>{

    public OrderListAdapter(Context mContext, List<Order> mSource) {
        super(mContext, mSource);
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_list,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order item = mSource.get(position);
        holder.bindData(item);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_order_no)
        TextView mTVOrderNo;
        @BindView(R.id.tv_order_name)
        TextView mTVOrderName;
        @BindView(R.id.tv_client_name)
        TextView mTVClientName;
        @BindView(R.id.tv_plan_s_time)
        TextView mTVPlanSTime;
        @BindView(R.id.tv_plan_e_time)
        TextView mTVPlanETime;
        @BindView(R.id.tv_status)
        TextView mTVStatus;
        @BindView(R.id.pb_order)
        ProgressBar mPbOrder;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mTVClientName.setVisibility(View.INVISIBLE);
        }

        public void bindData(Order item){
            if(ObjectUtils.isEmpty(item)){
                return;
            }
            mTVOrderNo.setText(String.format(mContext.getResources().getString(R.string.order_no_f),item.getNo()));
            mTVOrderName.setText(item.getName());
            mTVStatus.setText(OrderUtils.INSTANCE.getStatusName(item.getStatus()));

            if(!ObjectUtils.isEmpty(item.getExtend())){
                String clientName = item.getExtend().getClientName();
                if(!ObjectUtils.isEmpty(clientName)){
                    mTVClientName.setVisibility(View.VISIBLE);
                    mTVClientName.setText(String.format(mContext.getResources().getString(R.string.client_name_f),clientName));
                }
            }
            String start = item.getStartDt();
            String end = item.getEndDt();
            mTVPlanSTime.setText("计划开始:"+start);
            mTVPlanETime.setText("计划结束:"+end);
        }
    }
}
