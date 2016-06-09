package jeppesstudios.dgraf.toolbar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;

import java.util.Random;

/**
 * Created by jakob on 02/06/16.
 */
public class RadialDrawable extends Drawable implements Animator.AnimatorListener {

    private static final String TAG = RadialDrawable.class.getSimpleName();

    private int color;
    private float touchX = -1;
    private float touchY = -1;
    private Paint paint;
    private ObjectAnimator animator;
    private float progress;
    private float radius;


    public RadialDrawable(int color) {
        this.color = color;
        paint = new Paint();
        animator = ObjectAnimator.ofFloat(this, "progress", 0, 1);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.addListener(this);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(color);

        if(touchX != -1) {
            canvas.drawCircle(touchX, touchY, radius * progress, paint);
        }

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    private void setProgress(float progress) {
        this.progress = progress;
        invalidateSelf();
    }

    public void radiateFromPoint(float x, float y) {
        touchX = x;
        touchY = y;

        Rect rect = getBounds();
        float height = rect.height();
        float width = rect.width();

        float distToTopRight = (float) Math.sqrt(Math.pow(width - x, 2) + Math.pow(0 - y, 2));
        float distToTopLeft = (float) Math.sqrt(Math.pow(0 - x, 2) + Math.pow(0 - y, 2));
        float distToBottomRight = (float) Math.sqrt(Math.pow(width - x, 2) + Math.pow(height - y, 2));
        float distToBottomLeft = (float) Math.sqrt(Math.pow(0 - x, 2) + Math.pow(height - y, 2));

        radius = Math.max(distToBottomLeft, Math.max(distToBottomRight, Math.max(distToTopLeft, distToTopRight)));

        Random random = new Random();
        paint.setARGB(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

        animator.start();
    }


    @Override
    public void onAnimationEnd(Animator animation) {
        color = paint.getColor();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }
    @Override
    public void onAnimationCancel(Animator animation) {

    }
    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
