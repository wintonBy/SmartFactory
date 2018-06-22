package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.Company;
import com.sf.smartfactory.network.bean.FactoryInfo;

import butterknife.BindView;
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

    @BindView(R.id.tv_fac_name)
    TextView mTVFacName;
    @BindView(R.id.tv_found)
    TextView mTVFound;
    @BindView(R.id.tv_leader)
    TextView mTVLeader;
    @BindView(R.id.tv_emp)
    TextView mTVEmp;
    @BindView(R.id.tv_build_time)
    TextView mTVBuildTime;

    private Company company;

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
        initData();
        return view;
    }

    private void initData(){
        if(MyApplication.mFactoryInfo == null || MyApplication.mFactoryInfo.getCompany() == null){
            return;
        }
        company = MyApplication.mFactoryInfo.getCompany();

        mTVFacName.setText(String.format(getResources().getString(R.string.fac_name_f),TextUtils.isEmpty(company.getName())? "--":company.getName()));
        mTVBuildTime.setText(String.format(getResources().getString(R.string.build_time_f),TextUtils.isEmpty(company.getDate())? "--":company.getDate()));
        mTVFound.setText(String.format(getResources().getString(R.string.found_f),TextUtils.isEmpty(company.getFund())? "--":company.getFund()));
        mTVLeader.setText(String.format(getResources().getString(R.string.leader_f),TextUtils.isEmpty(company.getLegalperson())? "--":company.getLegalperson()));
        mTVEmp.setText(String.format(getResources().getString(R.string.emp_f),TextUtils.isEmpty(company.getEmpnums())? "--":company.getEmpnums()));
    }
}
