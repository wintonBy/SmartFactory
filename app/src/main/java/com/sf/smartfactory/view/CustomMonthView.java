//package com.sf.smartfactory.view;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//
//import com.haibin.calendarview.Calendar;
//import com.haibin.calendarview.MonthView;
//
///**
// * @author: winton
// * @time: 2018/8/11 12:55
// * @package: com.sf.smartfactory.view
// * @project: Factory
// * @mail:
// * @describe: 自定义月视图
// */
//public class CustomMonthView extends MonthView {
//
//    private float mRadius = 0f;
//
//    @Override
//    protected void onPreviewHook() {
//        mRadius = Math.min(mItemWidth,mItemHeight)/5 * 2;
//        mSchemePaint.setStyle(Paint.Style.STROKE);
//    }
//
//    public CustomMonthView(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
//        int cx = x + mItemWidth/2;
//        int cy = y + mItemHeight/2;
//        canvas.drawCircle(cx,cy,mRadius,mSelectedPaint);
//        return hasScheme;
//    }
//
//
//    @Override
//    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
//        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
//    }
//
//    @Override
//    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
//        float baselineY = mTextBaseLine + y;
//        int cx = x + mItemWidth / 2;
//        String day = String.valueOf(calendar.getDay());
//        canvas.drawText(day,cx,baselineY,
//                calendar.isCurrentDay()?mCurDayTextPaint
//                        :isSelected?mSelectTextPaint
//                        :calendar.isCurrentMonth()?mCurMonthTextPaint: mOtherMonthTextPaint);
//    }
//}
