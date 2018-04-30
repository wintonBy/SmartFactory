package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/4/1 16:28
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe:
 */
public class Alarm implements Serializable {
    private String no;
    private String msg;
    private String type;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "no='" + no + '\'' +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
