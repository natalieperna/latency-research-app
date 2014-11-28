package com.natalieperna.researchstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View {

    private Path path;
    private Paint paint;
    private int paintColor = Color.RED;
    private int brushSize = 10;

    ArrayList<Point> points;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupCanvas();
        points = new ArrayList<Point>;
    }

    private void setupCanvas() {
        path = new Path();
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(brushSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        points.add(new Point(x, y));

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void clear() {
        points.clear();
        path.reset();
        invalidate();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}
