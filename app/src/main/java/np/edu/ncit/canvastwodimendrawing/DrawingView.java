package np.edu.ncit.canvastwodimendrawing;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {
    private Paint paint;
    private Paint eraserPaint;
    private Path path;
    private List<Path> paths;
    private List<Paint> paints;
    private int currentColor;
    private int backgroundColor;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        path = new Path();
        paths = new ArrayList<>();
        paints = new ArrayList<>();

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        eraserPaint = new Paint();
        eraserPaint.setColor(Color.WHITE);
        eraserPaint.setStyle(Paint.Style.STROKE);
        eraserPaint.setStrokeWidth(50);
        eraserPaint.setAntiAlias(true);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        currentColor = Color.BLACK;
        backgroundColor = Color.WHITE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColor);
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                paths.add(path);
                paints.add(new Paint(paint));
                path = new Path();
                invalidate();
                break;
        }

        return true;
    }

    public void setPenColor(int color) {
        currentColor = color;
        paint.setColor(currentColor);
        paint.setXfermode(null);
    }

    public void setEraser() {
        paint.setColor(backgroundColor);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void clearCanvas() {
        paths.clear();
        paints.clear();
        invalidate();
    }
}
