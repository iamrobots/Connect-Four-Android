package com.iamrobots.connectfour.GamePlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.iamrobots.connectfour.R;

/**
 * Created by David Lively on 1/25/18.
 * lively@iamrobots.com
 */

public class TokenView extends View {

    private static final int DEFAULT_COLOR = 0xff000000;
    private static final float PADDING = 8.0f;

    private Paint mCirclePaint;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    private boolean mSelected;

    private void init(AttributeSet attrs) {

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);


        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TokenView, 0, 0);

            try {
                mCirclePaint.setColor(a.getColor(R.styleable.TokenView_color, DEFAULT_COLOR));
                mSelected = a.getBoolean(R.styleable.TokenView_selected, false);
            } finally {
                a.recycle();
            }
        } else {
            mCirclePaint.setColor(DEFAULT_COLOR);
            mSelected = false;
        }
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TokenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (canvas != null) {
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenterX = (right - left) / 2;
        mCenterY = (bottom - top) / 2;
        mRadius = Math.min(mCenterX, mCenterY);

        if (!mSelected) {
            mRadius -= PADDING;
        }
    }

    public void setColor(int color) {
        mCirclePaint.setColor(color);
        invalidate();
    }

    public void selected() {

        if (mSelected) return;

        ValueAnimator animator = ValueAnimator.ofFloat(0f, PADDING);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRadius += value;
                invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRadius = Math.min(mCenterX, mCenterY);
                invalidate();
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(300);
        animator.start();

    }

    public void unselected() {

        if (!mSelected) return;

        ValueAnimator animator = ValueAnimator.ofFloat(0f, PADDING);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRadius -= value;
                invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRadius = Math.min(mCenterX, mCenterY) - PADDING;
                invalidate();
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(200);
        animator.start();

    }
}
