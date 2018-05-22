package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.smartfactory.R;

import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/5/21 21:01
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 订单详情页
 */
public class OrderDetailFragment extends BaseFragment {

    /**
     * 生成该类的实例
     * @param params
     * @return
     */
    public static OrderDetailFragment newInstance(Bundle params){
        OrderDetailFragment instance = new OrderDetailFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_order_detail,null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }


    public void initData(){

    }

}
