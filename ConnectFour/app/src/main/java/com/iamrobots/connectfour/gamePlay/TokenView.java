package com.iamrobots.connectfour.gamePlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.iamrobots.connectfour.R;

/**
 * Created by David Lively on 1/25/18.
 * lively@iamrobots.com
 */

public class TokenView extends View {

    private static final int DEFAULT_COLOR = 0xff000000;
    private static final float SMALL_CIRCLE_PADDING = 20.0f;
    private static final float BIG_CIRCLE_PADDING = 4.0f;
    private static final float STROKE_WIDTH = 8.0f;

    private Paint mCircleFillPaint;
    private Paint mCircleStrokePaint;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private int mColor;

    private Paint mTextPaint;
    private int mScoreTextSize;
    private String mScore;
    private Rect mTextBounds;

    private boolean mSelected;

    private void init(AttributeSet attrs) {

        mCircleFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleFillPaint.setStyle(Paint.Style.FILL);
        mCircleStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleStrokePaint.setStyle(Paint.Style.STROKE);
        mCircleStrokePaint.setStrokeWidth(STROKE_WIDTH);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TokenView, 0, 0);

            try {
                mColor = a.getColor(R.styleable.TokenView_color, DEFAULT_COLOR);
                mSelected = a.getBoolean(R.styleable.TokenView_selected, false);
                mScoreTextSize = a.getDimensionPixelSize(R.styleable.TokenView_text_size, 80);
            } finally {
                a.recycle();
            }
        } else {
            mColor = DEFAULT_COLOR;
            mSelected = false;
            mScoreTextSize = 20;
        }

        mCircleFillPaint.setColor(mColor);
        if (mSelected)
            mCircleStrokePaint.setColor(Color.BLACK);
        else
            mCircleStrokePaint.setColor(mColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(Math.round(mScoreTextSize * getResources().getDisplayMetrics().scaledDensity));
        mTextBounds = new Rect();
        mTextPaint.getTextBounds("a", 0, 1, mTextBounds);
        mScore = "";
    }

    public TokenView(Context context) {
        super(context);
        init(null);
    }

    public TokenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TokenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float textXOffset = mCenterX - mTextPaint.measureText(mScore) / 2;
        float textYOffset = mCenterY + mTextBounds.height() / 2;

        if (canvas != null) {
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mCircleFillPaint);
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mCircleStrokePaint);
            canvas.drawText(mScore, textXOffset, textYOffset, mTextPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenterX = (right - left) / 2;
        mCenterY = (bottom - top) / 2;
        mRadius = Math.min(mCenterX, mCenterY) - BIG_CIRCLE_PADDING;

        if (!mSelected) {
            mRadius -= SMALL_CIRCLE_PADDING;
        }
    }

    public void setColor(int color) {
        mColor = color;
        mCircleFillPaint.setColor(mColor);
        invalidate();
    }

    public void selected() {

        if (mSelected) return;
        mSelected = true;

        mCircleStrokePaint.setColor(Color.BLACK);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, SMALL_CIRCLE_PADDING);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRadius = Math.min(mCenterX, mCenterY)  - SMALL_CIRCLE_PADDING + value;
                Log.i("INFO", "animation radius: " + mRadius + " + value: " + value);
                invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRadius = Math.min(mCenterX, mCenterY) - BIG_CIRCLE_PADDING;
                Log.i("INFO", "final radius: " + mRadius);
                invalidate();
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(300);
        animator.start();

    }

    public void unselected() {

        if (!mSelected) return;
        mSelected = false;

        mCircleStrokePaint.setColor(mColor);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, SMALL_CIRCLE_PADDING);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRadius = Math.min(mCenterX, mCenterY) - value;
                invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRadius = Math.min(mCenterX, mCenterY) - SMALL_CIRCLE_PADDING;
                invalidate();
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(200);
        animator.start();

    }

    public void setScore(String score) {
        mScore = score;
        invalidate();
    }
}
