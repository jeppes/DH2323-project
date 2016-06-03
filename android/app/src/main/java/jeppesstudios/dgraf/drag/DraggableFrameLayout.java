package jeppesstudios.dgraf.drag;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by jakob on 03/06/16.
 */
public class DraggableFrameLayout extends FrameLayout {
    private float dragDistance = 0.0f;
    private static final String TAG = DraggableFrameLayout.class.getSimpleName();
    private float maxDistance;
    private boolean draggingDown;
    private boolean dragging;

    public DraggableFrameLayout(Context context) {
        super(context);
    }

    public DraggableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DraggableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        maxDistance = getHeight() / 4;
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if(dragging) {
            dragScale(target, consumed[1], dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        dragScale(target, dyConsumed, dyUnconsumed);
    }

    private void dragScale(View target, int dyConsumed, int dyUnconsumed) {
        float scroll = dyUnconsumed;

        if (dragging) {
            if ((dragDistance > 0 && draggingDown) || (dragDistance < 0 && !draggingDown)) {
                onStopNestedScroll(target);
                return;
            }
        }

        if (dragDistance == 0.0f && dyUnconsumed != 0.0f) {
            draggingDown = dyUnconsumed < 0;
            dragging = true;
        }

        float multiple = draggingDown ? 1 : -1;
        dragDistance += scroll;
        Log.d(TAG, "dragScale: " + dragDistance);
        float fraction = translationFraction(dragDistance);
        setTranslationY(multiple * fraction*maxDistance);
        setScaleX(1 - fraction/10);
    }

    private float translationFraction(float distance) {
        float fraction = (float) Math.log10(1 + Math.abs(distance)/maxDistance);
        return fraction;
    }

    @Override
    public void onStopNestedScroll(View child) {
        if(translationFraction(dragDistance) > 0.5) {
            float height = -getHeight();
            if(draggingDown) {
                height = getHeight();
            }

            animate().translationY(height).setDuration(250).withEndAction(new Runnable() {
                @Override
                public void run() {
                    ((Activity) getContext()).onBackPressed();
                }
            }).start();

            return;
        }

        dragDistance = 0.0f;
        animate().scaleX(1).translationY(0).setDuration(100).start();
        draggingDown = false;
        dragging = false;
    }
}
