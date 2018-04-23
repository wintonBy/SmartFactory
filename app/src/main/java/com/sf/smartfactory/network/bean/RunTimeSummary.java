package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/4/23 19:31
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 运行时间汇总
 */
public class RunTimeSummary implements Serializable {
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
