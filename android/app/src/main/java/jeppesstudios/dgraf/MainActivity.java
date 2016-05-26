package jeppesstudios.dgraf;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private View card;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DURATION = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        card = findViewById(R.id.card);
        card.setZ(fab.getZ());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });

        card.setVisibility(View.INVISIBLE);
    }



    private void go() {

        Log.d(TAG, "card x: " + card.getX());
        Log.d(TAG, "card y: " + card.getY());


        float fabX = fab.getX() + fab.getWidth() / 2;
        float fabY = fab.getY() + fab.getHeight() / 2;
        float cardX = card.getX() + card.getWidth() / 2;
        float cardY = card.getY() + card.getHeight() / 2;
        float cardXOrigin = card.getX();
        float cardYOrigin = card.getY();

        FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();

        card.setY(fabY - (card.getHeight() / 2));
        card.setX(fabX - (card.getWidth() / 2));

        float startRadius = fab.getWidth() / 2;
        float endRadius = (float) Math.sqrt(Math.pow(card.getWidth()/2, 2) + Math.pow(card.getHeight()/2, 2));
        Animator circleRevealAnimator = ViewAnimationUtils.createCircularReveal(card,
                card.getWidth() / 2,
                card.getHeight() / 2,
                startRadius,
                endRadius);

        circleRevealAnimator.setDuration(DURATION);
        fab.animate().alpha(0).setDuration(DURATION / 10).start();


        // Animate the color
        int startColor = fab.getBackgroundTintList().getDefaultColor();
        int endColor = Color.WHITE; //((ColorDrawable) card.getBackground()).getColor();
        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(this, "color", startColor, endColor);
        colorAnimator.setDuration(DURATION / 6);

        // Animate the position
        card.animate().x(cardXOrigin).y(cardYOrigin).setInterpolator(interpolator).setDuration(DURATION / 2).start();
        fab.animate().x(cardX).y(cardY).setDuration(DURATION / 2).start();

        // Animator set
        AnimatorSet superAnimator = new AnimatorSet();
        superAnimator.playTogether(circleRevealAnimator, colorAnimator);
        superAnimator.setInterpolator(new FastOutSlowInInterpolator());
        superAnimator.start();
        card.setVisibility(View.VISIBLE);

//        circleRevealAnimator.start();
//        colorAnimator.start();
    }

    private void setColor(int color) {
        card.setBackgroundColor(color);
    }
}