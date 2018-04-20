package com.sf.smartfactory.contract;

/**
 * @author: winton
 * @time: 2018/3/22 20:10
 * @package: com.sf.smartfactory.contract
 * @project: SmartFactory
 * @mail:
 * @describe: 登录页面接口定义
 */
public interface LoginContract {

    interface  View{
        /**
         * 控制Loading的显示
         * @param show
         */
        void showLoading(boolean show);

        /**
         * 登录成功之后View要做的事情
         */
        void doLoginSuccess();

        /**
         * 获取用户名
         * @return
         */
        String getUsername();

        /**
         * 获取密码
         * @return
         */
        String getPassword();

        /**
         * 显示错误信息
         * @param msg
         */
        void showError(String msg);
    }

    interface Presenter{
        /**
         * 登录
         */
        void login();
    }
}
