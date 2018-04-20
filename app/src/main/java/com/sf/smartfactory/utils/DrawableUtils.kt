package com.sf.smartfactory.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat

/**
 * @author: winton
 * @time: 2018/4/20 23:14
 * @package: com.sf.smartfactory.utils
 * @project: SmartFactory
 * @mail:
 * @describe: drawable 工具类
 */
object DrawableUtils{
    /**
     * 变换drawable文件的背景颜色
     */
    fun changeDrawableColor(context:Context,drawId:Int,colorId:Int):Drawable{
        var drawable:Drawable= ContextCompat.getDrawable(context,drawId)!!.mutate()
        var drawWrapper = DrawableCompat.wrap(drawable)
        var colorStateList:ColorStateList = ColorStateList.valueOf(ContextCompat.getColor(context,colorId))
        DrawableCompat.setTintList(drawWrapper,colorStateList)
        return drawWrapper
    }
}