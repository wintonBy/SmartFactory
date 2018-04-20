package com.sf.smartfactory.presenter;

import android.app.Application;

import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.contract.TestContract;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.response.CategoryResponse;
import com.sf.smartfactory.ui.activity.TestActivity;

import java.util.HashMap;

import okhttp3.ResponseBody;

/**
 * Created by winton on 2017/6/25.
 */

public class TestPresenter extends BasePresenter<TestActivity> {

    private TestContract.View mView;

    public TestPresenter(TestContract.View view){
        mView = view;
    }

    public void testGet(){

    }

}
