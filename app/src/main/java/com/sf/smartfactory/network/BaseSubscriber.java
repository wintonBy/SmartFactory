package com.sf.smartfactory.network;

import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.network.response.BaseResponse;
import com.wasu.iutils.LogUtils;

import rx.Subscriber;

/**
 * Created by winton on 2017/6/25.
 */

public class BaseSubscriber<T extends BaseResponse> extends Subscriber<T> {


    @Override
    public void onStart() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        LogUtils.e(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        if(t == null){
            onError(new IllegalStateException("null response"));
            return;
        }
        if(!t.isSuccess() ){
            if(Constant.TOKEN_ERROR.equals(t.getMessage()) || Constant.LOGIN_EXPIRATION.equals(t.getMessage())){
                MyApplication.INSTANCE.toLogin();
                return;
            }
        }
    }

}
