package jeppesstudios.dgraf.drag;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * A layout which can be dismissed by dragging
 */
public class DraggableFrameLayout extends FrameLayout {
    // The drag distance is the total distance that the layout has been translated from its resting position
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
        // Set up the max distance as a fraction of the layouts height
        maxDistance = getHeight() / 4;

        // Only accept vertical scroll events
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // If the user is in the process of dragging the layout down, and decides
        // to change direction, we don't want to scroll the contents of the page.
        // To account for this we simply consume this scroll so that it does not
        // reach the child view.
        if(dragging) {
            dragScale(target, consumed[1], dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // This method will be called one we've registered for vertical scroll events in onStartNestedScroll
        dragScale(target, dyConsumed, dyUnconsumed);
    }

    /*
        Translate the layout down and scale it.
     */
    private void dragScale(View target, int dyConsumed, int dyUnconsumed) {
        float scroll = dyUnconsumed;

        if (dragging) {
            // If the user changes direction and drags the view back to its resting location,
            // snap it into place once they get within an indiscernible distance to prevent
            // undesired scrolling behavior in the opposite direction.
            if ((dragDistance > 0 && draggingDown) || (dragDistance < 0 && !draggingDown)) {
                onStopNestedScroll(target);
                return;
            }
        }

        // If the user has just started dragging, set the dragging variable to true.
        // Also determine if the user is dragging the layout up or down based on the
        // amount of unconsumed scroll.
        if (dragDistance == 0.0f && dyUnconsumed != 0.0f) {
            draggingDown = dyUnconsumed < 0;
            dragging = true;
        }

        // Perform the relevant transformations based on the scroll distance and
        // direction of drag.
        float multiple = draggingDown ? 1 : -1;
        dragDistance += scroll;
        float fraction = translationFraction(dragDistance);
        setTranslationY(multiple * fraction*maxDistance);
        setScaleX(1 - fraction/10);
    }

    /*
        The amount to translate by (or scale by) is given by the following fraction.
        A logarithmic function is used to give the impression of resistance when dragging.
     */
    private float translationFraction(float distance) {
        float fraction = (float) Math.log10(1 + Math.abs(distance)/maxDistance);
        return fraction;
    }

    @Override
    public void onStopNestedScroll(View child) {
        // If the user has dragged the layout past a certain threshold, animate the layout out of view.
        if(translationFraction(dragDistance) > 0.5) {
            float height = -getHeight();
            if(draggingDown) {
                // Set the direction in which the view should disappear based on the drag direction.
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

        // Reset the state if the threshold was not reached, and animate back to equilibrium
        dragDistance = 0.0f;
        animate().scaleX(1).translationY(0).setDuration(100).start();
        draggingDown = false;
        dragging = false;
    }
}
