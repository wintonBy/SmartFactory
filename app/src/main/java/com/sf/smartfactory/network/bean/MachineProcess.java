package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/4/4 10:13
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 加工信息
 */
public class MachineProcess implements Serializable{

    private Device device;
    private int sum;
    private Part part;
    private Stuff employee;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
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
