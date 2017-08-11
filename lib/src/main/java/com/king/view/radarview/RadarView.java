/*
     The MIT License (MIT)
     Copyright (c) 2017 Jenly Yu
     https://github.com/jenly1314
     Permission is hereby granted, free of charge, to any person obtaining
     a copy of this software and associated documentation files
     (the "Software"), to deal in the Software without restriction, including
     without limitation the rights to use, copy, modify, merge, publish,
     distribute, sublicense, and/or sell copies of the Software, and to permit
     persons to whom the Software is furnished to do so, subject to the
     following conditions:
     The above copyright notice and this permission notice shall be included
     in all copies or substantial portions of the Software.
     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
     FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
     DEALINGS IN THE SOFTWARE.
 */
package com.king.view.radarview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

public class RadarView extends View {

    public static final String DEFAULT_FORMAT = "%1$.0f";

    /**
     * 是否是扫描状态
     */
    private boolean mIsScaning = false;
    /**
     * 是否显示雷达界面（有雷达和分数两种界面效果）
     */
    private boolean mIsRadar = false;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 内圆环笔画宽度
     */
    private float mInsideStrokeWidth;
    /**
     * 外圆环笔画宽度
     */
    private float mOutsideStrokeWidth;
    /**
     * 对角线笔画宽度
     */
    private float mLineStrokeWidth;

    /**
     * 圆心坐标
     */
    private float mCircleCenterX,mCircleCenterY;

    /**
     * 外圆半径
     */
    private float mRadius;

    /**
     * 内圆半径
     */
    private float mInsideRadius;
    /**
     * 雷达扫描圆环的颜色
     */
    private int mCircleColor = Color.parseColor("#52fff9");
    /**
     * 对角线的颜色
     */
    private int mLineColor = Color.parseColor("#1ecdf4");

    /**
     * 分数界面外边框的颜色
     */
    private int mSideColor = Color.parseColor("#52fff9");
    /**
     * 分数界面外圆环的颜色
     */
    private int mOutsideBackgroundColor = Color.parseColor("#1bb8f2");
    /**
     * 分数界面内圆环的颜色
     */
    private int mInsideBackgroundColor = Color.WHITE;

    /**
     * 分数文本颜色
     */
    private int mTextColor = Color.parseColor("#01b0f1");
    /**
     * 分数标签文本颜色
     */
    private int mLabelTextColor = Color.parseColor("#01b0f1");

    /**
     * 显示分数文本的字体大小
     */
    private float mTextSize;
    /**
     * 显示分数文本标签的字体大小
     */
    private float mLabelTextSize;

    /**
     * 用于绘制雷达圆环的着色器
     */
    private Shader mCircleShader;
    /**
     * 用于绘制雷达扫描区域的着色器
     */
    private Shader mScanShader;

    private Matrix mMatrix;

    /**
     * 雷达扫描旋转的角度
     */
    private int mRotate;

    /**
     * 是否显示对角线
     */
    private boolean mIsShowLine = true;

    /**
     * 文本里圆心的Y轴偏移量
     */
    private float mTextOffsetY;
    /**
     * 标签里圆心的Y轴偏移量
     */
    private float mLabelTextOffseY;

    private String mFormat = DEFAULT_FORMAT;

    private boolean mIsShowLabel = true;

    private boolean mIsShowText = true;

    /**
     * 是否显示动画(显示分数时)
     */
    private boolean mIsShowAnim = true;

    private String mLabelText;
    /**
     * 分数值
     */
    private float mValue;

    /**
     * 文本
     */
    private String mText;

    /**
     * 显示分数动画持续时间
     */
    private int mDuration = 500;

    /**
     * 扫描延迟时间（扫描旋转隔时间,默认2毫秒旋转一度）
     */
    private int mScanTime = 2;
    /**
     * 最后扫描刷新时间
     */
    private float mLastTime;

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {

        mPaint = new Paint();
        mMatrix = new Matrix();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadarView);

