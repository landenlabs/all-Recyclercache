/*
 * Dennis Lang
 * Copyright (c) LanDen Labs 2022.
 */
package com.all.recyclercache.scroll;

import android.view.View;
import android.widget.TextView;

import com.all.recyclercache.R;

import java.util.Locale;

/**
 * Recycler View Holder for Planet item
 */
public class ScrollItemPlanetHolder extends ScrollItemHolder {

    private final TextView txtName;
    private final TextView txtDistance;
    private final TextView txtGravity;
    private final TextView txtDiameter;

    ScrollItemPlanetHolder(View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtDistance = itemView.findViewById(R.id.txtDistance);
        txtGravity = itemView.findViewById(R.id.txtGravity);
        txtDiameter = itemView.findViewById(R.id.txtDiameter);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // MemStats.cntViewHolder.decrementAndGet();
    }

    public void onBindViewHolder(ScrollItem item) {
        if (item instanceof ScrollItemPlanet) {
            ScrollItemPlanet planet = (ScrollItemPlanet) item;
            txtName.setText(planet.getPlanetName());
            txtDistance.setText(String.format(Locale.US, "Distance from Sun : %d Million KM",
                    planet.getDistanceFromSun()));
            txtGravity.setText(
                    String.format(Locale.US, "Surface Gravity : %d N/kg", planet.getGravity()));
            txtDiameter.setText(String.format(Locale.US, "Diameter : %d KM", planet.getDiameter()));
        }
    }

}
