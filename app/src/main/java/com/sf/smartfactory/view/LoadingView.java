package com.sf.smartfactory.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.R;
import android.view.animation.LinearInterpolator;

/**
 * Created by winton on 2017/7/13.
 */

public class LoadingView extends View {

    public static final int DEFAULT_SIZE = 150;
    private static final int START_RADIUS = 50; //开始时半径
    private static final int CIRCLE_NUMBER = 4; //小球数量
    private static final int SMALL_CIRCLE_RADIUS = 10;

    private int centerX;
    private int centerY;

    private int[] pointColors =new int[]{Color.RED ,Color.BLUE,Color.GRAY,Color.YELLOW,Color.MAGENTA};
    private Paint mPaint;
    private ValueAnimator valueAnimator ;
    private float currentAngle;
    private int currentRadius;
    private SplashState mState;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mState == null){
            mState = new RotateState();
        }
        mState.doDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(DEFAULT_SIZE,DEFAULT_SIZE);
        centerX = getMeasuredWidth()/2;
        centerY = getMeasuredHeight()/2;
    }

    /*一阶段旋转*/
    private class RotateState implements SplashState{
        private  int ANIMATOR_DURATION = 7500; //动画持续时间
        public RotateState() {
            currentRadius = START_RADIUS;
            valueAnimator = ValueAnimator.ofFloat(0,(float)Math.PI*2);
            valueAnimator.setDuration(ANIMATOR_DURATION);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float)valueAnimator.getAnimatedValue();
                    currentAngle = (float)(360/2*Math.PI) *value;
                }
            });
            valueAnimator.start();
        }
        @Override
        public void doDraw(Canvas mCanvas) {
            drawCircle(mCanvas);
            invalidate();
        }
    }
    /**
     * 画小圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        for(int i=0;i<CIRCLE_NUMBER ;i++){
            float angle = (360/CIRCLE_NUMBER) * i + currentAngle ;
            float x = (float)(centerX + Math.cos(angle*Math.PI/180)*currentRadius);
            float y = (float)(centerY + Math.sin(angle*Math.PI/180)*currentRadius);
            mPaint.setColor(pointColors[i]);
            canvas.drawCircle(x,y,SMALL_CIRCLE_RADIUS,mPaint);
        }
    }

    /**
     * 画背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas){
        canvas.drawARGB(255,255,255,255);
    }


    private interface SplashState {

        void doDraw(Canvas canvas);

    }

}
