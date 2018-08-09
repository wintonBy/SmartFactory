package com.sf.smartfactory.network;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.network.response.BaseResponse;

import rx.Subscriber;

/**
 * Created by winton on 2017/6/25.
 */

public abstract class BaseSubscriber<T extends BaseResponse> extends Subscriber<T> {


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
        failed(e);
    }

    @Override
    public void onNext(T t) {
        if(t == null){
            failed(new Exception("数据异常"));
            return;
        }
        if(Constant.REQUEST_LIMIT.equals(t.getMessage())){
            LogUtils.e("请求超过限制");
            failed(new Exception(t.getMessage()));
            return;
        }

        if(!t.isSuccess() ){
            if(Constant.TOKEN_ERROR.equals(t.getMessage()) || Constant.LOGIN_EXPIRATION.equals(t.getMessage())){
                ToastUtils.showLong("登录失效，重新登录");
                MyApplication.INSTANCE.toLogin();
                return;
            }
            failed(new Exception(t.message));
            return;
        }
        success(t);
    }
    abstract public void success(T t);

    abstract public void failed(Throwable e);

}
