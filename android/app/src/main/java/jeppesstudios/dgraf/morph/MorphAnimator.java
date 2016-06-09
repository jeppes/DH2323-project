package jeppesstudios.dgraf.morph;

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
 * An animator which takes two views, a floating action button and an arbitrary rectangle.
 * In this class, start view refers to the view visible in the starting state of the animation,
 * end view refers to the the view visible in the final state.
 */
public class MorphAnimator {

    private static final String TAG = MorphAnimator.class.getSimpleName();

    // Duration of all the animations
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

    /*
        Set up the circular reveal animation which will be used to create the morph illusion.
     */
    private Animator setupCircleReveal(boolean reversed) {
        // The start radius will simply be the width of the floating action button
        float startRadius = startView.getWidth() / 2;
        // The end radius is the distance from the center of the end view to its origin.
        // This will ensure the circle covers the entire view.
        float endRadius = (float) Math.sqrt(Math.pow(endView.getWidth()/2, 2) + Math.pow(endView.getHeight()/2, 2));

        // If the animation is being reversed, swap the starting and ending radii
        if (reversed) {
            float temp = startRadius;
            startRadius = endRadius;
            endRadius = temp;
        }

        // Setup the actual circular reveal animator, be sure to set the visibilities of
        // the views accordingly once this animation is run.
        Animator circleRevealAnimator = ViewAnimationUtils.createCircularReveal(endView,
                endView.getWidth() / 2,
                endView.getHeight() / 2,
                startRadius,
                endRadius);
        circleRevealAnimator.setDuration(DURATION);
        return circleRevealAnimator;
    }

    /*
        Set up an animator for changing the color of the views
     */
    private Animator setupColorAnimator(boolean reversed) {
        // Animate the color
        int startColor = startView.getBackgroundTintList().getDefaultColor();
        // The end color is just white in our examples, but this could be changed
        // to an arbitrary color easily as shown in the commented out code.
        int endColor = Color.WHITE; //((ColorDrawable) card.getBackground()).getColor();

        // If the animation is reversing, just swap the colors
        if (reversed) {
            int temp = startColor;
            startColor = endColor;
            endColor = temp;
        }

        // Setup the animator and return it
        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(this, "color", startColor, endColor);
        colorAnimator.setDuration(DURATION);
        return colorAnimator;
    }

    /*
        Calculate the bezier path along which the views will be translated.
        This bezier path is relative to the end view's origin,
        so be sure to apply an offset if you wish to animate the start view along the same path
     */
    private Path setupPathAnimator(boolean reversed) {
        // Path for animating position
        Path path = new Path();
        float controlX;
        float controlY;

        // The curvature of the path will depend on the view's positions relative to each other.
        // The following code handles the two possible cases, i.e. when the start view is below
        // the end view and when it is above it, and then sets the control points for the path accordingly.
        if (endViewYOrigin > startView.getY()) {
            controlX = Math.min(startViewX, startViewX - endView.getWidth() / 2);
            controlY = Math.max(startView.getY(), endViewYOrigin);
        } else {
            controlX = Math.min(startViewX, endViewXOrigin);
            controlY = Math.max(startView.getY(), endViewYOrigin) - endView.getHeight() * 4 / 7;
        }

        // If the animation is reversed, change the starting and ending points accordingly.
        if (reversed) {
            path.moveTo(endViewXOrigin, endViewYOrigin);
            path.quadTo(controlX, controlY, startViewX - (endView.getWidth() / 2), startViewY - (endView.getHeight() / 2));
        } else {
            path.moveTo(startViewX - (endView.getWidth() / 2), startViewY - (endView.getHeight() / 2));
            path.quadTo(controlX, controlY, endViewXOrigin, endViewYOrigin);
        }
        return path;
    }

    /*
        This method will run the animation forwards, i.e. the start view will morph into the end view.
     */
    private void forwards() {
        setup();

        // Align the center of the of the end view with the center of the start view
        endView.setY(startViewY - (endView.getHeight() / 2));
        endView.setX(startViewX - (endView.getWidth() / 2));

        // Set up the different animators used.
        // All of which should be reversed, of course.
        Animator circleAnimator = setupCircleReveal(false);
        Animator colorAnimator = setupColorAnimator(false);

        Path path = setupPathAnimator(false);
        Animator cardPathAntimator = ObjectAnimator.ofFloat(endView, endView.X, endView.Y, path);
        cardPathAntimator.setDuration(DURATION);
        path.offset(endView.getWidth() / 2 - startView.getWidth() / 2, endView.getHeight() / 2 - startView.getHeight() / 2);
        Animator fabPathAntimator = ObjectAnimator.ofFloat(startView, startView.X, startView.Y, path);
        fabPathAntimator.setDuration(DURATION);

        Animator fadeAnimator = ObjectAnimator.ofFloat(startView, startView.ALPHA, 1.0f, -3.0f);
        fadeAnimator.setDuration(DURATION);

        fabPathAntimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                // Reset the position of the start view after the animation is finished, and make
                // it invisible.
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
        path.offset(endView.getWidth() / 2 - startView.getWidth() / 2, endView.getHeight() / 2 - startView.getHeight() / 2);
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

    /*
        Run a set of animations at the same time.
     */
    private void startAnimation(Animator... animators) {
        AnimatorSet superAnimator = new AnimatorSet();
        superAnimator.playTogether(animators);
        superAnimator.setInterpolator(interpolator);

        superAnimator.start();
    }

    private void setup() {
        // Store the relevant positions of the views for later use
        startViewX = startView.getX() + startView.getWidth() / 2;
        startViewY = startView.getY() + startView.getHeight() / 2;
        endViewXOrigin = endView.getX();
        endViewYOrigin = endView.getY();
    }

    /*
        Run the morph animation between the two views.
        Be sure to specify if the animation should be reversed.
     */
    public void animate(boolean reversed) {
        if (reversed) {
            backwards();
        } else {
            forwards();
        }
    }

    /*
        Helper method used by the color animator
     */
    private void setColor(int color) {
        endView.setBackgroundColor(color);
    }

}