        mTextSize = a.getDimension(R.styleable.RadarView_android_textSize,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,40,getDisplayMetrics()));
        mTextColor = a.getColor(R.styleable.RadarView_android_textColor,mTextColor);
        mText = a.getString(R.styleable.RadarView_android_text);

        mLabelTextSize = a.getDimension(R.styleable.RadarView_labelTextSize,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getDisplayMetrics()));
        mLabelTextColor = a.getColor(R.styleable.RadarView_labelTextColor,mTextColor);
        mLabelText = a.getString(R.styleable.RadarView_labelText);
        mFormat = a.getString(R.styleable.RadarView_format);
        if(TextUtils.isEmpty(mFormat)){
            mFormat = DEFAULT_FORMAT;
        }

        mSideColor = a.getColor(R.styleable.RadarView_sideColor,mSideColor);
        mOutsideBackgroundColor = a.getColor(R.styleable.RadarView_outsideBackgroundColor,mOutsideBackgroundColor);
        mInsideBackgroundColor = a.getColor(R.styleable.RadarView_insideBackgroundColor,mInsideBackgroundColor);

        mDuration = a.getInt(R.styleable.RadarView_duration,mDuration);

        mTextOffsetY = a.getDimension(R.styleable.RadarView_textOffsetY,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,getDisplayMetrics()));
        mLabelTextOffseY = a.getDimension(R.styleable.RadarView_labelTextOffsetY,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,36,getDisplayMetrics()));
        mCircleColor = a.getColor(R.styleable.RadarView_circleColor,mCircleColor);
        mLineColor = a.getColor(R.styleable.RadarView_lineColor,mLineColor);
        mRotate = a.getInt(R.styleable.RadarView_rotate,mRotate);
        mIsShowLine = a.getBoolean(R.styleable.RadarView_showLine,mIsShowLine);
        mIsShowText = a.getBoolean(R.styleable.RadarView_showText,mIsShowText);
        mIsShowLabel = a.getBoolean(R.styleable.RadarView_showLabel,mIsShowLabel);
        mScanTime = a.getInt(R.styleable.RadarView_scanTime,mScanTime);

        mInsideStrokeWidth = a.getDimension(R.styleable.RadarView_insideStrokeWidth,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getDisplayMetrics()));
        mOutsideStrokeWidth = a.getDimension(R.styleable.RadarView_outsideStrokeWidth,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getDisplayMetrics()));
        mLineStrokeWidth = a.getDimension(R.styleable.RadarView_lineStrokeWidth,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,0.3f,getDisplayMetrics()));
        a.recycle();
    }


    private DisplayMetrics getDisplayMetrics(){
        return getResources().getDisplayMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //默认值
        int defaultValue = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getDisplayMetrics());
        //默认宽高
        int defaultWidth = defaultValue + getPaddingLeft() + getPaddingRight();
        int defaultHeight = defaultValue + getPaddingTop() + getPaddingBottom();

        int width = measureHandler(widthMeasureSpec,defaultWidth);
        int height = measureHandler(heightMeasureSpec,defaultHeight);

        setMeasuredDimension(width,height);
        //圆心坐标
        mCircleCenterX = (getWidth() + getPaddingLeft() - getPaddingRight())/ 2.0f;
        mCircleCenterY = (getHeight() + getPaddingTop() - getPaddingBottom())/ 2.0f;

        //外圆半径
        mRadius = (getWidth()- getPaddingLeft() - getPaddingRight() - mOutsideStrokeWidth) / 2.0f;
        //内院的半径 为外援半径的1/3
        mInsideRadius = mRadius / 3;

    }

    private int measureHandler(int measureSpec,int defaultSize){

        int result = defaultSize;
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        if(measureMode == MeasureSpec.EXACTLY){
            result = measureSize;
        }else if(measureMode == MeasureSpec.AT_MOST){
            result = Math.min(defaultSize,measureSize);
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIsRadar){
            drawRadar(canvas);
        }else{
            drawScore(canvas);
        }
    }

    /**
     * 绘制雷达扫描图
     * @param canvas
     */
    private void  drawRadar(Canvas canvas){

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        if(mIsShowLine){//是否绘制对角线和斜对角线
            mPaint.setColor(mLineColor);
            mPaint.setStrokeWidth(mLineStrokeWidth);

            //对角线的半径
            float lineRadius =  mRadius - mOutsideStrokeWidth/2;
            // 根据角度绘制对角线
            float startX, startY, endX, endY;
            double radian;

            // 绘制0°~180°对角线
            canvas.drawLine(mCircleCenterX - lineRadius, mCircleCenterY, mCircleCenterX + lineRadius, mCircleCenterY, mPaint);
            // 绘制90°~270°对角线
            canvas.drawLine(mCircleCenterX, mCircleCenterY - lineRadius, mCircleCenterX, mCircleCenterY + lineRadius, mPaint);


            // 绘制45°~225°对角线
            // 计算开始位置x/y坐标点
            radian = Math.toRadians(45f);
            startX = (float) (mCircleCenterX + lineRadius * Math.cos(radian));
            startY = (float) (mCircleCenterY + lineRadius * Math.sin(radian));
            // 计算结束位置x/y坐标点
            radian = Math.toRadians(45f + 180f);
            endX = (float) (mCircleCenterX + lineRadius * Math.cos(radian));
            endY = (float) (mCircleCenterY + lineRadius * Math.sin(radian));
            canvas.drawLine(startX, startY, endX, endY, mPaint);

            // 绘制135°~315°对角线
            // 计算开始位置x/y坐标点
            radian = Math.toRadians(135f);
            startX = (float) (mCircleCenterX + lineRadius * Math.cos(radian));
            startY = (float) (mCircleCenterY + lineRadius * Math.sin(radian));
            // 计算结束位置x/y坐标点
            radian = Math.toRadians(135f + 180f);
            endX = (float) (mCircleCenterX + lineRadius * Math.cos(radian));
            endY = (float) (mCircleCenterY + lineRadius * Math.sin(radian));
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }
        //为矩阵设置旋转坐标
        mMatrix.setRotate(mRotate, mCircleCenterX, mCircleCenterY);

        //绘制圆环的渐变着色器
        if (mCircleShader == null) {
            mCircleShader = new SweepGradient(mCircleCenterX, mCircleCenterY, Color.TRANSPARENT, mCircleColor);
        }
        mCircleShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mCircleShader);

        mPaint.setStrokeWidth(mInsideStrokeWidth);
        mPaint.setColor(mCircleColor);
        //绘制内框圆
        canvas.drawCircle(mCircleCenterX,mCircleCenterY,mInsideRadius,mPaint);
        canvas.drawCircle(mCircleCenterX,mCircleCenterY,mInsideRadius * 2,mPaint);

        //绘制外框圆
        mPaint.setStrokeWidth(mOutsideStrokeWidth);
        canvas.drawCircle(mCircleCenterX,mCircleCenterY,mRadius,mPaint);

        //绘制圆扫描区域的渐变着色器
        if (mScanShader == null){
            mScanShader = new SweepGradient(mCircleCenterX, mCircleCenterY,new int[] {Color.TRANSPARENT,Color.TRANSPARENT, mCircleColor},null);
        }
        mScanShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mScanShader);
        mPaint.setStyle(Paint.Style.FILL);

        //绘制扫面区域
        float radius = mRadius + mOutsideStrokeWidth/2;
        canvas.drawCircle(mCircleCenterX, mCircleCenterX, radius, mPaint);
    }


    /**
     * 绘制扫描结果分数图
     * @param canvas
     */
    private void drawScore(Canvas canvas){
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(mInsideStrokeWidth);
        mPaint.setColor(mSideColor);

        //绘制外框圆
        canvas.drawCircle(mCircleCenterX,mCircleCenterY,mRadius,mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mOutsideBackgroundColor);
        canvas.drawCircle(mCircleCenterX,mCircleCenterY,mRadius,mPaint);

        //绘制内框圆
        mPaint.setColor(mInsideBackgroundColor);
        canvas.drawCircle(mCircleCenterX,mCircleCenterY,mInsideRadius * 2,mPaint);


        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextAlign(Paint.Align.CENTER);

        if(mIsShowLabel && !TextUtils.isEmpty(mLabelText)){//绘制标签文本逻辑
            mPaint.setColor(mLabelTextColor);
            mPaint.setTextSize(mLabelTextSize);
            float x = mCircleCenterX;
            float y = mCircleCenterY + mLabelTextOffseY;
            canvas.drawText(mLabelText,x,y,mPaint);
        }

        if(mIsShowText && !TextUtils.isEmpty(mText)){//绘制文本逻辑
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(mTextSize);
            mPaint.setFakeBoldText(true);
            float x = mCircleCenterX;
            float y = mCircleCenterY + mTextOffsetY;
            canvas.drawText(mText,x,y,mPaint);
        }

    }

    private String getFormatText(float value,String format){
        if(TextUtils.isEmpty(format)){
            return String.valueOf(value);
        }
        return String.format(format,value);
    }

    public void showText(String text){
        mIsRadar = false;
        setText(text);
    }

    /**
     * 显示分数
     * @param value    目标值（最终分数）
     */
    public void showScore(float value){
        showScore(value,mDuration);
    }

    /**
     * 显示分数
     * @param value    目标值（最终分数）
     * @param duration  从初始值到目标值的动画持续时间
     */
    public void showScore(float value,int duration){
        showScore(0,value,duration);
    }

    /**
     * 显示分数
     * @param from  初始值
     * @param to    目标值（最终分数）
     * @param duration  从初始值到目标值的动画持续时间
     */
    public void showScore(float from,float to,int duration){
        showScore(from,to,duration,mIsShowAnim);
    }

    /**
     * 显示分数
     * @param from  初始值
     * @param to    目标值（最终分数）
     * @param duration  从初始值到目标值的动画持续时间
     * @param isShowAnim 是否显示动画
     */
    public void showScore(float from,float to,int duration,boolean isShowAnim){
        showScore(from,to,duration,mFormat,isShowAnim);
    }

    /**
     * 显示分数
     * @param from  初始值
     * @param to    目标值（最终分数）
     * @param duration  从初始值到目标值的动画持续时间
     * @param format
     * @param isShowAnim 是否显示动画
     */
    public void showScore(float from, float to, int duration, final String format,boolean isShowAnim){
        this.mIsRadar = false;
        this.mDuration = duration;
        this.mFormat = (format == null ? DEFAULT_FORMAT : format);
        this.mIsShowAnim = isShowAnim;
        if(mIsShowAnim){
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from,to);
            valueAnimator.setDuration(duration);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mValue = (float)animation.getAnimatedValue();
                    setText(mValue,format);
                }
            });

            valueAnimator.start();
        }else{
            mValue = to;
            setText(mValue,format);
        }

    }


    public void setText(@StringRes int resId){
        this.mText = getResources().getString(resId);
        if(!mIsRadar){
            invalidate();
        }
    }

    public void setText(String text){
        this.mText = text;
        if(!mIsRadar){
            invalidate();
        }
    }

    /**
     * 设置分数文本
     * @param value
     * @param format
     */
    public void setText(float value,String format){
        this.mValue = value;
        this.mFormat = (TextUtils.isEmpty(format) ? DEFAULT_FORMAT : format);
        this.mText = getFormatText(value,format);
        if(!mIsRadar){
            invalidate();
        }
    }

    public void setLabelText(@StringRes int resId){
        this.mLabelText = getResources().getString(resId);
        if(!mIsRadar){
            invalidate();
        }
    }

    public void setLabelText(String text){
        this.mLabelText = text;
        if(!mIsRadar){
            invalidate();
        }
    }

    /**
     * 开始扫描动画
     */
    public void start(){
        mIsRadar = true;
        mIsScaning = true;
        updateScan();

    }

    public void start(int... colors){
        setScanColor(colors);
        start();
    }

    /**
     * 更新雷达扫描区域
     */
    private void updateScan(){
        float curTime = System.currentTimeMillis();
        if(curTime>=mLastTime + mScanTime && mIsScaning){
            mLastTime = curTime;
            removeCallbacks(mRunnable);
            postDelayed(mRunnable,mScanTime);
        }

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mRotate++;
            if(mRotate>=360){
                mRotate = 0;
            }
            invalidate();
            updateScan();
        }
    };

    /**
     * 停止扫描动画
     */
    public void stop(){
        mIsScaning = false;
    }

    public void setInsideStrokeWidth(float insideStrokeWidth) {
        this.mInsideStrokeWidth = insideStrokeWidth;
    }

    public void setOutsideStrokeWidth(float outsideStrokeWidth) {
        this.mOutsideStrokeWidth = outsideStrokeWidth;
    }

    public void setLineStrokeWidth(float lineStrokeWidth) {
        this.mLineStrokeWidth = lineStrokeWidth;
    }

    public void setCircleColor(int circleColor) {
        this.mCircleColor = circleColor;
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
    }

    public void setSideColor(int sideColor) {
        this.mSideColor = sideColor;
    }

    public void setOutsideColor(int outsideColor) {
        this.mOutsideBackgroundColor = outsideColor;
    }

    public void setInsideColor(int insideColor) {
        this.mInsideBackgroundColor = insideColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public void setLabelTextColor(int labelTextColor) {
        this.mLabelTextColor = labelTextColor;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public void setLabelTextSize(float labelTextSize) {
        this.mLabelTextSize = labelTextSize;
    }

    public void setRotate(int rotate) {
        this.mRotate = rotate;
    }

    public void setShowLine(boolean isShowLine) {
        this.mIsShowLine = isShowLine;
    }

    public void setTextOffsetY(float textOffsetY) {
        this.mTextOffsetY = textOffsetY;
    }

    public void setShowLabel(boolean showLabel) {
        mIsShowLabel = showLabel;
    }

    public void setShowText(boolean showText) {
        mIsShowText = showText;
    }

    public void setShowAnim(boolean isShowAnim) {
        this.mIsShowAnim = isShowAnim;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setScanTime(int scanTime) {
        this.mScanTime = scanTime;
    }

    public boolean isRadar() {
        return mIsRadar;
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public int getLineColor() {
        return mLineColor;
    }

    public int getSideColor() {
        return mSideColor;
    }

    public int getOutsideColor() {
        return mOutsideBackgroundColor;
    }

    public void setRadar(boolean isRadar){
        this.mIsRadar = isRadar;
    }

    public int getInsideColor() {
        return mInsideBackgroundColor;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public int getLabelTextColor() {
        return mLabelTextColor;
    }

    /**
     * 雷达扫描区域的着色器色值
     * @param colors 传多个色值表示渐变
     */
    public void setScanColor(int... colors){
        mScanShader = new SweepGradient(mCircleCenterX, mCircleCenterY,colors,null);
    }

    /**
     * 雷达内外圆环的着色器色值
     * @param colors 传多个色值表示渐变
     */
    public void setCircleColor(int... colors){
        mCircleShader = new SweepGradient(mCircleCenterX, mCircleCenterY,colors,null);
    }
}
