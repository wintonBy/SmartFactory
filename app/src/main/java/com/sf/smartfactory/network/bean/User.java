package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/3/28 18:58
 * @package: com.sf.smartfactory.network.response.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 用户类
 */
public class User implements Serializable {

    private int id;
    private Role role;
    private String username;
    private String name;
    private String phone;
    private int gender;
    private Object image;
    private String address;
    private String createDt;
    private int off;

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getGender() {
        return gender;
    }

    public Object getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getCreateDt() {
        return createDt;
    }

    public int getOff() {
        return off;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", image=" + image +
                ", address='" + address + '\'' +
                ", createDt='" + createDt + '\'' +
                ", off=" + off +
                '}';
    }
}
