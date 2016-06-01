package jeppesstudios.dgraf.radial;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jeppesstudios.dgraf.R;

/**
 * Created by jakob on 01/06/16.
 */
public class RadialAdapter extends RecyclerView.Adapter<RadialAdapter.ViewHolder> {

    private RadialView radialView;
    private int backgroundColor;

    public RadialAdapter(RadialView radialView, int backgroundColor) {
        this.radialView = radialView;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);

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
