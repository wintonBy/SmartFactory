package com.sf.smartfactory.contract;

import com.sf.smartfactory.mvp.IView;

/**
 * @author: winton
 * @time: 2018/3/22 17:05
 * @package: com.sf.smartfactory.contract
 * @project: SmartFactory
 * @mail:
 * @describe: 启动页协议
 */
public interface SplashContract {
    interface View{
        /**
         * 跳转到下个页面
         */
        void toNext();
        /**
         * 用于显示错误信息
         * @param msg
         */
        void showError(String msg);
    }

    interface Presenter{
        /**
         * 获取工厂信息
         */
        void loadFactoryInfo();
    }

}
