package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.ParamsBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/23 21:46
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 设备倍率响应
 */
public class DeviceRateResponse extends BaseResponse {
    private DataBean data;

    public DataBean getData() {
        return data;
    }
    public static class DataBean{
        private List<TimeResponse.Device> deviceValues;

        public List<TimeResponse.Device> getDeviceValues() {
            return deviceValues;
        }

        public void setDeviceValues(List<TimeResponse.Device> deviceValues) {
            this.deviceValues = deviceValues;
        }
    }

    public static class Device implements Serializable{
        private int id;
        private String createDt;
        private DataBean data;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateDt() {
            return createDt;
        }

        public void setCreateDt(String createDt) {
            this.createDt = createDt;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            private ParamsBean params;

            public ParamsBean getParams() {
                return params;
            }

            public void setParams(ParamsBean params) {
                this.params = params;
            }
        }
    }
}
