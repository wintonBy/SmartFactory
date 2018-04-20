package com.sf.smartfactory.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/3/27 19:16
 * @package: com.sf.smartfactory.adapter
 * @project: SmartFactory
 * @mail:
 * @describe: 适配器基类
 */
public abstract class ILViewBaseAdapter<T> extends BaseAdapter{

    public List<T> mSource;
    public Context mContext;

    public ILViewBaseAdapter(Context mContext, List<T> mSource) {
        this.mSource = mSource;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mSource == null ? 0 : mSource.size();
    }

    @Override
    public T getItem(int position) {
        return mSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public  abstract View getView(int position, View convertView, ViewGroup parent);
}
