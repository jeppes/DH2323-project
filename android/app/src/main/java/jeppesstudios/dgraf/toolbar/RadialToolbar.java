package jeppesstudios.dgraf.toolbar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowInsets;

/**
 * A custom toolbar which draws itself under the status bar and adjusts its size accordingly.
 */
public class RadialToolbar extends Toolbar {
    private final String TAG = RadialToolbar.class.getSimpleName();

    boolean insetsApplied = false;
    private RadialDrawable radialDrawable;

    public RadialToolbar(Context context) {
        super(context);
    }

    public RadialToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RadialToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        // The status bar should 'fitSystemWindows', i.e. allow the system to apply some inset to it.
        // We will intercept this later to draw under the status bar.
        setFitsSystemWindows(true);

        // Get a reference to the current background color and setup the background drawable
        // (see radial drawable class)
        int color = ((ColorDrawable)getBackground()).getColor();
        radialDrawable = new RadialDrawable(color);
        setBackground(radialDrawable);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        // The system is attempting to apply insets to this toolbar, intercept these and
        // change the height of the toolbar instead. Then set some padding so that any
        // content drawn within the toolbar will appear at the proper position.
        // This method may be called several times, so only do this once.
        if (!insetsApplied) {
            setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);
            int height = getLayoutParams().height + insets.getSystemWindowInsetTop();
            int width = getLayoutParams().width;
            setLayoutParams(new CoordinatorLayout.LayoutParams(width, height));
            insetsApplied = true;
        }
        return insets;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Listen for touch events and send them to the background drawable.
        radialDrawable.radiateFromPoint(ev.getX(), ev.getY());
        return super.onTouchEvent(ev);
    }
}
