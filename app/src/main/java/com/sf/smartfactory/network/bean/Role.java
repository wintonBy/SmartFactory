package com.sf.smartfactory.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/3/28 18:59
 * @package: com.sf.smartfactory.network.response.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 角色类
 */
public class Role implements Serializable {

    /**
     * id : 0
     * name : all
     * description : null
     * privileges : ["userMng","monitor","productionMng","employeeMng","deviceMng","analysis"]
     */

    private int id;
    private String name;
    private Object description;
    private List<String> privileges;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getDescription() {
        return description;
    }

    public List<String> getPrivileges() {
        return privileges;
    }
}
