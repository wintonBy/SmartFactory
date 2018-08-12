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
        result = TimeUtils.getMillis(getTodayStart(),24 * 60 * 60,1000) -1;
        return result;
    }

    fun getDate(time: Long):String{
        var simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
        return simpleDateFormat.format(time)
    }

    fun getTime(str:String ):Long{
        var simpleDateFormat = SimpleDateFormat("yyyyMMdd")
        var date = simpleDateFormat.parse(str)
        return date.time
    }

    fun getYear(time:Long):Int{
        var simpleDateFormat = SimpleDateFormat("yyyy")
        var year = simpleDateFormat.format(time);
        return  year.toInt()
    }

    fun getMonth(time: Long):Int{
        var simpleDateFormat = SimpleDateFormat("MM")
        var month = simpleDateFormat.format(time);
        return  month.toInt()
    }

    fun getDay(time:Long):Int {
        var simpleDateFormat = SimpleDateFormat("dd")
        var day = simpleDateFormat.format(time);
        return  day.toInt()
    }

    fun getDayStart(date:Date):Long{
        var calendar = Calendar.getInstance()
        calendar.time =  date
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        return calendar.timeInMillis
    }

    fun getDayEnd(date:Date):Long{
        var calendar = Calendar.getInstance()
        calendar.time =  date
        calendar.set(Calendar.HOUR_OF_DAY,23)
        calendar.set(Calendar.MINUTE,59)
        calendar.set(Calendar.SECOND,59)
        calendar.set(Calendar.MILLISECOND,999)
        return calendar.timeInMillis
    }

    fun isToday(date: Date):Boolean{
        var sdf = SimpleDateFormat("yyyyMMdd")
        var today = Date()
        var sToday = sdf.format(today)
        var day = sdf.format(date)
        return  sToday.contentEquals(day)
    }

}