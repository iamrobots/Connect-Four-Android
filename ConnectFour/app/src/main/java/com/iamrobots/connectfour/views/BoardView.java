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

    private int mRows;
    private int mColumns;
    private float mRadius;
    private float[] mPosX;
    private float[] mPosY;

    private Bitmap mBoardBitmap;
    private Paint mBoardPaint;
    private Paint mEraser;

    private Bitmap mBackBoardBitmap;
    private Paint mBackBoardPaint;
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

        mBackBoardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackBoardPaint.setColor(Color.WHITE);
        mBackBoardPaint.setStyle(Paint.Style.FILL);

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
            mRows = DEFAULT_ROWS;
            mColumns = DEFAULT_COLUMNS;
            mBoardPaint.setColor(DEFAULT_BOARD_COLOR);
            mFirstPlayerPaint.setColor(DEFAULT_PLAYER1_COLOR);
            mSecondPlayerPaint.setColor(DEFAULT_PLAYER2_COLOR);
        }

        mPosY = new float[mRows];
        mPosX = new float[mColumns];
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

        canvas.drawBitmap(mBackBoardBitmap, 0, 0, null);
        canvas.drawBitmap(mBoardBitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSize(minw, widthMeasureSpec);

        int minh = ((w / mColumns) * mRows) + getPaddingBottom() + getPaddingTop();
        int h = resolveSize(minh, heightMeasureSpec);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width = w - (getPaddingLeft() + getPaddingRight());
        int height = h - (getPaddingTop() + getPaddingBottom());

        initBoard(width, height);

    }

    private void initBoard(int width, int height) {
        int circlePadding = 4;

        mRadius = Math.min(width / mColumns, height / mRows) / 2;
        mPosX[0] = mRadius + ((float) width - (mRadius * mColumns) * 2) / 2;
        mPosY[mRows - 1] = mRadius + ((float) height - (mRadius * mRows) * 2) / 2;

        for (int i = 1; i < mColumns; i++) {
            mPosX[i] = mPosX[i - 1] + mRadius * 2;
        }

        for (int i = mRows - 1; i > 0; i--) {
            mPosY[i - 1] = mPosY[i] + mRadius * 2;
        }

        mBackBoardBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas backboardCanvas = new Canvas(mBackBoardBitmap);
        backboardCanvas.drawRect(0f, 0f, width, height, mBackBoardPaint);

        mBoardBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas boardCanvas = new Canvas(mBoardBitmap);
        boardCanvas.drawRect(0f, 0f, (float) width, (float) height, mBoardPaint);

        for (int i = 0; i < mRows; ++i) {
            for (int j = 0; j < mColumns; ++j) {
                boardCanvas.drawCircle(mPosX[j], mPosY[i], mRadius - circlePadding, mEraser);
            }
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /*
         * Drops a ball of the players color at the given row and column.
         * TODO: Animate ball drop!
         */
    public void dropBall(int row, int column, int player) {
        Paint paint;

        if (column < 0 || column >= mColumns)
            return;
        if (row < 0 || row >= mRows)
            return;
        if (player > 1 || player < 0)
            return;
        if (mBackBoardBitmap == null)
            return;

        if (player == 1)
            paint = mSecondPlayerPaint;
        else
            paint = mFirstPlayerPaint;


        Canvas canvas = new Canvas(mBackBoardBitmap);
        canvas.drawCircle(mPosX[column], mPosY[row], mRadius, paint);
        invalidate();

    }

    public int getRows() {
        return mRows;
    }

    public int getColumns() {
        return mColumns;
    }

    // TODO: figure out what to do when out of bounds on getRow and getColumn
    public int getRow(float y) {
        if (y < 0 || y > getMeasuredHeight())
            return -1;
        int interval = getMeasuredHeight() / mRows;
        return mRows - ( (int) y / interval) - 1;

    }

    public int getColumn(float x) {
        if (x < 0 || x > getMeasuredWidth())
            return -1;
        int interval = getMeasuredWidth() / mColumns;
        return (int) x / interval;

    }
}
