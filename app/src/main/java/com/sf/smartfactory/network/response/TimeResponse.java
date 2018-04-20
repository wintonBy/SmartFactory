package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.ParamsBean;

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
        private Summary summary;
        private List<Device> deviceValues;

        public Summary getSummary() {
            return summary;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

        public List<Device> getDeviceValues() {
            return deviceValues;
        }

        public void setDeviceValues(List<Device> deviceValues) {
            this.deviceValues = deviceValues;
        }
    }



    public static class Summary implements Serializable{
        private long offline;
        private long idle;
        private long emergency;
        private long working;
        private long collect_err;
        private long pause;
        private long editing;
        private long overhaul;

        public long getOffline() {
            return offline;
        }

        public void setOffline(long offline) {
            this.offline = offline;
        }

        public long getIdle() {
            return idle;
        }

        public void setIdle(long idle) {
            this.idle = idle;
        }

        public long getEmergency() {
            return emergency;
        }

        public void setEmergency(long emergency) {
            this.emergency = emergency;
        }

        public long getWorking() {
            return working;
        }

        public void setWorking(long working) {
            this.working = working;
        }

        public long getCollect_err() {
            return collect_err;
        }

        public void setCollect_err(long collect_err) {
            this.collect_err = collect_err;
        }

        public long getPause() {
            return pause;
        }

        public void setPause(long pause) {
            this.pause = pause;
        }

        public long getEditing() {
            return editing;
        }

        public void setEditing(long editing) {
            this.editing = editing;
        }

        public long getOverhaul() {
            return overhaul;
        }

        public void setOverhaul(long overhaul) {
            this.overhaul = overhaul;
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
