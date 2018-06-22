package com.sf.smartfactory.network.subscriber;

import com.blankj.utilcode.util.SPUtils;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.response.LoginResponse;

/**
 * @author: winton
 * @time: 2018/4/1 14:49
 * @package: com.sf.smartfactory.network.subscriber
 * @project: SmartFactory
 * @mail:
 * @describe: 登录的订阅者
 */
public class LoginBaseSubscriber extends BaseSubscriber<LoginResponse>{

    @Override
    public void success(LoginResponse loginResponse) {
        SPUtils.getInstance().put(Constant.SP_TOKEN,loginResponse.getToken());
        SPUtils.getInstance().put(Constant.SP_USER_NAME,loginResponse.getUser().getName());
        SPUtils.getInstance().put(Constant.SP_USER_GENDER,loginResponse.getUser().getGender());
        SPUtils.getInstance().put(Constant.SP_USER_PHONE,loginResponse.getUser().getPhone());
        SPUtils.getInstance().put(Constant.SP_USER_USERNAME,loginResponse.getUser().getUsername());
    }

    @Override
    public void failed(Throwable e) {

    }
}
