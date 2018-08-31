package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.R;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.contract.LoginContract;
import com.sf.smartfactory.presenter.LoginPresenter;
import com.sf.smartfactory.view.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/3/22 20:10
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 登录页面
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View,View.OnClickListener {
    @BindView(R.id.et_username)
    EditText mETUsername;
    @BindView(R.id.et_password)
    EditText mETPassword;
    @BindView(R.id.bt_login)
    Button mBTLogin;

    private LoadingDialog mLoading;

    /**
     * 启动Login页的方法
     * @param context
     * @param params
     */
    public static void start(Context context, Bundle params){
        if(context == null){
            throw new IllegalArgumentException("context should not null");
        }
        Intent intent = new Intent(context,LoginActivity.class);
        if(params != null){
            intent.putExtras(params);
        }
        context.startActivity(intent);
    }

    @Override
    public void showLoading(boolean show) {
        if(show){
            if(mLoading == null){
                mLoading = new LoadingDialog(this);
            }
            mLoading.show();

        }else {
            if(mLoading != null && mLoading.isShowing()){
                mLoading.dismiss();
            }
        }
    }

    @Override
    public void doLoginSuccess() {
        IndexActivity.start(this,null);
        finish();
    }

    @Override
    public String getUsername() {
        if(mETUsername != null){
            return mETUsername.getText().toString();
        }
        return null;
    }

    @Override
    public String getPassword() {
        if(mETPassword != null){
            return mETPassword.getText().toString();
        }
        return null;
    }

    @Override
    public void showError(String msg) {
        if(StringUtils.isTrimEmpty(msg)){
            return;
        }
        SnackbarUtils.with(getWindow().getDecorView()).setMessage(msg).showError();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBTLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        String userName = SPUtils.getInstance().getString(Constant.SP_USER_USERNAME);
        String password = SPUtils.getInstance().getString(Constant.SP_PASSWORD);
        if(!StringUtils.isTrimEmpty(userName)){
            mETUsername.setText(userName);
            if(!StringUtils.isTrimEmpty(password)){
                mETPassword.setText(password);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                mPresenter.login();
                break;
        }
    }

    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }
}
