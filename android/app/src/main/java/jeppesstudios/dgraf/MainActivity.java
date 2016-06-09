package jeppesstudios.dgraf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jeppesstudios.dgraf.drag.DragActivity;
import jeppesstudios.dgraf.radial.RadialReactionActivity;
import jeppesstudios.dgraf.morph.MorphActivity;
import jeppesstudios.dgraf.toolbar.ToolbarActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up buttons for switching between all the different animation samples.
        findViewById(R.id.morph_button).setOnClickListener(this);
        findViewById(R.id.radial_button).setOnClickListener(this);
        findViewById(R.id.toolbar_button).setOnClickListener(this);
        findViewById(R.id.drag_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.morph_button:
                intent = new Intent(this, MorphActivity.class);
                break;
            case R.id.radial_button:
                intent = new Intent(this, RadialReactionActivity.class);
                break;
            case R.id.toolbar_button:
                intent = new Intent(this, ToolbarActivity.class);
                break;
            case R.id.drag_button:
                intent = new Intent(this, DragActivity.class);
                break;
        }
        startActivity(intent);
    }
}
