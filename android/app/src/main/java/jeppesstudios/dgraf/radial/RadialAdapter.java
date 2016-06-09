package jeppesstudios.dgraf.radial;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jeppesstudios.dgraf.R;

/**
 * An adapter used for populating the grid with dummy elements.
 * This does not affect the animation in any way.
 */
public class RadialAdapter extends RecyclerView.Adapter<RadialAdapter.ViewHolder> {

    private RadialView radialView;
    private int backgroundColor;

    public RadialAdapter(RadialView radialView, int backgroundColor) {
        // Hold a reference to the overlaying view, so that the animation
        // can be triggered when an element is clicked.
        this.radialView = radialView;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);

        // Run the animation when a grid item is clicked
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radialView.setRect(v,  backgroundColor);
            }
        });

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    // Just set the number of elements to 25 like in the google play example
    @Override
    public int getItemCount() {
        return 25;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
