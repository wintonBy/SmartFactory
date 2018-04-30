package com.sf.smartfactory.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.Stuff;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/4/5 10:38
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 员工列表适配器
 */
public class StuffListAdapter extends IRVBaseAdapter<Stuff,StuffListAdapter.ViewHolder> {

    public StuffListAdapter(Context mContext, List<Stuff> mSource) {
        super(mContext, mSource);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_stuff_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stuff item = mSource.get(position);
        holder.bindData(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_stuff_no)
        TextView mTVNo;
        @BindView(R.id.tv_stuff_name)
        TextView mTVName;
        @BindView(R.id.tv_stuff_phone)
        TextView mTVPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(Stuff item){
            if(item == null){
                return;
            }
            int rightDraw = R.mipmap.ic_male;
            mTVName.setText(item.getName());
            if(item.getGender() == Stuff.FEMALE){
                //1是男，2是女，0是未知
                rightDraw = R.mipmap.ic_female;
            }
            Drawable drawable = mContext.getResources().getDrawable(rightDraw);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTVName.setCompoundDrawables(null,null,drawable,null);
            mTVNo.setText(String.format(mContext.getResources().getString(R.string.stuff_no_f),item.getNo()));
            String phone = item.getPhone();
            if(StringUtils.isTrimEmpty(phone)){
                phone = "未知";
            }
            mTVPhone.setText(String.format(mContext.getResources().getString(R.string.phone_f),phone));
        }
    }
}
