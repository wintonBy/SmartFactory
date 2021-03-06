package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.R;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.response.BaseResponse;
import com.sf.smartfactory.view.DialogEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/5/5 23:52
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 设置页面
 */
public class SettingFragment extends BaseFragment{

    private DialogEx logoutDialog;
    private UpdateFrequencyDialog updateFrequencyDialog;
    @BindView(R.id.tv_update_frequency)
    TextView tvUpdateFrequency;
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
        initData();
        return view;
    }

    @OnClick(R.id.tv_logout)
    public void clickLogout(View view){
        if(logoutDialog == null){
            logoutDialog = new DialogEx(getActivity(),"确认退出当前账号?",R.string.confirm,R.string.cancel){
                @Override
                public void confirm() {
                    doLogout();
                    cancel();
                }
            };
        }
        logoutDialog.show();
    }

    @OnClick(R.id.rl_update_frequency)
    public void clickUpdateFrequency(View view){
        if(updateFrequencyDialog == null){
            updateFrequencyDialog = UpdateFrequencyDialog.createInstance();
            updateFrequencyDialog.setListener(new UpdateFrequencyDialog.OnItemChooseListener() {
                @Override
                public void choose(int sec, String title) {
                    setFrequency(title,sec);
                    updateFrequencyDialog.dismiss();
                }
            });
        }
        updateFrequencyDialog.show(getActivity().getSupportFragmentManager(),"chooseUpdateFrequency");
    }

    /**
     * 设置数据刷新的频率
     * @param title
     * @param sec
     */
    private void setFrequency(String title,int sec){
        Constant.REFRESH_SPACE = sec * 1000;
        tvUpdateFrequency.setText(title);
        SPUtils.getInstance().put(Constant.SP_UPDATE_FREQUENCY_TITLE,title);
        SPUtils.getInstance().put(Constant.SP_UPDATE_FREQUENCY_VALUE,sec * 1000);
    }

    /**
     * 注销登录
     */
    private void doLogout(){
        RetrofitClient.getInstance().logout(new BaseSubscriber<BaseResponse>(){

            @Override
            public void success(BaseResponse baseResponse) {
                cleanUserInfo();
                MyApplication.INSTANCE.toLogin();
            }

            @Override
            public void failed(Throwable e) {
                ToastUtils.showShort(e.getMessage());
            }
        });
    }

    /**
     * 注销登录时清除用户信息
     */
    private void cleanUserInfo(){
        SPUtils.getInstance().remove(Constant.SP_TOKEN);
        SPUtils.getInstance().remove(Constant.SP_USER_NAME);
        SPUtils.getInstance().remove(Constant.SP_USER_GENDER);
        SPUtils.getInstance().remove(Constant.SP_USER_PHONE);
        SPUtils.getInstance().remove(Constant.SP_USER_USERNAME);
        SPUtils.getInstance().remove(Constant.SP_PASSWORD);
        SPUtils.getInstance().put(Constant.SP_IS_FIRST_OPEN,true);
    }

    private void initData(){
        tvUpdateFrequency.setText(SPUtils.getInstance().getString(Constant.SP_UPDATE_FREQUENCY_TITLE,"5秒"));
    }

}
