package com.sf.smartfactory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/3/27 19:56
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: RecyclerView基类
 */
public abstract class IRVBaseAdapter<S,T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public Context mContext;
    public List<S> mSource;

    public IRVBaseAdapter(Context mContext, List<S> mSource) {
        this.mContext = mContext;
        this.mSource = mSource;
    }

    @Override
    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(T holder, int position);

    @Override
    public int getItemCount() {
        return mSource == null? 0:mSource.size();
    }
}
