package com.sf.smartfactory.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.DeviceStatus;
import com.sf.smartfactory.network.bean.ParamsBean;
import com.sf.smartfactory.utils.DeviceUtils;
import com.sf.smartfactory.utils.DrawableUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/3/30 14:36
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 设备列表适配器
 */
public class DevicesListAdapter extends IRVBaseAdapter<DeviceStatus,DevicesListAdapter.DevicesViewHolder> {

    private ItemClickListener mListener;

    public DevicesListAdapter(Context mContext, List<DeviceStatus> mSource) {
        super(mContext, mSource);
    }

    @Override
    public DevicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_device_list,parent,false);
        return  new DevicesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DevicesViewHolder holder, int position) {
        final DeviceStatus item = mSource.get(position);
        holder.bindDeviceData(item);
        if(mListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickItem(item);

                    
                }
            });
        }
    }

    class DevicesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_device_num)
        TextView mTVDeviceNum;
        @BindView(R.id.tv_device_status)
        TextView mTVDeviceStatus;
        @BindView(R.id.tv_device_type)
        TextView mTVDeviceType;
        @BindView(R.id.iv_device)
        ImageView mIVDevice;
        @BindView(R.id.tv_device_rate)
        TextView mTVDeviceRate;

        public DevicesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        /**
         * 绑定数据
         * @param item
         */
        public void bindDeviceData(DeviceStatus item){
            clearRate();
            if(ObjectUtils.isEmpty(item)){
                return;
            }
            mTVDeviceStatus.setText(DeviceUtils.INSTANCE.getStatusArrName(item.getStatus()));
            mTVDeviceStatus.setBackground(getTagBgByType(item.getStatus()));

            if(!ObjectUtils.isEmpty(item.getDevice())){
                //获取设备参数
                mTVDeviceType.setText(String.format(mContext.getResources().getString(R.string.type_f),item.getDevice().getDeviceType().getName().toUpperCase()));
                String orgStr = item.getDevice().getName();
                mTVDeviceNum.setText(orgStr);
                String imgUrl = item.getDevice().getDeviceType().getImg();
                mIVDevice.setBackgroundDrawable(getDeviceBgByType(item.getStatus()));
                Glide.with(mContext).asDrawable().load(imgUrl).into(mIVDevice);
            }
            if(!ObjectUtils.isEmpty(item.getExtend())){
                //获取扩展参数
                if(!ObjectUtils.isEmpty(item.getExtend().getParams())){
                    ParamsBean paramsBean = item.getExtend().getParams();
                    mTVDeviceRate.setText(String.format(mContext.getResources().getString(R.string.device_rate_f),
                                        paramsBean.getAxis_rate(),
                                        paramsBean.getFast_rate(),
                                        paramsBean.getFeed_rate()
                                        ));
                }
            }

        }

        /**
         * 设置倍率的默认值
         */
        private void clearRate(){
            mTVDeviceRate.setText(mContext.getResources().getString(R.string.device_rate_d));
        }
    }



    /**
     * 根据类型设置设备背景颜色
     * @param status
     * @return
     */
    private Drawable getDeviceBgByType(String status){
        int colorId = R.color.normal_color;
        if(DeviceStatus.OFFLINE.equals(status)){
            colorId = R.color.offline_color;
        }
        if(DeviceStatus.PAUSE.equals(status)){
            colorId = R.color.pause_color;
        }
        if(DeviceStatus.EDITING.equals(status)){
            colorId = R.color.editing_color;
        }
        if(DeviceStatus.WORKING.equals(status)){
            colorId = R.color.working_color;
        }
        if(DeviceStatus.IDLE.equals(status)){
            colorId = R.color.idle_color;
        }
        return DrawableUtils.INSTANCE.changeDrawableColor(mContext,R.drawable.device_img_bg,colorId);
    }

    /**
     * 根据状态类型设置Tag背景颜色
     * @param status
     * @return
     */
    private Drawable getTagBgByType(String status){
        int colorId = R.color.normal_color;
        if(DeviceStatus.OFFLINE.equals(status)){
            colorId = R.color.offline_color;
        }
        if(DeviceStatus.PAUSE.equals(status)){
            colorId = R.color.pause_color;
        }
        if(DeviceStatus.EDITING.equals(status)){
            colorId = R.color.editing_color;
        }
        if(DeviceStatus.WORKING.equals(status)){
            colorId = R.color.working_color;
        }
        if(DeviceStatus.IDLE.equals(status)){
            colorId = R.color.idle_color;
        }
        return DrawableUtils.INSTANCE.changeDrawableColor(mContext,R.drawable.tag_bg,colorId);
    }


    public void setListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    public interface ItemClickListener{
        /**
         * 点击Item
         * @param item
         */
        void clickItem(DeviceStatus item);
    }
}
