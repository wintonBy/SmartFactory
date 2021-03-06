package com.sf.smartfactory.network.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: winton
 * @time: 2018/5/9 20:09
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 加工节拍
 */
public class DeviceClock implements Serializable {

    private Device device;
    private long runHistoryAvg;
    private long runAvg;
    private long runMax;
    private long runMin;

    private long workHistoryAvg;
    private long workAvg;
    private long workMax;
    private long workMin;

    private Part part;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public long getRunAvg() {
        return runAvg;
    }

    public void setRunAvg(long runAvg) {
        this.runAvg = runAvg;
    }

    public long getRunMax() {
        return runMax;
    }

    public void setRunMax(long runMax) {
        this.runMax = runMax;
    }

    public long getRunMin() {
        return runMin;
    }

    public void setRunMin(long runMin) {
        this.runMin = runMin;
    }

    public long getWorkAvg() {
        return workAvg;
    }

    public void setWorkAvg(long workAvg) {
        this.workAvg = workAvg;
    }

    public long getWorkMax() {
        return workMax;
    }

    public void setWorkMax(long workMax) {
        this.workMax = workMax;
    }

    public long getWorkMin() {
        return workMin;
    }

    public void setWorkMin(long workMin) {
        this.workMin = workMin;
    }

    public long getRunHistoryAvg() {
        return runHistoryAvg;
    }

    public void setRunHistoryAvg(long runHistoryAvg) {
        this.runHistoryAvg = runHistoryAvg;
    }

    public long getWorkHistoryAvg() {
        return workHistoryAvg;
    }

    public void setWorkHistoryAvg(long workHistoryAvg) {
        this.workHistoryAvg = workHistoryAvg;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
