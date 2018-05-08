package com.sf.smartfactory.utils

import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: winton
 * @rate: 2018/4/9 18:33
 * @package: com.sf.smartfactory.utils
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public object DateUtils{

    /**
     * 获取今天的起始long
     */
    fun getTodayStart() :Long{
        var result :Long = 0;
        var strFormat = SimpleDateFormat("yyyy-MM-dd");
        var todayStart:String = strFormat.format(Date());
        result = TimeUtils.getMillis(todayStart,strFormat,0,0);
        return result;
    }

    fun getWorkStart():Long{
        var result :Long = 0;
        var todayStart = getTodayStart();
        result = TimeUtils.getMillis(todayStart,7 * 60 * 60,1000);
        return result;
    }

    fun getTodayEnd() :Long{
        var result:Long = 0;
        result = TimeUtils.getMillis(getTodayStart(),24 * 60 * 60,1000);
        return result;
    }


}