package com.sf.smartfactory.event

import com.sf.smartfactory.network.bean.OEE

/**
 * @author: winton
 * @rate: 2018/4/21 15:01
 * @package: com.sf.smartfactory.event
 * @project: SmartFactory
 * @mail:
 * @describe: 设备OEE事件
 */
class DeviceOeeEvent constructor(deviceId:String,isError:Boolean,oee:OEE?){
    var deviceId =deviceId;
    var isError = isError;
    var oee = oee;
}