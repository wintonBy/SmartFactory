package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.ParamsBean;
import com.sf.smartfactory.network.bean.RunTimeSummary;
import com.sf.smartfactory.network.bean.Status;

import java.io.Serializable;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/8 20:45
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: time接口响应
 */
public class TimeResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private RunTimeSummary summary;
        private List<Device> deviceValues;
        private List<Status> list;


        public RunTimeSummary getSummary() {
            return summary;
        }

        public void setSummary(RunTimeSummary summary) {
            this.summary = summary;
        }

        public List<Device> getDeviceValues() {
            return deviceValues;
        }

        public void setDeviceValues(List<Device> deviceValues) {
            this.deviceValues = deviceValues;
        }

        public List<Status> getList() {
            return list;
        }

        public void setList(List<Status> list) {
            this.list = list;
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

        public static class DataBean implements Serializable{
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
