package com.missu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by alimj on 10/13/2016.
 */

public class ChangeColorAndIcon extends View {

    /**
     * 自定义属性的三个变量
     */
    private int mColor=0xFF45C01A;  //默认的绿色
    private Bitmap mIconBitmap;
    private String mText="微信";
    private int mTextSize= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics());

    /**
     * 内存绘图用的变量
     */
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mAlpha;

    private Rect IconRect;
    private Rect mTextBound;
    private Paint mTextPaint;


    public ChangeColorAndIcon(Context context) {
        this(context,null);
    }
    public ChangeColorAndIcon(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChangeColorAndIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a =context.obtainStyledAttributes(attrs,R.styleable.ChangeColorAndIcon);

        /**
         * 循环获取的每一个资源，获取各个属性
         */
        int n = a.getIndexCount();
        for(int i=0;i<n;i++){

            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.ChangeColorAndIcon_icon://获取icon属性
                   BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorAndIcon_color://获取颜色属性
                    mColor=a.getColor(attr,0xFF45C01A);
                    break;
                case R.styleable.ChangeColorAndIcon_text: //获取标题属性
                    mText=a.getString(attr);
                    break;
                case R.styleable.ChangeColorAndIcon_text_size://获取标题大小属性
                    mTextSize=(int) a.getDimension(attr,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));
                    break;


            }
        }
        a.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0Xff555555);

        mTextPaint.getTextBounds(mText,0,mText.length(),mTextBound);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconwidth = Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-mTextBound.height());

        int left = getMeasuredWidth()/2 - iconwidth/2;
        int top = getMeasuredHeight()/2-((mTextBound.height() +iconwidth)/2);
        IconRect = new Rect(left,top,left+iconwidth,top+iconwidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap,null,IconRect,null);
        int alpha = (int )Math.ceil(255*mAlpha);
        //准备bitmap， setAlpha，纯色，xfermode，图标
        setupTagetBitmap(alpha);

        drawSourceText(canvas,alpha);
        drawTarget(canvas,alpha);

        canvas.drawBitmap(mBitmap,0,0,null);

    }

    /*
    绘画图标
     */
    private void drawTarget(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth()/2- mTextBound.width()/2;
        int y= IconRect.bottom+mTextBound.height();
        canvas.drawText(mText,x,y,mTextPaint);

    }
    /*
    绘画文字
     */

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255-alpha);
        int x = getMeasuredWidth()/2- mTextBound.width()/2;
        int y= IconRect.bottom+mTextBound.height();
        canvas.drawText(mText,x,y,mTextPaint);

    }

    /*
    更改字体颜色
     */
    private void setupTagetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas =new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(IconRect,mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap,null,IconRect,mPaint);
        }

        public void setIconAlpha(float alpha){
            this.mAlpha = alpha;
            invalidateView();
        }

    /*
    重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper()==Looper.myLooper()){
            invalidate();
        }else {
            postInvalidate();
        }
    }
}

