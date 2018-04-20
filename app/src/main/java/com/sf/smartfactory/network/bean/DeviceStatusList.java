package com.sf.smartfactory.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/3/30 16:43
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 设备列表
 */
public class DeviceStatusList implements Serializable{

    private List<DeviceStatus> list;

    public List<DeviceStatus> getList() {
        return list;
    }

    public void setList(List<DeviceStatus> list) {
        this.list = list;
    }
}
