package com.sf.smartfactory.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.sf.smartfactory.R;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;

import java.util.Date;

/**
 * @author: winton
 * @time: 2018/8/14 17:22
 * @package: com.sf.smartfactory.view
 * @project: Factory
 * @mail:
 * @describe: 日历控件定义
 */
public class DataPickerDecorator implements CalendarCellDecorator{

    private Drawable normalDraw;
    private Drawable middleDraw;
    private int todayUnSelectColor;
    private int unableColor;
    private int normalColor;


    public DataPickerDecorator(Context mContext){
        normalDraw = ContextCompat.getDrawable(mContext,R.drawable.day_selected);
        middleDraw = ContextCompat.getDrawable(mContext,R.drawable.day_selected_middle);
        todayUnSelectColor = Color.GREEN;
        unableColor = ContextCompat.getColor(mContext,R.color.black30);
        normalColor = ContextCompat.getColor(mContext,R.color.day_text_color);
    }

    @Override
    public void decorate(CalendarCellView cellView, Date date) {
        String data = String.valueOf(cellView.getTag());
        cellView.getDayOfMonthTextView().setTextColor(normalColor);
        if(!cellView.isSelectable()){
            //如果不可选取的日期
            if(cellView.isCurrentMonth()){
                cellView.getDayOfMonthTextView().setTextColor(unableColor);
            }else {
                cellView.getDayOfMonthTextView().setText("");
            }
            return;
        }
//        if(cellView.isSelected()){
//            //被选中的View
//            String location = data.substring(data.indexOf("rangeState") + 11, data.length() - 1);
//            if("FIRST".equals(location)){
//                cellView.getDayOfMonthTextView().setBackground(normalDraw);
//            }else if("LAST".equals(location)){
//                cellView.getDayOfMonthTextView().setBackground(normalDraw);
//            }else if("NONE".equals(location)){
//                cellView.getDayOfMonthTextView().setBackground(normalDraw);
//            }else if("MIDDLE".equals(location)){
//                cellView.getDayOfMonthTextView().setBackground(middleDraw);
//            }
//            if(cellView.isToday()){
//                cellView.getDayOfMonthTextView().setBackground(normalDraw);
//            }
//            return;
//        }
        //未选中的View
        if(cellView.isToday()&& cellView.isCurrentMonth()){
            cellView.getDayOfMonthTextView().setTextColor(todayUnSelectColor);
            return;
        }
    }
}
