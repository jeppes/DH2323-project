package jeppesstudios.dgraf;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by jespersandstrom on 30/05/16.
 */
public class MorphAnimator {

    private static final String TAG = MorphAnimator.class.getSimpleName();
    private static final int DURATION = 400;

    private FastOutSlowInInterpolator interpolator;
    private float startViewX;
    private float startViewY;
    private float endViewXOrigin;
    private float endViewYOrigin;

    private View startView;
    private View endView;

    public MorphAnimator(View startView, View endView) {
        this.startView = startView;
        this.endView = endView;
        interpolator = new FastOutSlowInInterpolator();
    }

    private Animator setupCircleReveal(boolean reversed) {
        float startRadius = startView.getWidth() / 2;
        float endRadius = (float) Math.sqrt(Math.pow(endView.getWidth()/2, 2) + Math.pow(endView.getHeight()/2, 2));
        if (reversed) {
            float temp = startRadius;
            startRadius = endRadius;
            endRadius = temp;
        }

        Animator circleRevealAnimator = ViewAnimationUtils.createCircularReveal(endView,
                endView.getWidth() / 2,
                endView.getHeight() / 2,
                startRadius,
                endRadius);
        circleRevealAnimator.setDuration(DURATION);
        return circleRevealAnimator;
    }

    private Animator setupColorAnimator(boolean reversed) {
        // Animate the color
        int startColor = startView.getBackgroundTintList().getDefaultColor();
        int endColor = Color.WHITE; //((ColorDrawable) card.getBackground()).getColor();
        if (reversed) {
            int temp = startColor;
            startColor = endColor;
            endColor = temp;
        }

        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(this, "color", startColor, endColor);
        colorAnimator.setDuration(DURATION);

        return colorAnimator;
    }

    private Path setupPathAnimator(boolean reversed) {
        // Path for animating position
        Path path = new Path();
        float controlX;
        float controlY;
        if (endViewYOrigin > startView.getY()) {
            controlX = Math.min(startViewX, startViewX - endView.getWidth() / 2);
            controlY = Math.max(startView.getY(), endViewYOrigin);
        } else {
            controlX = Math.min(startViewX, endViewXOrigin);
            controlY = Math.max(startView.getY(), endViewYOrigin) - endView.getHeight() * 4 / 7;
        }

        Log.d(TAG, "setupPathAnimator: " + controlX);
        Log.d(TAG, "setupPathAnimator: " + controlY);

        if (reversed) {
            path.moveTo(endViewXOrigin, endViewYOrigin);
            path.quadTo(controlX, controlY, startViewX - (endView.getWidth() / 2), startViewY - (endView.getHeight() / 2));
        } else {
            path.moveTo(startViewX - (endView.getWidth() / 2), startViewY - (endView.getHeight() / 2));
            path.quadTo(controlX, controlY, endViewXOrigin, endViewYOrigin);
        }
        return path;
    }

    private void forwards() {
        setup();

        endView.setY(startViewY - (endView.getHeight() / 2));
        endView.setX(startViewX - (endView.getWidth() / 2));

        Animator circleAnimator = setupCircleReveal(false);
        Animator colorAnimator = setupColorAnimator(false);

        Path path = setupPathAnimator(false);
        Animator cardPathAntimator = ObjectAnimator.ofFloat(endView, endView.X, endView.Y, path);
        cardPathAntimator.setDuration(DURATION);
        path.offset(endView.getWidth() / 2 - startView.getWidth() / 2, endView.getHeight() / 2 - endView.getHeight() / 2);
        Animator fabPathAntimator = ObjectAnimator.ofFloat(startView, startView.X, startView.Y, path);
        fabPathAntimator.setDuration(DURATION);

        Animator fadeAnimator = ObjectAnimator.ofFloat(startView, startView.ALPHA, 1.0f, -3.0f);
        fadeAnimator.setDuration(DURATION);

        fabPathAntimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                startView.setX(startViewX - startView.getWidth() / 2);
                startView.setY(startViewY - startView.getHeight() / 2);
                startView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        startAnimation(circleAnimator, colorAnimator, cardPathAntimator, fabPathAntimator, fadeAnimator);
        endView.setVisibility(View.VISIBLE);
    }

    private void backwards() {
        setup();

        Animator circleAnimator = setupCircleReveal(true);
        Animator colorAnimator = setupColorAnimator(true);
        Path path = setupPathAnimator(true);
        Animator cardPathAntimator = ObjectAnimator.ofFloat(endView, endView.X, endView.Y, path);
        cardPathAntimator.setDuration(DURATION);
        path.offset(endView.getWidth() / 2 - startView.getWidth() / 2, endView.getHeight() / 2 - endView.getHeight() / 2);
        startView.setX(endViewXOrigin + endView.getWidth() / 2);
        startView.setY(endViewYOrigin + endView.getHeight() / 2);
        startView.setTranslationX(startViewX - startView.getWidth() / 2);
        startView.setTranslationY(startViewY - startView.getHeight() / 2);
        startView.setVisibility(View.VISIBLE);
        Animator fabPathAnitmator = ObjectAnimator.ofFloat(startView, startView.X, startView.Y, path);
        fabPathAnitmator.setDuration(DURATION);

        Animator fadeAnimator = ObjectAnimator.ofFloat(startView, startView.ALPHA, -3.0f, 1.0f);
        fadeAnimator.setDuration(DURATION);

        cardPathAntimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                endView.setVisibility(View.GONE);
                endView.setX(endViewXOrigin);
                endView.setY(endViewYOrigin);
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            public void onAnimationRepeat(Animator animation) {

            }
        });

        startAnimation(circleAnimator, colorAnimator, cardPathAntimator, fabPathAnitmator, fadeAnimator);
        startView.setVisibility(View.VISIBLE);
    }

    private void startAnimation(Animator... animators) {
        AnimatorSet superAnimator = new AnimatorSet();
        superAnimator.playTogether(animators);
        superAnimator.setInterpolator(interpolator);

        superAnimator.start();
    }

    private void setup() {
        startViewX = startView.getX() + startView.getWidth() / 2;
        startViewY = startView.getY() + startView.getHeight() / 2;
        endViewXOrigin = endView.getX();
        endViewYOrigin = endView.getY();
    }

    public void animate(boolean reversed) {
        if (reversed) {
            backwards();
        } else {
            forwards();
        }
    }

    private void setColor(int color) {
        endView.setBackgroundColor(color);
    }

}
