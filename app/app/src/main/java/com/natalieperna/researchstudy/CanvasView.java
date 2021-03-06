package com.natalieperna.researchstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class CanvasView extends View {

    private Path path;
    private Paint paint;
    private Queue<TimePoint> queue;
    private TestResults test;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupCanvas();
        setupQueue();
    }

    /**
     * Prepare the canvas for drawing with an empty path, brush size, colour, etc.
     */
    private void setupCanvas() {
        int paintColor = Color.RED;
        int brushSize = 10;

        path = new Path();
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(brushSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * Prepare the drawing queue which will store path coordinates until it is time to draw them
     */
    private void setupQueue() {
        Comparator<TimePoint> comparator = new TimePointComparator();
        queue = new PriorityQueue<TimePoint>(10, comparator);

        test = new TestResults();
    }

    /**
     * Start a new test
     */
    public void newTest(int test, int lag) {
        path.reset();
        queue.clear(); // just in case
        invalidate();
        this.test = new TestResults(test, lag);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        long now = SystemClock.uptimeMillis();

        test.path.add(new TimePoint(x, y, now));

        switch (event.getAction()) {
            // To start a new path, move brush to first position
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                if (test.start == 0) // Only set start time once at very beginning
                    test.start = now;
                break;
            // When new points are collected, queue them for drawing later
            // Draw any points on the queue for more than lag ms
            case MotionEvent.ACTION_MOVE:
                queue.add(new TimePoint(x, y, now));
                delayedDraw(now);
                break;
            // When path is done, finish drawing the remaining points
            case MotionEvent.ACTION_UP:
                test.end = now;
                finishDrawing();
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Dequeue and draw any points that were entered at least lag ms ago
     *
     * @param now the current system time
     */
    private void delayedDraw(long now) {
        TimePoint toDraw = queue.peek();
        while (toDraw != null && now - toDraw.t >= test.lag) {
            path.lineTo(toDraw.x, toDraw.y);
            queue.remove();
            toDraw = queue.peek();
        }

        // Must be run on the UI thread
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    /**
     * Draw remaining points in queue over time
     * Need to do this on a new separate thread now that the touch events are over
     */
    private void finishDrawing() {
        final Timer timer = new Timer();

        // Keep drawing remaining points in delayed queue until it's empty
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (queue.isEmpty()) {
                    timer.cancel();
                } else {
                    long now = SystemClock.uptimeMillis();
                    delayedDraw(now);
                }
            }
        }, 0, 100);
    }

    public TestResults getTest() {
        return test;
    }

    /**
     * Comparator class for PriorityQueue
     */
    private class TimePointComparator implements Comparator<TimePoint> {
        @Override
        public int compare(TimePoint lhs, TimePoint rhs) {
            if (lhs.t > rhs.t) {
                return 1;
            } else if (lhs.t < rhs.t) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
