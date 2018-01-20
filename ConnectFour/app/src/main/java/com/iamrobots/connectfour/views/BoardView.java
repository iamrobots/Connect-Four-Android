package com.iamrobots.connectfour.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.iamrobots.connectfour.R;

/**
 * Created by David Lively on 1/18/18.
 * lively@iamrobots.com
 */

public class BoardView extends View {

    private static final int DEFAULT_ROWS = 6;
    private static final int DEFAULT_COLUMNS = 7;
    private static final int DEFAULT_BOARD_COLOR = Color.parseColor("#3498db");
    private static final int DEFAULT_PLAYER1_COLOR = Color.parseColor("#f1c40f");
    private static final int DEFAULT_PLAYER2_COLOR = Color.parseColor("#e74c3c");

    private float mRadius; // radius
    private int mRows;
    private int mColumns;

    private Bitmap mBoardBitmap;
    private Paint mBoardPaint;
    private Paint mEraser;

    private Paint mFirstPlayerPaint;
    private Paint mSecondPlayerPaint;

    private void init(@Nullable AttributeSet attrs) {

        // Setting up paints
        mBoardPaint = new Paint();
        mBoardPaint.setAntiAlias(true);
        mBoardPaint.setStyle(Paint.Style.FILL);

        mEraser = new Paint();
        mEraser.setAntiAlias(true);
        mEraser.setColor(Color.TRANSPARENT);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mFirstPlayerPaint = new Paint();
        mFirstPlayerPaint.setAntiAlias(true);
        mFirstPlayerPaint.setStyle(Paint.Style.FILL);

        mSecondPlayerPaint = new Paint();
        mSecondPlayerPaint.setAntiAlias(true);
        mSecondPlayerPaint.setStyle(Paint.Style.FILL);


        // Parse attributes from attrs.xml here
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BoardView, 0, 0);
            try {
                mRows = a.getInt(R.styleable.BoardView_rows, DEFAULT_ROWS);
                mColumns = a.getInt(R.styleable.BoardView_columns, DEFAULT_COLUMNS);
                mBoardPaint.setColor(a.getColor(R.styleable.BoardView_board_color, DEFAULT_BOARD_COLOR));
                mFirstPlayerPaint.setColor(a.getColor(R.styleable.BoardView_player1_color, DEFAULT_PLAYER1_COLOR));
                mSecondPlayerPaint.setColor(a.getColor(R.styleable.BoardView_player2_color, DEFAULT_PLAYER2_COLOR));

            } finally {
                a.recycle();
            }
        } else {
            setRowsColumns(DEFAULT_ROWS, DEFAULT_COLUMNS);
            mBoardPaint.setColor(DEFAULT_BOARD_COLOR);
            mFirstPlayerPaint.setColor(DEFAULT_PLAYER1_COLOR);
            mSecondPlayerPaint.setColor(DEFAULT_PLAYER2_COLOR);
        }
    }

    public void setRowsColumns(int rows, int columns) {
        mRows = rows;
        mColumns = columns;
    }

    public void dropBall(int row, int column, int player) {
        Paint paint;

        if (column < 0 || column >= mColumns)
            return;
        if (row < 0 || row >= mRows)
            return;
        if (player > 1 || player < 0)
            return;

        if (player == 1)
            paint = mSecondPlayerPaint;
        else
            paint = mFirstPlayerPaint;

        float posX = mRadius; // + (mRadius) * column;
        float posY = mRadius; // + (mRadius) * (mRows - row);
    }

    public BoardView(Context context) {
        super(context);
        init(null);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBoardBitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        int minh = (MeasureSpec.getSize(w) / mColumns) * mRows + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width = w - (getPaddingLeft() + getPaddingRight());
        int height = h - (getPaddingTop() + getPaddingBottom());
        int circlePadding = 4;
        float posX;
        float posY;

        mRadius = Math.min(width / mColumns, height / mRows) / 2;
        posX = mRadius + ((float) width - (mRadius * mColumns) * 2) / 2;
        posY = mRadius + ((float) height - (mRadius * mRows) * 2) / 2;

        mBoardBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBoardBitmap);
        canvas.drawRect(0f, 0f, (float) width, (float) height, mBoardPaint);

        for (int i = 0; i < mRows; ++i) {
            for (int j = 0; j < mColumns; ++j) {
                canvas.drawCircle(posX, posY, mRadius - circlePadding, mEraser);
                posX += mRadius * 2;
            }
            posY += mRadius * 2;
            posX = mRadius + (width - (mRadius * mColumns) * 2) / 2;
        }

    }
}
