package jeppesstudios.dgraf;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private View card;
    private PathView pathView;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pathView = (PathView) findViewById(R.id.pathview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        card = findViewById(R.id.card);
        card.setZ(fab.getZ());

        final MorphAnimator animator = new MorphAnimator(fab, card);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.animate(false);
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.animate(true);
            }
        });
        card.setVisibility(View.INVISIBLE);
    }

}