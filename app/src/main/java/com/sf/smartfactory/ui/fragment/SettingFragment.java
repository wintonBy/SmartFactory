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
 * @time: 2018/5/5 23:52
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 设置页面
 */
public class SettingFragment extends BaseFragment{

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static SettingFragment newInstance(Bundle params){
        SettingFragment instance = new SettingFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_setting,null);
        ButterKnife.bind(this,view);
        return view;
    }
}
