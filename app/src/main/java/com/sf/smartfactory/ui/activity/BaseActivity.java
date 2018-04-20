package com.sf.smartfactory.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.mvp.IView;
import com.sf.smartfactory.presenter.BasePresenter;

import java.lang.ref.WeakReference;

/**
 * Created by winton on 2017/6/22.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;
    protected WeakReference<Activity> mActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.INSTANCE.addActivity(mActivity = new WeakReference<Activity>(this));
        initView();
        mPresenter = loadPresenter();
        initCommonData();
        initListener();
        initData();

    }
    protected abstract void initView();

    protected abstract P loadPresenter();

    /**
     * 初始化页面监听器，由子类重载
     */
    protected void initListener(){

    }

    /**
     * 初始化页面数据，由子类重载
     */
    protected void initData(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
        MyApplication.INSTANCE.removeActivity(mActivity);
    }

    private void initCommonData(){
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }


}
