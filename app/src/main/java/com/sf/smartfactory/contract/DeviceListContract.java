package com.sf.smartfactory.contract;

import com.sf.smartfactory.network.bean.DeviceStatus;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/3/29 23:39
 * @package: com.sf.smartfactory.contract
 * @project: SmartFactory
 * @mail:
 * @describe: 设备列表页协议
 */
public interface DeviceListContract {

    interface View{
        /**
         * 设置设备列表数据源
         * @param list
         */
        void showDevicesList(List<DeviceStatus> list);

        /**
         * 显示错误信息
         * @param msg
         */
        void showError(String msg);

        /**
         * 显示loading
         */
        void showLoading();

    }

    interface Presenter{
        /**
         * 获取设备列表
         */
        void loadDeviceList(String type);
    }
}
