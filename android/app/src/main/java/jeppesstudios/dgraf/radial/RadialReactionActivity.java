package jeppesstudios.dgraf.radial;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import jeppesstudios.dgraf.R;

public class RadialReactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radial_reaction);

        // Set up the grid and its overlaying view
        RadialView radialView = (RadialView) findViewById(R.id.radial_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        // Get the background color for the overlaying view
        TypedArray array = getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorBackground,
        });
        int backgroundColor = array.getColor(0, 0xFF00FF);
        array.recycle();

        recyclerView.setAdapter(new RadialAdapter(radialView, backgroundColor));
    }
}
