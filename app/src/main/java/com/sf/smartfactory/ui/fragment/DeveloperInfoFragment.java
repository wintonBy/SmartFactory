package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.smartfactory.R;

/**
 * @author: winton
 * @time: 2018/5/2 23:21
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class DeveloperInfoFragment extends BaseFragment {

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static DeveloperInfoFragment newInstance(Bundle params){
        DeveloperInfoFragment instance = new DeveloperInfoFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_developer_info,null);
        return view;
    }
}
