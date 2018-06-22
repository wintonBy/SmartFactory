package com.sf.smartfactory.network.bean;

import com.sf.smartfactory.mvp.IModel;

/**
 * @author: winton
 * @time: 2018/6/19 13:55
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 工厂信息
 */
public class FactoryInfo implements IModel {

    private int id;
    private String name;
    private String workshopImgApp;
    private Company company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkshopImgApp() {
        return workshopImgApp;
    }

    public void setWorkshopImgApp(String workshopImgApp) {
        this.workshopImgApp = workshopImgApp;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
