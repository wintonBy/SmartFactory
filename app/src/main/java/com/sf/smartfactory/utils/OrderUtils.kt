package com.sf.smartfactory.utils

/**
 * @author: winton
 * @time: 2018/5/19 22:26
 * @package: com.sf.smartfactory.utils
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
object OrderUtils{

    fun getStatusName(status:Int) :String?{
        if(status==0){
            return "进行中"
        }
        if(status == 10){
            return "已完成"
        }
        if(status == 11){
            return "挂起"
        }
        return null
    }
}