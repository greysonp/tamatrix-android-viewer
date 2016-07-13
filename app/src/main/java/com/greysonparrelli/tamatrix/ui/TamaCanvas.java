package com.greysonparrelli.tamatrix.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.greysonparrelli.tamatrix.models.Tama;

import java.util.HashMap;
import java.util.Map;

public class TamaCanvas extends View {

    private static final int LCD_WIDTH = 48;
    private static final int LCD_HEIGHT = 32;

    private static final int LCD_PIXEL_WIDTH = 10;
    private static final int LCD_PIXEL_HEIGHT = 10;
    private static final int LCD_PIXEL_MARGIN = 2;

    private final Map<Character, Paint> mPaint = new HashMap<>();
    private Tama mTama;

    // Initializer Block: Runs before every constructor
    {
        mPaint.put('A', makePaint(Color.argb(0, 0, 0, 0)));
        mPaint.put('B', makePaint(Color.argb(255, 160, 176, 144)));
        mPaint.put('C', makePaint(Color.argb(255, 112, 112, 88)));
        mPaint.put('D', makePaint(Color.argb(255, 16, 32, 0)));
    }

    public TamaCanvas(Context context) {
        super(context);
    }

    public TamaCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
            height = (int) (width * ((float) LCD_HEIGHT)/LCD_WIDTH);
        }
        if (widthMode == MeasureSpec.UNSPECIFIED && heightMode != MeasureSpec.UNSPECIFIED) {
            width = (int) (height * ((float) LCD_WIDTH)/LCD_HEIGHT);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        // If we have no tama, clear the screen and bail
        if (mTama == null) {
            canvas.drawRect(0, 0, width, height, mPaint.get('A'));
            return;
        }

        // We want to fill the entire bounds of the view, so we need to scale our default heights and widths
        float baseWidth = (LCD_WIDTH * LCD_PIXEL_WIDTH) + (LCD_WIDTH * LCD_PIXEL_MARGIN);
        float baseHeight = (LCD_HEIGHT * LCD_PIXEL_HEIGHT) + (LCD_HEIGHT * LCD_PIXEL_MARGIN);

        float widthScaleFactor = width/baseWidth;
        float heightScaleFactor = height/baseHeight;

        float scaledPixelWidth = widthScaleFactor * LCD_PIXEL_WIDTH;
        float scaledPixelHeight = heightScaleFactor * LCD_PIXEL_HEIGHT;
        float scaledHorizontalMargin = widthScaleFactor * LCD_PIXEL_MARGIN;
        float scaledVerticalMargin = heightScaleFactor * LCD_PIXEL_MARGIN;

        String pixels = mTama.pixels;
        for (int i = 0; i < pixels.length(); i++) {
            int row = i / LCD_WIDTH;
            int col = i % LCD_WIDTH;

            float x0 = col * scaledPixelWidth + (col * scaledHorizontalMargin);
            float x1 = x0 + scaledPixelWidth;
            float y0 = row * scaledPixelHeight + (row * scaledVerticalMargin);
            float y1 = y0 + scaledPixelHeight;

            canvas.drawRect(x0, y0, x1, y1, mPaint.get(pixels.charAt(i)));
        }
        canvas.drawRect(0, 40, 40, 40, mPaint.get('C'));
    }

    public void setTama(Tama tama) {
        mTama = tama;
        invalidate();
    }

    private Paint makePaint(int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        return paint;
    }
}
