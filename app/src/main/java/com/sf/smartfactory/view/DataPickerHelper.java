package com.sf.smartfactory.view;

import android.graphics.Typeface;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/8/15 9:24
 * @package: com.sf.smartfactory.view
 * @project: Factory
 * @mail:
 * @describe: 日期选择帮助类
 */
public class DataPickerHelper {

    private static String[] newShortWeekdays = {"","日","一","二","三","四","五","六"};

    private static DataPickerDecorator dataPickerDecorator;

    private static Date minDate;

    private static Date maxDate;

    private static com.squareup.timessquare.DayViewAdapter dayAdapter;

    static {
        dayAdapter = new DayViewAdapter();

        /**
         * 规定日历范围
         */
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-10);
        minDate = calendar.getTime();
        calendar.add(Calendar.YEAR,10);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        maxDate = calendar.getTime();
    }



    public static class Builder{
        final CalendarPickerView calendar;

        private Date minDate;

        private Date maxDate;

        private com.squareup.timessquare.DayViewAdapter adapter;

        private DataPickerDecorator dataPickerDecorator;

        public Builder(CalendarPickerView calendar){
            this.calendar = calendar;

        }

        public Builder setRange(Date minDate,Date maxDate){
            this.maxDate = maxDate;
            this.minDate = minDate;
            return this;
        }

        public Builder setDecorator(DataPickerDecorator dataPickerDecorator){
            this.dataPickerDecorator = dataPickerDecorator;
            return this;
        }

        public Builder setDayViewAdapter(com.squareup.timessquare.DayViewAdapter adapter){
            this.adapter = adapter;
            return this;
        }

        public CalendarPickerView build(){

            if(dataPickerDecorator == null){
                dataPickerDecorator = new DataPickerDecorator(calendar.getContext());
            }

            if(minDate == null || maxDate == null){
                maxDate = DataPickerHelper.maxDate;
                minDate = DataPickerHelper.minDate;
            }

            if(adapter == null){
                adapter = DataPickerHelper.dayAdapter;
            }
            calendar.setCustomDayView(adapter);
            ArrayList<CalendarCellDecorator> d = new ArrayList<>();
            d.add(dataPickerDecorator);
            calendar.setDecorators(d);
            calendar.init(minDate,maxDate)
                    .setShortWeekdays(DataPickerHelper.newShortWeekdays)
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withSelectedDate(new Date());
            return calendar;
        }


    }


}
