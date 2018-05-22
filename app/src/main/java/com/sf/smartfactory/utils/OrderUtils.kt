package com.sf.smartfactory.utils

/**
 * @author: winton
 * @time: 2018/5/19 22:26
 * @package: com.sf.smartfactory.utils
 * @project: SmartFactory
 * @mail:
 * @describe: 订单工具类
 */
object OrderUtils{

    const val  ING = 0;

    const val OVER = 10;

    const val HOLD_UP = 11;

    fun getStatusName(status:Int) :String?{
        if(status==ING){
            return "进行中"
        }
        if(status == OVER){
            return "已完成"
        }
        if(status == HOLD_UP){
            return "挂起"
        }
        return null
    }
}