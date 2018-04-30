package com.sf.smartfactory.constant;


import com.blankj.utilcode.util.SPUtils;

/**
 * @author: winton
 * @time: 2018/3/30 17:01
 * @package: com.sf.smartfactory.constant
 * @project: SmartFactory
 * @mail:
 * @describe: 全局的
 */
public final class GlobalSession {

    public static String getToken(){
        return SPUtils.getInstance().getString(Constant.SP_TOKEN);
    }
}
