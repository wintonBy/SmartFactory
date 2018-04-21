package com.sf.smartfactory.event

import com.sf.smartfactory.network.response.TimeResponse

/**
 * @author: winton
 * @time: 2018/4/21 11:40
 * @package: com.sf.smartfactory.event
 * @project: SmartFactory
 * @mail:
 * @describe: 设备运行时间消息
 */
class DeviceTimeEvent constructor(deviceId:String,isError:Boolean,runTimeSummary: TimeResponse.Summary){
    var deviceId = deviceId;
    var isError = isError;
    var runTimeSummary = runTimeSummary;
}