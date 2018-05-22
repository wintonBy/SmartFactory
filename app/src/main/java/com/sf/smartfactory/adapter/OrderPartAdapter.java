package com.sf.smartfactory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.OrderRelation;
import com.sf.smartfactory.network.bean.Part;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/5/22 20:25
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class OrderPartAdapter extends IRVBaseAdapter<OrderRelation,OrderPartAdapter.ViewHolder> {

    public OrderPartAdapter(Context mContext, List<OrderRelation> mSource) {
        super(mContext, mSource);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_part,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderRelation item = mSource.get(position);
        holder.bindData(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_part_no)
        TextView mPartNo;
        @BindView(R.id.tv_part_target)
        TextView mTarget;
        @BindView(R.id.tv_part_over)
        TextView mPartOver;
        @BindView(R.id.pb_part)
        ProgressBar mpbBar;
        @BindView(R.id.tv_part_percent)
        TextView mTVPercent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bindData(OrderRelation data){
            mPartNo.setText(data.getPart().getNo()+"·"+data.getPart().getName());
            mTarget.setText("目标数量:"+data.getTarget().getQuantity());
            mPartOver.setText("已完成:"+data.getTarget().getDoneNum());
            mpbBar.setProgress(data.getTarget().getPercent());
            mTVPercent.setText("进度"+data.getTarget().getPercent()+"%");
        }
    }
}
