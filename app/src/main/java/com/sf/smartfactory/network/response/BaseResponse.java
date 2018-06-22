package com.sf.smartfactory.network.response;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/3/28 18:49
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 基本返回类
 */
public class BaseResponse implements Serializable {
    public static final int FAILED = 0;
    public static final int SUCCESS = 1;
    /**
     * 登录失效
     */
    public static final int CODE_TIME_OUT = 2;

    public String message;
    public int result;

    public String getMessage() {
        return message;
    }

    public int getResult() {
        return result;
    }

    public boolean isSuccess(){
        if(getResult() == SUCCESS){
            return true;
        }
        return false;
    }

    public boolean isCodeTimeOut(){
        return getResult() == CODE_TIME_OUT;
    }
}
