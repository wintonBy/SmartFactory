package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/4/24 23:41
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 升级信息
 */
public class UpdateInfo implements Serializable {
    private int force;
    private int id;
    private String url;
    private String info;

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
