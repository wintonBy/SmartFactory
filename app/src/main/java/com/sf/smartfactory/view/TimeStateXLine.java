package com.sf.smartfactory.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.Status;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/28 9:29
 * @package: com.sf.smartfactory.view
 * @project: SmartFactory
 * @mail:
 * @describe: 状态View的时间轴
 */
public class TimeStateXLine extends View {

    private int mPaintColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
    private int mWidth;
    private int mHeight;
    private int contentWidth;
    private int contentHeight;
    private Paint mPaint;
    private int paddingLeft,paddingRight,paddingTop,paddingBottom;
    private long allCount;
    private int lastX;

    private List<Status> mData;

    private final int DEFAULT_SIZE = 100;

    public TimeStateXLine(Context context) {
        this(context,null);
    }

    public TimeStateXLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeStateXLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = getSize(widthSize,widthMode);
        mHeight = getSize(heightSize,heightMode);
        setMeasuredDimension(mWidth,mHeight);
        contentWidth = getSize(widthSize,widthMode) - paddingLeft-paddingRight;
        contentHeight = getSize(heightSize,heightMode) - paddingTop - paddingBottom;
    }

    private int getSize(int size,int mecMode){
        int result = size;
        switch (mecMode){
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result =DEFAULT_SIZE;
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(mData == null){
            return;
        }

    }
}
