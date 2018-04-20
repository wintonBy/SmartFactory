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
         * 设置oee信息
         * @param oeeInfo
         */
        void setOEEInfo(OEE oeeInfo);

        /**
         * 显示错误
         * @param msg
         */
        void showError(String msg);

        /**
         * 展示设备运行状况概要
         * @param summary
         */
        void setRunSummary(TimeResponse.Summary summary);

        /**
         * 显示设备轴吕
         * @param devices
         */
        void setDeviceRate(List<TimeResponse.Device> devices);

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
         * @param start
         * @param end
         */
        void loadTimes(String deviceId,long start,long end);

        /**
         * 获取设备OEE分析
         * @param deviceId
         * @param start
         * @param end
         */
        void loadOEE(String deviceId,long start,long end);
    }

}
