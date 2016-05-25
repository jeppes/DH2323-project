package jeppesstudios.dgraf;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private View card;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        card = findViewById(R.id.card);

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

        card.setY(fabY - (card.getHeight() / 2));
        card.setX(fabX - (card.getWidth() / 2));

        float startRadius = fab.getWidth() / 2;
        Animator circleRevealAnimator = ViewAnimationUtils.createCircularReveal(card,
                card.getWidth() / 2,
                card.getHeight() / 2,
                startRadius,
                Math.max(card.getHeight(),card.getWidth()) * 1.5f);

        circleRevealAnimator.setDuration(DURATION);
        circleRevealAnimator.start();
        fab.animate().alpha(0).setDuration(DURATION / 5).start();
        card.setVisibility(View.VISIBLE);
    }

}