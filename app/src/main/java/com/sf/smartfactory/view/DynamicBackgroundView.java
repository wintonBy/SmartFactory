package com.sf.smartfactory.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.sf.smartfactory.R;

import java.util.HashMap;
import java.util.Random;

/**
 * @author: winton
 * @time: 2017/12/5 11:11
 * @package: com.android.wasu.enjoytv.guide.view
 * @project: wasuenjoytv
 * @mail:
 * @describe: 能够动态画圆圈的View
 */
public class DynamicBackgroundView extends View {
    private final String TAG = DynamicBackgroundView.class.getSimpleName();

    private Paint mPaint;
    private int backgroundColor;
    private int circleColor;
    private float width,height;
    private float mCircleR;
    private int circleNumber;
    private int speed;
    private final String default_dynamic_view_color = "#FF2196F3";
    private HashMap<Integer,PointF> mCircles;
    private HashMap<Integer,PointF> mCirclesDirection;

    public DynamicBackgroundView(Context context) {
        this(context,null);
    }

    public DynamicBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DynamicBackgroundView);
        if(array != null){
            backgroundColor = array.getColor(R.styleable.DynamicBackgroundView_view_bg, backgroundColor);
            circleColor = array.getColor(R.styleable.DynamicBackgroundView_ball_color, circleColor);
            circleNumber = array.getInt(R.styleable.DynamicBackgroundView_ball_number, circleNumber);
            mCircleR = array.getDimension(R.styleable.DynamicBackgroundView_ball_r_size, mCircleR);
            speed = array.getInt(R.styleable.DynamicBackgroundView_ball_speed,speed);
            array.recycle();
        }

    }

    /**
     * 初始化
     */
    private void  init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundColor = Color.parseColor(default_dynamic_view_color);
        circleColor = Color.parseColor("#33FFFFFF");
        circleNumber = 10;
        mCircles = new HashMap<>();
        mCirclesDirection = new HashMap<>();
        mCircleR = 120;
        speed = 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        createPoints(circleNumber);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawCircle(canvas);
        super.onDraw(canvas);
    }

    /**
     * 绘制圆圈
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        initCirclePaint();
        for(int i=0;i<circleNumber ;i++){
            PointF point = mCircles.get(i);
            canvas.drawCircle(point.x,point.y,mCircleR,mPaint);
        }
        updatePointLocation();
        invalidate();
    }

    /**
     * 对point进行合法坚查
     * @param point
     * @return
     */
    private int checkPoint(PointF point){
        if(point.x <= mCircleR ){
            //碰撞到左边
            return 1;
        }
        if(point.x >= width - mCircleR){
            //碰撞到右边
            return 2;
        }
        if(point.y <= mCircleR){
            //碰撞上边
            return 3;
        }
        if(point.y >= height - mCircleR){
            //碰撞到右边
            return 4;
        }
        //未检测到碰撞
        return 0;
    }

    /**
     * 更新页面上点的位置
     */
    private void updatePointLocation(){
        for(int i=0;i<circleNumber ;i++){
            PointF point = mCircles.get(i);
            PointF direction = mCirclesDirection.get(i);
            switch (checkPoint(point)){
                case 1:
                case 2:
                    direction.x = -direction.x;
                    break;
                case 3:
                case 4:
                    direction.y = -direction.y;
                    break;
            }
            point.x += direction.x;
            point.y += direction.y;
            mCircles.put(i,point);
        }
    }


    /**
     * 初始化画圆的笔
     */
    private void initCirclePaint(){
        if(mPaint == null){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(circleColor);
    }

    /**
     * 画页面背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas){
        canvas.drawColor(backgroundColor);
    }

    /**
     * 在View范围内产生指定数量的点
     * @return
     */
    private void createPoints(int circleNumber){
        float startX = mCircleR;
        float endX = width - mCircleR;
        float startY = mCircleR;
        float endY = height - mCircleR;
        Random random = new Random();
        for(int i=0 ;i<circleNumber;i++){
            float x = random.nextFloat()*(endX -startX) + startX;
            float y = random.nextFloat()*(endY - startY) + startY;
            PointF point = new PointF(x,y);
            PointF direction = createPointDirection();
            mCircles.put(i,point);
            mCirclesDirection.put(i,direction);
        }
    }

    /**
     * 产生单个Point
     * @return
     */
    private PointF createPoint(){
        float startX = mCircleR;
        float endX = width - mCircleR;
        float startY = mCircleR;
        float endY = height - mCircleR;
        Random random = new Random();
        float x = random.nextFloat()*(endX -startX) + startX;
        float y = random.nextFloat()*(endY - startY) + startY;
        PointF point = new PointF(x,y);
        return point;
    }

    /**
     * 产生一个随机的方向向量
     * @return
     */
    private PointF createPointDirection(){
        Random random = new Random();
        float angle = random.nextFloat()* (float)(2*Math.PI);
        float dx =(float) Math.sin(angle);
        float dy = (float)Math.cos(angle);
        if(dx == 0){
            dx = 0.1f;
        }
        if(dy == 0){
            dy = 0.1f;
        }
        return new PointF(speed*dx,speed*dy);
    }
    /************************************对外的方法**********************************************/
    /**
     * 设置背景颜色
     * @param backgroundColor
     */
    public void setBackColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    /**
     * 设置小球颜色
     * @param circleColor
     */
    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        invalidate();
    }

    /**
     * 设置小球半径
     * @param mCircleR
     */
    public void setCircleR(float mCircleR) {
        this.mCircleR = mCircleR;
        invalidate();
    }

    /**
     * 设置小球数量
     * @param circleNumber
     */
    public void setCircleNumber(int circleNumber) {
        this.circleNumber = circleNumber;
        createPoints(circleNumber);
        invalidate();
    }

    /**
     * 设置小球运行的速度
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
        invalidate();
    }
}
