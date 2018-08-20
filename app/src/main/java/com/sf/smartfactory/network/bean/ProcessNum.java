package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/8/8 20:43
 * @package: com.sf.smartfactory.network.bean
 * @project: Factory
 * @mail:
 * @describe: 加工数量
 */
public class ProcessNum implements Serializable {
    private String date;

    private int num;
    private Part part;
    private Stuff employee;

    private Device device;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Stuff getEmployee() {
        return employee;
    }

    public void setEmployee(Stuff employee) {
        this.employee = employee;
    }
}
