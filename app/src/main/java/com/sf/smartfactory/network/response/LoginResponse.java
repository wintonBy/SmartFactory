package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.User;

/**
 * @author: winton
 * @time: 2018/3/28 18:55
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 登录响应类
 */
public class LoginResponse extends BaseResponse{
    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
