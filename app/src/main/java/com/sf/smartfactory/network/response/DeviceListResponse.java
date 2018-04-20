package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.DeviceStatusList;

/**
 * @author: winton
 * @time: 2018/3/30 15:39
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 设备列表响应
 */
public class DeviceListResponse extends BaseResponse {
    private DeviceStatusList data;

    public DeviceStatusList getData() {
        return data;
    }
}
