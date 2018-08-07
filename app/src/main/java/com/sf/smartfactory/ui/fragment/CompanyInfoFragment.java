package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.network.bean.Company;

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
        String name = SPUtils.getInstance().getString(Constant.SP_FACTORY_NAME,"");
        String date = SPUtils.getInstance().getString(Constant.SP_FACTORY_DATE,"");
        String found = SPUtils.getInstance().getString(Constant.SP_FACTORY_FOUND,"");
        String legalPerson = SPUtils.getInstance().getString(Constant.SP_FACTORY_LEGAL_PERSON,"");
        String empNum = SPUtils.getInstance().getString(Constant.SP_FACTORY_EMP_NUM,"");
        mTVFacName.setText(String.format(getResources().getString(R.string.fac_name_f),TextUtils.isEmpty(name)? "--":name));
        mTVBuildTime.setText(String.format(getResources().getString(R.string.build_time_f),TextUtils.isEmpty(date)? "--":date));
        mTVFound.setText(String.format(getResources().getString(R.string.found_f),TextUtils.isEmpty(found)? "--":found));
        mTVLeader.setText(String.format(getResources().getString(R.string.leader_f),TextUtils.isEmpty(legalPerson)? "--":legalPerson));
        mTVEmp.setText(String.format(getResources().getString(R.string.emp_f),TextUtils.isEmpty(empNum)? "--":empNum));
    }
}
