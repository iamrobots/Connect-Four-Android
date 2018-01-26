package com.iamrobots.connectfour.GamePlay;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.iamrobots.connectfour.R;

/**
 * Created by David Lively on 1/25/18.
 * lively@iamrobots.com
 */

public class TokenView extends View {

    private static final int DEFAULT_COLOR = 0xff000000;

    Paint mCirclePaint;

    private void init(AttributeSet attrs) {

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TokenView, 0, 0);

            try {
                mCirclePaint.setColor(a.getColor(R.styleable.TokenView_color, DEFAULT_COLOR));
            } finally {
                a.recycle();
            }
        } else {
            mCirclePaint.setColor(DEFAULT_COLOR);
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
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mCirclePaint);
        }
    }

    public void setColor(int color) {
        mCirclePaint.setColor(color);
        invalidate();
    }
}
