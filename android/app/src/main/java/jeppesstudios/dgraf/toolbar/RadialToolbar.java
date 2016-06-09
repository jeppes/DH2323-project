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
 * Created by jakob on 02/06/16.
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
        setFitsSystemWindows(true);
        int color = ((ColorDrawable)getBackground()).getColor();
        radialDrawable = new RadialDrawable(color);
        setBackground(radialDrawable);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
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
        Log.d(TAG, "onTouchEvent: " + ev.getX() + "," + ev.getY());
        radialDrawable.radiateFromPoint(ev.getX(), ev.getY());
        return super.onTouchEvent(ev);
    }
}
