package com.sf.smartfactory.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.ui.fragment.BaseFragment;
import com.sf.smartfactory.utils.DateUtils;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/8/11 12:02
 * @package: com.sf.smartfactory.view
 * @project: Factory
 * @mail:
 * @describe: 日期选择对话框
 */
public class DatePickDialog extends BaseFragment {

    @BindView(R.id.calendarView)
    CalendarPickerView mCalendarView;

    @BindView(R.id.bt_choose)
    Button btChoose;

    private  DataPickerDecorator dataPickerDecorator;
    private String[] newShortWeekdays = {"","日","一","二","三","四","五","六"};

    private long start;
    private long end;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_calendar_pop,container,false);
        ButterKnife.bind(this,view);
        initCalendarView();
        return view;
    }

    private void initCalendarView(){
        dataPickerDecorator = new DataPickerDecorator(getContext());
        List<CalendarCellDecorator> d = new ArrayList<>();
        d.add(dataPickerDecorator);
        mCalendarView.setCustomDayView(new DayViewAdapter());
        mCalendarView.setDecorators(d);
        mCalendarView.setDateTypeface(Typeface.DEFAULT);
    }

    @OnClick(R.id.bt_choose)
    public void clickConfirm(){
        List<Date> dates = mCalendarView.getSelectedDates();
        Date stDate = dates.get(0);
        start = DateUtils.INSTANCE.getDayStart(stDate);
        Date stEnd;
        if(dates.size() == 1){
            stEnd = dates.get(0);
        }else {
            stEnd = dates.get(dates.size() -1);
        }
        if(DateUtils.INSTANCE.isToday(stEnd)){
            stEnd = new Date();
            end = stEnd.getTime();
        }else {
            end = DateUtils.INSTANCE.getDayEnd(stEnd);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    public void updateTime(long start,long end){
        this.start = start;
        this.end = end;
    }

    private void initView(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-10);
        Date minDate = calendar.getTime();
        calendar.add(Calendar.YEAR,10);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date maxDate = calendar.getTime();

        mCalendarView.init(minDate,maxDate)
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .setShortWeekdays(newShortWeekdays)
                .withSelectedDates(initRange());

        mCalendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                LogUtils.d(mCalendarView.getSelectedDates().size());
                LogUtils.d(date);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private List<Date> initRange(){
        Date sDate = new Date(start);
        Date eDate = new Date(end);
        List<Date> range = new ArrayList<>(2);
        range.add(sDate);
        range.add(eDate);
        return range;
    }

}
