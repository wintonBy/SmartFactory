package com.sf.smartfactory.utils

import android.graphics.drawable.Drawable
import com.sf.smartfactory.R

/**
 * @author: winton
 * @time: 2018/4/2 15:30
 * @package: com.sf.smartfactory.utils
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
object DeviceUtils {

    fun getStatusArrName(status:String)  : String?{
        when(status){
            in "offline","close" -> return "离线"
            in "idle" -> return "空闲"
            in "working" -> return "运行"
            in "editing" -> return "设置"
            in "emergency" -> return "急停"
            in "pause" -> return "暂停"
            in "overhaul" -> return "检修"
            in "collect_err" -> return "采集异常"
        }
        return null;
    }

    fun getImageByType(type:String) : String?{
        when(type){
            in "cnc-gsk980td","cnc-gsk980tdb","cnc-gsk980tda","cnc-gsk990ma" ->
                    return "https://factorismart.oss-cn-hangzhou.aliyuncs.com/images/gsk980.png"
            in "cnc-fanuc30i","cnc-fanuc0i" ->
                    return "https://factorismart.oss-cn-hangzhou.aliyuncs.com/images/fanuc0i.png"
            in "cnc-840d" ->
                    return "https://factorismart.oss-cn-hangzhou.aliyuncs.com/images/siemens.png"
            in "cnc-m70" ->
                    return "https://factorismart.oss-cn-hangzhou.aliyuncs.com/images/mitsubishielectric.jpg"
        }
        return null;
    }

    fun getDeviceImgBg(status: String) :Any{
        when(status){
            in "working" -> return R.drawable.device_img_bg;
        }
        return R.drawable.device_img_bg;
    }

    fun isTypeRight( status: String,type :String) :Boolean{
        if(type == "normal"){
            when(status){
                in "idle","working","editing","pause" -> return true
            }
        }
        if(type == "error"){
            when(status){
                in "emergency","overhaul","collect_err" -> return true;
            }
        }
        if(type == "offline"){
            when(status){
                in "offline", "close" -> return true;
            }
        }
        return false;
    }

}