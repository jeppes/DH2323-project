package jeppesstudios.dgraf.radial;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

/**
 * This is the view which overlays the grid.
 * It is responsible for the animation.
 * After setup, run it with the setRect method
 */
public class RadialView extends View {

    private static final String TAG = RadialView.class.getSimpleName();

    // The duration of the animation
    private int DURATION = 400;

    // State variables
    private float radiusProgress = 0.0f;
    private float radius = -1.0f;

    private RectF clipRect;
    private ObjectAnimator animator;
    private Paint paint;

    public RadialView(Context context) {
        super(context);
        init();
    }

    public RadialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Create an animator with a reference to the setRadius() method
        animator = ObjectAnimator.ofFloat(this, "radius", 0.0f, 1.0f);
        animator.setDuration(DURATION);
        animator.setInterpolator(new FastOutSlowInInterpolator());

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    // Increase the size of the radius and call invalidate to trigger a redraw.
    public void setRadius(float progress) {
        radiusProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // When drawing, clip out the selected element so that it will not be affected.
        if (clipRect != null) {
            canvas.clipRect(clipRect, Region.Op.DIFFERENCE);
        }

        // If the animation is running, draw a circle which will grow in
        // relation to the current state of the radiusProgress
        if (radiusProgress > 0 && clipRect != null) {
            canvas.drawCircle(clipRect.centerX(), clipRect.centerY(), radius * radiusProgress, paint);
        }
    }

    public void setRect(View v, int color) {
        // Calculate the relative position of the view
        int clipViewLocation[] = new int[2];
        v.getLocationOnScreen(clipViewLocation);

        int radialViewLocation[] = new int[2];
        getLocationInWindow(radialViewLocation);

        float leftOffset = radialViewLocation[0];
        float topOffset = radialViewLocation[1];

        float left = clipViewLocation[0] - leftOffset;
        float top = clipViewLocation[1] - topOffset;

        clipRect = new RectF(left, top, left + v.getWidth(), top + v.getHeight());

        // Calculate the radius
        float distToTopRight = (float) Math.sqrt(Math.pow(getWidth() - clipRect.centerX(), 2) + Math.pow(0 - clipRect.centerY(), 2));
        float distToTopLeft = (float) Math.sqrt(Math.pow(0 - clipRect.centerX(), 2) + Math.pow(0 - clipRect.centerY(), 2));
        float distToBottomRight = (float) Math.sqrt(Math.pow(getWidth() - clipRect.centerX(), 2) + Math.pow(getHeight() - clipRect.centerY(), 2));
        float distToBottomLeft = (float) Math.sqrt(Math.pow(0 - clipRect.centerX(), 2) + Math.pow(0 - clipRect.centerY(), 2));

        radius = Math.max(distToBottomLeft, Math.max(distToBottomRight, Math.max(distToTopLeft, distToTopRight)));

        paint.setColor(color);

        // Invalidate the view to trigger a redraw, i.e. onDraw will be run.
        invalidate();
        animator.start();
    }
}
