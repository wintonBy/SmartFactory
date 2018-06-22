package com.sf.smartfactory.network.bean;

import com.sf.smartfactory.mvp.IModel;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/6/19 14:00
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 公司信息
 */
public class Company implements Serializable {

    private String date;
    private String fund;
    private String name;
    private String empnums;
    private String legalperson;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpnums() {
        return empnums;
    }

    public void setEmpnums(String empnums) {
        this.empnums = empnums;
    }

    public String getLegalperson() {
        return legalperson;
    }

    public void setLegalperson(String legalperson) {
        this.legalperson = legalperson;
    }
}
