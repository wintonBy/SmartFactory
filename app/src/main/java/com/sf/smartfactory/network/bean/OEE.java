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
    private int qe ;
    private int oee;
    private int ae;
    private int pe;

    public int getQe() {
        return qe;
    }

    public void setQe(int qe) {
        this.qe = qe;
    }

    public int getOee() {
        return oee;
    }

    public void setOee(int oee) {
        this.oee = oee;
    }

    public int getAe() {
        return ae;
    }

    public void setAe(int ae) {
        this.ae = ae;
    }

    public int getPe() {
        return pe;
    }

    public void setPe(int pe) {
        this.pe = pe;
    }
}
