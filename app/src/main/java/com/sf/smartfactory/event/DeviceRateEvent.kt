package com.sf.smartfactory.event

import com.sf.smartfactory.network.response.TimeResponse

/**
 * @author: winton
 * @rate: 2018/4/21 16:44
 * @package: com.sf.smartfactory.event
 * @project: SmartFactory
 * @mail:
 * @describe: 设备倍率刷新事件
 */
class DeviceRateEvent constructor(deviceId:String, isError:Boolean,  devices :List<TimeResponse.Device>?){
    var deviceId = deviceId
    var isError = isError
    var devices = devices;
}