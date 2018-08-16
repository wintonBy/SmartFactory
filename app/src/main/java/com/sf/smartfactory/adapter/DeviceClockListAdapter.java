package com.sf.smartfactory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.DeviceClock;
import com.sf.smartfactory.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/5/10 14:58
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class DeviceClockListAdapter extends IRVBaseAdapter<DeviceClock,DeviceClockListAdapter.ViewHolder> {

    public DeviceClockListAdapter(Context mContext, List<DeviceClock> mSource) {
        super(mContext, mSource);
    }

    @Override
    public DeviceClockListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device_clock_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceClockListAdapter.ViewHolder holder, int position) {
        DeviceClock item = mSource.get(position);
        String title = getTitle(item);
        holder.setTitle(title);

        String maxC =  DateUtils.INSTANCE.getTimeSpan(item.getWorkMax());
        String minC = DateUtils.INSTANCE.getTimeSpan(item.getWorkMin());
        String avgC = DateUtils.INSTANCE.getTimeSpan(item.getWorkAvg());
        String workHistory = DateUtils.INSTANCE.getTimeSpan(item.getWorkHistoryAvg());
        holder.setTvWorkMin(String.format(mContext.getString(R.string.min_f),minC));
        holder.setTvWorkAvg(String.format(mContext.getString(R.string.avg_f),avgC));
        holder.setTvWorkMax(String.format(mContext.getString(R.string.max_f),maxC));
        holder.setTvWorkHistory(workHistory);

        String maxR = DateUtils.INSTANCE.getTimeSpan(item.getRunMax());
        String minR = DateUtils.INSTANCE.getTimeSpan(item.getRunMin());
        String avgR = DateUtils.INSTANCE.getTimeSpan(item.getRunMin());
        String runHistory = DateUtils.INSTANCE.getTimeSpan(item.getRunHistoryAvg());
        holder.setTvRunMin(String.format(mContext.getString(R.string.min_f),minR));
        holder.setTvRunAvg(String.format(mContext.getString(R.string.avg_f),avgR));
        holder.setTvRunMax(String.format(mContext.getString(R.string.max_f),maxR));
        holder.setTvRunHistory(runHistory);


    }


    private String getTitle(DeviceClock clock){
        if(clock.getDevice() == null){
            return "";
        }
        String deviceName = clock.getDevice().getName();
        String product = clock.getPart() == null ? "--":clock.getPart().getName();
        String productNo = clock.getPart() == null ? "--":clock.getPart().getNo();
        StringBuilder builder = new StringBuilder();
        builder.append(deviceName)
                .append("·")
                .append(TextUtils.isEmpty(product)? "--":product)
                .append("·")
                .append(TextUtils.isEmpty(productNo)?"--":productNo);
        return builder.toString();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_work_min)
        TextView tvWorkMin;
        @BindView(R.id.tv_work_avg)
        TextView tvWorkAvg;
        @BindView(R.id.tv_work_max)
        TextView tvWorkMax;
        @BindView(R.id.tv_work_history)
        TextView tvWorkHistory;


        @BindView(R.id.tv_run_min)
        TextView tvRunMin;
        @BindView(R.id.tv_run_avg)
        TextView tvRunAvg;
        @BindView(R.id.tv_run_max)
        TextView tvRunMax;
        @BindView(R.id.tv_run_history)
        TextView tvRunHistory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setTitle(String title){
            if(!TextUtils.isEmpty(title)){
                tvTitle.setText(title);
            }
        }
        public void setTvWorkMin(String data){
            if(!TextUtils.isEmpty(data)){
                tvWorkMin.setText(data);
            }
        }
        public void setTvWorkAvg(String data){
            if(!TextUtils.isEmpty(data)){
                tvWorkAvg.setText(data);
            }
        }
        public void setTvWorkMax(String data){
            if(!TextUtils.isEmpty(data)){
                tvWorkMax.setText(data);
            }
        }
        public void setTvWorkHistory(String data){
            if(!TextUtils.isEmpty(data)){
                tvWorkHistory.setText("历史均值:"+data);
            }
        }

        public void setTvRunMin(String data){
            if(!TextUtils.isEmpty(data)){
                tvRunMin.setText(data);
            }
        }

        public void setTvRunAvg(String data){
            if(!TextUtils.isEmpty(data)){
                tvRunAvg.setText(data);
            }
        }
        public void setTvRunMax(String data){
            if(!TextUtils.isEmpty(data)){
                tvRunMax.setText(data);
            }
        }
        public void setTvRunHistory(String data){
            if(!TextUtils.isEmpty(data)){
                tvRunHistory.setText("历史均值:"+data);
            }
        }
    }
}
