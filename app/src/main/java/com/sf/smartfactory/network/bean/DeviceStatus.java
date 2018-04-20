package com.sf.smartfactory.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/1 15:13
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 设备状态
 */
public class DeviceStatus implements Serializable {
    public final static String OFFLINE = "offline";
    public final static String WORKING = "working";
    public final static String EDITING = "editing";
    public final static String PAUSE = "pause";
    public final static String IDLE = "idle";


    private int id;
    private Device device;
    private String dt;
    private String status;
    private long duration;
    private ExtendBean extend;
    private String createDt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public ExtendBean getExtend() {
        return extend;
    }

    public void setExtend(ExtendBean extend) {
        this.extend = extend;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public static class ExtendBean {
        /**
         * params : {"axis_rate":0,"feed_rate":0}
         * quantity : {"add":0,"sum":814,"currentSum":180}
         * currentAlarm : []
         * deviceValueId : 77414
         */

        private ParamsBean params;
        private Quantity quantity;
        private int deviceValueId;
        private List<Alarm> currentAlarm;

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public Quantity getQuantity() {
            return quantity;
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public int getDeviceValueId() {
            return deviceValueId;
        }

        public void setDeviceValueId(int deviceValueId) {
            this.deviceValueId = deviceValueId;
        }

        public List<Alarm> getCurrentAlarm() {
            return currentAlarm;
        }

        public void setCurrentAlarm(List<Alarm> currentAlarm) {
            this.currentAlarm = currentAlarm;
        }

    }
}
