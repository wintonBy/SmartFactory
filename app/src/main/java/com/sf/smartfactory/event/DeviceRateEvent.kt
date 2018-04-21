package com.sf.smartfactory.event

/**
 * @author: winton
 * @time: 2018/4/21 16:44
 * @package: com.sf.smartfactory.event
 * @project: SmartFactory
 * @mail:
 * @describe: 设备倍率刷新事件
 */
class DeviceRateEvent constructor(deviceId:String,isError:Boolean){
    var deviceId = deviceId
    var isError = isError
}