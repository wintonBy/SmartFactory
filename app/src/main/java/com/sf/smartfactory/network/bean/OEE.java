package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/4/9 17:38
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class OEE implements Serializable {
    private double qe = 0;
    private double oee = 0;
    private double ae = 0;
    private double pe = 0;

    public double getQe() {
        return qe;
    }

    public void setQe(double qe) {
        this.qe = qe;
    }

    public double getOee() {
        return oee;
    }

    public void setOee(double oee) {
        this.oee = oee;
    }

    public double getAe() {
        return ae;
    }

    public void setAe(double ae) {
        this.ae = ae;
    }

    public double getPe() {
        return pe;
    }

    public void setPe(double pe) {
        this.pe = pe;
    }
}
