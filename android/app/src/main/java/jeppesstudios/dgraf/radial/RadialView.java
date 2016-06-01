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
 * Created by jespersandstrom on 01/06/16.
 */
public class RadialView extends View{

    private static final String TAG = RadialView.class.getSimpleName();

    private int DURATION = 500;

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
        animator = ObjectAnimator.ofFloat(this, "radius", 0.0f, 1.0f);
        animator.setDuration(DURATION);
        animator.setInterpolator(new FastOutSlowInInterpolator());

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setRadius(float progress) {
        radiusProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (clipRect != null) {
            canvas.clipRect(clipRect, Region.Op.DIFFERENCE);
        }

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

        // Setup a gradient for the paint
//        int centerColor = Color.parseColor("#eeeeee");
//        int edgeColor1 = Color.parseColor("#cceeeeee");
//        int edgeColor2 = Color.parseColor("#aaeeeeee");
//        int edgeColor3 = Color.parseColor("#77eeeeee");
//        int edgeColor4 = Color.parseColor("#44eeeeee");
//        int colors[] = new int[]{centerColor, edgeColor1, edgeColor2, edgeColor3, edgeColor4};
//        float stops[] = new float[]{radius, radius, radius, radius, radius};
//        paint.setShader(new RadialGradient(clipRect.centerX(), clipRect.centerY(), radius * 6, colors, stops, Shader.TileMode.CLAMP));

        paint.setColor(color);

        invalidate();
        animator.start();
    }
}
