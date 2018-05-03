package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sf.smartfactory.R;
import com.sf.smartfactory.constant.Constant;
import com.blankj.utilcode.util.SPUtils;
import com.sf.smartfactory.ui.activity.CommonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/3/27 17:59
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 用户页
 */
public class UserFragment extends BaseFragment{

    private View rootView;
    @BindView(R.id.tv_name)
    TextView mTVName;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static UserFragment newInstance(Bundle params){
        UserFragment instance = new UserFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_user,null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mTVName.setText(SPUtils.getInstance().getString(Constant.SP_USER_NAME));
    }

    @OnClick(R.id.tv_about)
    public void clickAbout(View view){
        CommonActivity.start(getActivity(),"版本信息",CommonActivity.FT_VERSION_INFO);
    }

    @OnClick(R.id.tv_service)
    public void clickService(View view){
        CommonActivity.start(getActivity(),"服务信息",CommonActivity.FT_DEVELOP_INFO);
    }
}
