package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.DeviceClock;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/5/9 20:44
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class DeviceClockResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private List<DeviceClock> clocks;

        public List<DeviceClock> getClocks() {
            return clocks;
        }

        public void setClocks(List<DeviceClock> clocks) {
            this.clocks = clocks;
        }
    }
}
