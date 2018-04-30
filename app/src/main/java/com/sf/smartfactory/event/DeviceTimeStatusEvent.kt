package com.sf.smartfactory.event

import com.sf.smartfactory.network.bean.Status

/**
 * @author: winton
 * @time: 2018/4/27 20:03
 * @package: com.sf.smartfactory.event
 * @project: SmartFactory
 * @mail:
 * @describe: 设备运行时间状态
 */

class DeviceTimeStatusEvent constructor(deviceId:String,isError:Boolean,statusList :List<Status>?){
    var deviceId = deviceId;
    var isError = isError;
    var statusList = statusList;
}