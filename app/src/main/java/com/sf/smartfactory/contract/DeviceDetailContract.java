package com.sf.smartfactory.contract;

import com.sf.smartfactory.network.bean.DeviceStatus;
import com.sf.smartfactory.network.bean.LastStatus;
import com.sf.smartfactory.network.bean.OEE;
import com.sf.smartfactory.network.response.TimeResponse;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/7 23:25
 * @package: com.sf.smartfactory.contract
 * @project: SmartFactory
 * @mail:
 * @describe: 设备详情协议
 */
public interface DeviceDetailContract {

    interface View{
        /**
         * 设置设备信息
         * @param status
         */
        void setDeviceInfo(LastStatus status);

        /**
         * 显示错误
         * @param msg
         */
        void showError(String msg);


    }
    interface Presenter{
        /**
         * 获取最新状态
         * @param deviceId
         */
        void loadLastStatus(String deviceId);

        /**
         * 获取设备运行记录
         * @param deviceId
         */
        void loadRates(String deviceId);

        /**
         * 获取运行时间汇总分析
         * @param deviceId
         */
        void loadTimeSummary(String deviceId);

        /**
         * 获取设备OEE分析
         * @param deviceId
         */
        void loadOEE(String deviceId);

        /**
         * 获取设备运行图
         * @param deviceId
         */
        void loadTimeStatus(String deviceId);

        /**
         * 获取数据总接口
         * @param deviceId
         */
        void loadTime(String deviceId);
    }

}
