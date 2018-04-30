package com.sf.smartfactory.presenter;

import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.contract.LoginContract;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.response.LoginResponse;
import com.sf.smartfactory.network.subscriber.LoginBaseSubscriber;
import com.sf.smartfactory.ui.activity.LoginActivity;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;

import rx.Subscriber;

/**
 * @author: winton
 * @time: 2018/3/22 20:21
 * @package: com.sf.smartfactory.presenter
 * @project: SmartFactory
 * @mail:
 * @describe: 登录Presenter
 */
public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.Presenter {

    @Override
    public void login() {
        final String username = getView().getUsername();
        if(StringUtils.isTrimEmpty(username)){
            getView().showError("用户名不能为空");
            return;
        }
        final String pwd = getView().getPassword();
        if(StringUtils.isTrimEmpty(pwd)){
            getView().showError("密码不能为空");
            return;
        }
        Subscriber mLoginSubscriber = new LoginBaseSubscriber(){
            @Override
            public void onStart() {
                super.onStart();
                getView().showLoading(true);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                getView().showLoading(false);
                super.onNext(loginResponse);
                if(loginResponse.isSuccess()){
                    getView().doLoginSuccess();
                    SPUtils.getInstance().put(Constant.SP_USER_USERNAME,username);
                    SPUtils.getInstance().put(Constant.SP_PASSWORD,pwd);
                }else {
                    getView().showError(loginResponse.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                getView().showLoading(false);
                super.onError(e);
                getView().showError("服务异常");
            }
        };
        if(RegexUtils.isMobileSimple(username)){
            RetrofitClient.getInstance().loginByPhone(username,pwd,mLoginSubscriber);
        }else {
            RetrofitClient.getInstance().loginByUsername(username,pwd,mLoginSubscriber);
        }


    }

}
