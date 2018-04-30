package com.sf.smartfactory.contract;

import com.sf.smartfactory.network.bean.UpdateInfo;

/**
 * @author: winton
 * @time: 2018/3/22 20:34
 * @package: com.sf.smartfactory.contract
 * @project: SmartFactory
 * @mail:
 * @describe: 首页协议
 */
public interface IndexContract {
    interface Presenter{
        /**
         * 版本检测
         */
        void checkVersion();
    }

    interface View{
        /**
         * 显示新版本
         * @param info 新版本信息
         */
        void showNewVersion(UpdateInfo info);
    }
}
