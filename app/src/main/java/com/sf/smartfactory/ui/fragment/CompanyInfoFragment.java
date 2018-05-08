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
 * @time: 2018/5/8 14:22
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 企业信息展示页
 */
public class CompanyInfoFragment extends BaseFragment{

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static CompanyInfoFragment newInstance(Bundle params){
        CompanyInfoFragment instance = new CompanyInfoFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_company,null);
        ButterKnife.bind(this,view);
        return view;
    }
}
