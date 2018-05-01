package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/4/24 23:29
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 设备状态
 */
public class Status implements Serializable ,Cloneable{
    private String dt;
    private long duration;
    private String status;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Status copy(Status status){
        try {
            Status result = (Status) status.clone();
            return result;
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return new Status();
    }
}
