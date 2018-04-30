package com.sf.smartfactory.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.DeviceStatus;
import com.sf.smartfactory.network.bean.Status;
import com.blankj.utilcode.util.LogUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/26 20:14
 * @package: com.sf.smartfactory.view
 * @project: SmartFactory
 * @mail:
 * @describe: 设备时间状态View
 */
public class DeviceTimeStateView extends View {

    private List<Status> mData;

    private final int offlineColor = ContextCompat.getColor(getContext(), R.color.offline_color);
    private final int idleColor = ContextCompat.getColor(getContext(), R.color.idle_color);
    private final int pauseColor = ContextCompat.getColor(getContext(), R.color.pause_color);
    private final int emergencyColor = ContextCompat.getColor(getContext(), R.color.emergency_color);
    private final int editingColor = ContextCompat.getColor(getContext(), R.color.editing_color);
    private final int errorColor = ContextCompat.getColor(getContext(), R.color.collect_error_color);
    private final int overhaulColor = ContextCompat.getColor(getContext(), R.color.overhaul_color);
    private final int workingColor = ContextCompat.getColor(getContext(), R.color.working_color);

    private int mWidth;
    private int mHeight;
    private int contentWidth;
    private int contentHeight;
    private int statusLineHeight = 100;
    private int timeLineHeight = 8;
    private int textLineHeight = 30;
    private final int scaleNum = 4;
    private Paint mPaint;
    private int mPaintColor;
    private int paddingLeft,paddingRight,paddingTop,paddingBottom;
    private long allCount;
    private float lastX;


    private final int DEFAULT_SIZE = 100;

    public DeviceTimeStateView(Context context) {
        this(context,null);
    }

    public DeviceTimeStateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DeviceTimeStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0.1f);
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
        lastX = paddingLeft;
    }

    public void setData(List<Status> data){
        mData = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mData != null){
            lastX = paddingLeft;
            computeAllCount();
            for(int i=0;i<mData.size();i++){
                drawStatus(canvas,mData.get(i));
            }
            drawTimeLine(canvas);
            drawScale(canvas);
            drawText(canvas);
        }
    }

    /**
     * 绘制状态
     * @param canvas
     * @param status
     */
    private void drawStatus(Canvas canvas,Status status){
        mPaintColor = getPaintColor(status.getStatus());
        mPaint.setColor(mPaintColor);
        float width = getStatusWidth(allCount,status.getDuration(),contentWidth);
        RectF rect = new RectF();
        rect.set(lastX,paddingTop,lastX+width,paddingTop+statusLineHeight);
        if(lastX+width > mWidth-paddingRight){
            LogUtils.d("超过了"+lastX+width);
        }
        canvas.drawRect(rect,mPaint);
        lastX = lastX+width+0.000001f;
    }

    /**
     * 绘制时间刻度
     * @param canvas
     */
    private void drawTimeLine(Canvas canvas){
        mPaintColor = ContextCompat.getColor(getContext(),R.color.colorPrimary);
        mPaint.setColor(mPaintColor);
        Rect rect = new Rect();
        rect.set(paddingLeft,paddingTop+statusLineHeight,mWidth-paddingRight,paddingTop+statusLineHeight+timeLineHeight);
        canvas.drawRect(rect,mPaint);
    }

    /**
     * 绘制刻度
     * @param canvas
     */
    private void drawScale(Canvas canvas){
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(textLineHeight);
        int stepWidth = contentWidth / scaleNum;
        int scaleWidth = 5;
        int scaleHeight = 15;
        float top = paddingTop+statusLineHeight+timeLineHeight;
        float bottom = top+ scaleHeight;

        for(int i=0;i<=scaleNum;i++){
            RectF rect = new RectF();
            float left = paddingLeft - scaleWidth/2 + stepWidth * i;
            if(i == 0){
                left = left + scaleWidth/2;
            }
            float right = left + scaleWidth;
            if(i == scaleNum ){
                right = right-scaleWidth/2;
                left = left - scaleWidth/2;
            }
            rect.set(left,top,right,bottom);
            canvas.drawRect(rect,mPaint);
            canvas.drawText("16:00",left-30,bottom+20,mPaint);
        }

    }

    /**
     * 绘制下方字体
     * @param canvas
     */
    private void drawText(Canvas canvas){
        String start = mData.get(0).getDt();
//        canvas.drawText(start,);

    }

    /**
     * 获取画笔的颜色
     * @param status
     * @return
     */
    private int getPaintColor(String status){
        int result = mPaintColor;
        switch (status){
            case "working":
                result = workingColor;
                break;
            case "idle":
                result = idleColor;
                break;
            case "pause":
                result = pauseColor;
                break;
            case "emergency":
                result = emergencyColor;
                break;
            case "editing":
                result = editingColor;
                break;
            case "overhaul":
                result = overhaulColor;
                break;
            case "collect_error":
                result = errorColor;
                break;
            case "offline":
            case"close":
                result = offlineColor;
                break;
        }
        return result;
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
        contentWidth = getSize(widthSize,widthMode) - paddingLeft - paddingRight;
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

    /**
     *
     */
    private void computeAllCount(){
        allCount = 0;
        if(mData == null){
            allCount = 0;
            return;
        }
        for(int i=0;i<mData.size();i++){
            allCount += mData.get(i).getDuration();
        }
    }

    /**
     * 计算需要绘制的状态的宽度
     * @param allCount
     * @param duration
     * @param canDrawWidth
     * @return
     */
    private float getStatusWidth(long allCount,long duration,int canDrawWidth){
        float result = 0;
        if(allCount == 0){
            LogUtils.eTag("DeviceTimeStateView","总长度为 0");
            return result;
        }
        double percent = (double)duration / (double)allCount;
        result =(float) (percent * canDrawWidth);
        return result;
    }
}
