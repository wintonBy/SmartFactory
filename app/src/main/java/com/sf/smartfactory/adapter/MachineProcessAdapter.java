package com.sf.smartfactory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.MachineProcess;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/4/4 11:10
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 加工信息列表适配
 */
public class MachineProcessAdapter extends IRVBaseAdapter<MachineProcess,MachineProcessAdapter.ViewHolder> {

    public MachineProcessAdapter(Context mContext, List<MachineProcess> mSource) {
        super(mContext, mSource);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_machine_process_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MachineProcess item = mSource.get(position);
        holder.bindData(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_machine_id)
        TextView mTVId;
        @BindView(R.id.tv_stuff_name)
        TextView mTVStuff;
        @BindView(R.id.tv_num)
        TextView mTVNum;
        @BindView(R.id.tv_product)
        TextView mTVProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(MachineProcess item){
            if(ObjectUtils.isEmpty(item)){
                return;
            }
            if(!ObjectUtils.isEmpty(item.getDevice())){
                mTVId.setText(item.getDevice().getName());
            }
            mTVNum.setText(String.valueOf(item.getSum()));
            mTVStuff.setText(getEmployeeName(item));
            mTVProduct.setText(getProductName(item));
        }
    }

    /**
     * 获取员工姓名
     * @param item
     * @return
     */
    private String getEmployeeName(MachineProcess item){
        if(item != null && item.getEmployee() != null && !TextUtils.isEmpty(item.getEmployee().getName())){
            return item.getEmployee().getName();
        }
        return "--";
    }

    /**
     *  获取产品名称
     * @param item
     * @return
     */
    private String getProductName(MachineProcess item){
        if(item != null && item.getPart() != null && !TextUtils.isEmpty(item.getPart().getName())){
            return item.getPart().getName();
        }
        return "--";
    }
}
