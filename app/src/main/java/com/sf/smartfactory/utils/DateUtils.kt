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
        var strFormat = SimpleDateFormat("yyyy-MM-dd");
        var todayStart:String = strFormat.format(Date());
        return  TimeUtils.getMillis(todayStart,strFormat,0,0);
    }

    /**
     * 获取今天工作开始日期
     */
    fun getWorkStart():Long{
        val todayStart = getTodayStart();
        return TimeUtils.getMillis(todayStart,7 * 60 * 60,1000);
    }

    /**
     * 获取今天结束日期
     */
    fun getTodayEnd() :Long{
        val result = TimeUtils.getMillis(getTodayStart(),24 * 60 * 60,1000) -1;
        return result;
    }

    /**
     * long 转日期
     */
    fun getDate(time: Long):String{
        var simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
        return simpleDateFormat.format(time)
    }

    /**
     * yyyyMMdd 转long
     */
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

    /**
     * 判断是否是今日
     */
    fun isToday(date: Date):Boolean{
        val sdf = SimpleDateFormat("yyyyMMdd")
        val today = Date()
        val sToday = sdf.format(today)
        val day = sdf.format(date)
        return  sToday.contentEquals(day)
    }

    fun getTimeSpan(mills:Long):String{
        if(mills <=0){
            return "00:00:00";
        }
        val h = mills/(60 * 60 * 1000)
        val left = mills%(60 * 60 *1000)
        val m = left/(60 * 1000)
        val s = (left%(60 * 1000))/1000
        var result:StringBuilder = StringBuilder();
        if(h >9){
            result.append(h)
        }else{
            result.append(0).append(h)
        }
        result.append(":")
        if(m>9){
            result.append(m)
        }else{
            result.append(0).append(m)
        }
        result.append(":")
        if(s >9){
            result.append(s)
        }else{
            result.append(0).append(s)
        }
        return result.toString()
    }

}