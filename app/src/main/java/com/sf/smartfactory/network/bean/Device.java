package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/3/30 14:37
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 设备
 */
public class Device implements Serializable{

    private String id;
    private Object collector;
    private Object name;
    private String type;
    private String createDt;
    private boolean off;
    private ExtendBean extend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCollector() {
        return collector;
    }

    public void setCollector(Object collector) {
        this.collector = collector;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public boolean isOff() {
        return off;
    }

    public void setOff(boolean off) {
        this.off = off;
    }

    public ExtendBean getExtend() {
        return extend;
    }

    public void setExtend(ExtendBean extend) {
        this.extend = extend;
    }

    public static class ExtendBean {
        /**
         * ip : 192.168.0.200
         * mdcMqttId : mdc-cnc-1
         */

        private String ip;
        private String mdcMqttId;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getMdcMqttId() {
            return mdcMqttId;
        }

        public void setMdcMqttId(String mdcMqttId) {
            this.mdcMqttId = mdcMqttId;
        }
    }
}
