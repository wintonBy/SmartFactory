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
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.network.bean.DeviceClock;

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
        String workData = getWorkData(item);
        String runData = getRunData(item);
        holder.setTitle(title);
        holder.setTvWorkData(workData);
        holder.setTvRunData(runData);
    }


    private String getTitle(DeviceClock clock){
        if(clock.getDevice() == null){
            return "";
        }
        String deviceName = clock.getDevice().getName();
        String product = clock.getName();
        StringBuilder builder = new StringBuilder();
        builder.append("设备：")
                .append(deviceName);
        if(!TextUtils.isEmpty(product)){
            builder.append("产品：").append(product);
        }
        return builder.toString();

    }

    private String getWorkData(DeviceClock clock){
        String maxC = clock.getWorkMax()==0 ? "无":TimeUtils.getFitTimeSpan(clock.getWorkMax(), 0,4);
        String minC = clock.getWorkMin()==0 ? "无":TimeUtils.getFitTimeSpan(clock.getWorkMin(),0,4);
        String avgC = clock.getWorkAvg()==0 ? "无":TimeUtils.getFitTimeSpan(clock.getWorkAvg(),0,4);

        return String.format(mContext.getString(R.string.machine_clock_data_f),maxC,minC,avgC);
    }

    private String getRunData(DeviceClock clock){
        String maxR = clock.getRunMax()==0 ? "无":TimeUtils.getFitTimeSpan(clock.getRunMax(),0,4);
        String minR = clock.getRunMin()==0 ? "无":TimeUtils.getFitTimeSpan(clock.getRunMin(),0,4);
        String avgR = clock.getRunAvg()==0 ? "无":TimeUtils.getFitTimeSpan(clock.getRunMin(),0,4);
        return String.format(mContext.getString(R.string.machine_clock_data_f),maxR,minR,avgR);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_work_data)
        TextView tvWorkData;

        @BindView(R.id.tv_run_data)
        TextView tvRunData;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setTitle(String title){
            if(!TextUtils.isEmpty(title)){
                tvTitle.setText(title);
            }
        }
        public void setTvWorkData(String data){
            if(!TextUtils.isEmpty(data)){
                tvWorkData.setText(data);
            }
        }

        public void setTvRunData(String data){
            if(!TextUtils.isEmpty(data)){
                tvRunData.setText(data);
            }
        }
    }
}
