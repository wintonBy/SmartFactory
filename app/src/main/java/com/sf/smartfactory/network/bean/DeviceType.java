package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/5/10 20:48
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class DeviceType implements Serializable {
    private String id;
    private String name;
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
