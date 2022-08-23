package com.example.simplegraphics;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class DrawView extends View implements OnTouchListener {
    private static final String TAG = "DrawView";

    private boolean eraser = false;

    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint();

    public void setEraser(boolean isEraser) {
        this.eraser = isEraser;
    }

    public DrawView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setBackgroundColor(Color.BLACK);

        this.setOnTouchListener(this);

        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {

        for (Point point : points) {
            canvas.drawCircle(point.x, point.y, 5, paint);
            // Log.d(TAG, "Painting: "+point);
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        // if(event.getAction() != MotionEvent.ACTION_DOWN)
        // return super.onTouchEvent(event);

        Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();

        if (eraser && points.contains(point)) {
            Log.d(TAG, "remove point: " + point);
            points.remove(point);
            Log.d(TAG, "points size: " + points.size());
        } else if (!eraser) {
            points.add(point);
        }

        invalidate();
        return true;
    }
}

class Point {
    float x, y;

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point toCompare = (Point) obj;
            int x1 = Math.round(toCompare.x);
            int y1 = Math.round(toCompare.y);
            int x2 = Math.round(x);
            int y2 = Math.round(y);

            if (Math.abs(x1-x2) < 20 && Math.abs(y1-y2) < 20) {
                return true;
            }
        }

        return false;
    }

}
