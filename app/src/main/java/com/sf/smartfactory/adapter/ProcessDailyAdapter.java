package com.sf.smartfactory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.Device;
import com.sf.smartfactory.network.bean.ProcessNum;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/8/9 10:03
 * @package: com.sf.smartfactory.adapter
 * @project: Factory
 * @mail:
 * @describe: 每日加工数量适配器
 */
public class ProcessDailyAdapter extends IRVBaseAdapter<List<ProcessNum>,ProcessDailyAdapter.ViewHolder> {

    public ProcessDailyAdapter(Context mContext, List<List<ProcessNum>> mSource) {
        super(mContext, mSource);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_process_num_daily,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private View getDailyView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_process_num_daily_inner,null);
        return view;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<ProcessNum> item = mSource.get(position);
        holder.initDaily(item.size());
        holder.bindData(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_daily)
        LinearLayout llDaily;
        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }

        public void bindData(List<ProcessNum> data){
            for(int i=0;i<data.size();i++){
                if(i == 0){
                    Device device = data.get(i).getDevice();
                    setName(device);
                }
                setDaily(data.get(i),i);
            }
        }

        public void initDaily(int size){
            int childCount = llDaily.getChildCount();
            LogUtils.d(size);
            if(childCount == size){
                return;
            }
            if(childCount > size){
                int out = childCount - size;
                for (int i=1;i<=out;i++){
                    llDaily.removeViewAt(childCount-i);
                }
                return;
            }
            if(childCount <size){
                int in = size - childCount;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                for(int i=1;i<=in;i++){
                    View view = getDailyView();
                    llDaily.addView(view,params);
                }
            }
        }

        private void setDaily(ProcessNum processNum,int position) {
            View item = llDaily.getChildAt(position);
            TextView date = item.findViewById(R.id.tv_date);
            date.setText(String.format(mContext.getResources().getString(R.string.process_date_f),processNum.getDate()));
            TextView num = item.findViewById(R.id.tv_num);
            num.setText(String.format(mContext.getResources().getString(R.string.process_num_f),processNum.getNum()));
            if(position == llDaily.getChildCount() -1){
                item.findViewById(R.id.divider_line).setVisibility(View.INVISIBLE);
            }
        }


        private void setName(Device device){
            String name = device.getName();
            tvName.setText(String.format(mContext.getResources().getString(R.string.device_no_f),name));
        }
    }
}
