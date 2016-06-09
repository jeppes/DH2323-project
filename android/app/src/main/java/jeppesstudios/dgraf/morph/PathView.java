package jeppesstudios.dgraf.morph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * This is just a custom view used for drawing the bezier path on the screen.
 * It is only used for debugging purposes.
 */
public class PathView extends View {

    private Paint tracePaint = null;
    private Path tracePath = null;

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*
        Set a path on this view, once the path has been set it will automatically be drawn to the screen.
     */
    public void initWithPath(Path path) {
        tracePaint = new Paint();
        tracePaint.setColor(Color.BLACK);
        tracePaint.setStrokeWidth(5);
        tracePaint.setAntiAlias(true);
        tracePaint.setStyle(Paint.Style.STROKE);
        tracePath = path;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (tracePath != null && tracePaint != null) {
            // Draw the path to the screen
            canvas.drawPath(tracePath, tracePaint);
        }

    }
}
