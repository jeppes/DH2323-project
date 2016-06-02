package jeppesstudios.dgraf.toolbar;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by jakob on 02/06/16.
 */
public class RadialToolbarBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private final String TAG = RadialToolbarBehavior.class.getSimpleName();

    public RadialToolbarBehavior() {
    }

    public RadialToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, Toolbar child, WindowInsetsCompat insets) {
        Log.d(TAG, "onApplyWindowInsets: ");
        return super.onApplyWindowInsets(coordinatorLayout, child, insets);
    }
}
