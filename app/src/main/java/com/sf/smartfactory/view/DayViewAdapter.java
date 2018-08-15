package com.sf.smartfactory.view;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.sf.smartfactory.R;
import com.squareup.timessquare.CalendarCellView;

/**
 * @author: winton
 * @time: 2018/8/14 18:52
 * @package: com.sf.smartfactory.view
 * @project: Factory
 * @mail:
 * @describe: 日历day
 */
public class DayViewAdapter implements com.squareup.timessquare.DayViewAdapter {

    private FrameLayout.LayoutParams params ;
    public DayViewAdapter(){
        int size  = SizeUtils.dp2px(30);
        params = new FrameLayout.LayoutParams(size,size);
    }

    @Override
    public void makeCellView(CalendarCellView parent) {
        TextView textView = new TextView(
                new ContextThemeWrapper(parent.getContext(), R.style.calendar_day_style));
        textView.setDuplicateParentStateEnabled(true);
        parent.addView(textView,params);
        parent.setDayOfMonthTextView(textView);
    }
}
