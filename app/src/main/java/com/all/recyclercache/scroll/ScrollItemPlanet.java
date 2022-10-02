/*
 * Dennis Lang
 * Copyright (c) LanDen Labs 2022.
 */
package com.all.recyclercache.scroll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.all.recyclercache.R;


/**
 * Scrollable Planet item
 */
@SuppressWarnings("unused")
public class ScrollItemPlanet extends ScrollItem {

    private String planetName;
    private int distanceFromSun;
    private int gravity;
    private int diameter;

    public ScrollItemPlanet(String planetName, int distanceFromSun, int gravity, int diameter) {
        this.planetName = planetName;
        this.distanceFromSun = distanceFromSun;
        this.gravity = gravity;
        this.diameter = diameter;
    }

    @Override
    public ScrollItemPlanetHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scroll_item_planet, parent, false);
        return new ScrollItemPlanetHolder(view);
    }

    @Override
    public int getItemType() {
        return ItemType.TYPE_PLANET;
    }

    // ---------------------------------------------------------------------------------------------

    String getPlanetName() {
        return planetName;
    }

    void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    int getDistanceFromSun() {
        return distanceFromSun;
    }

    void setDistanceFromSun(int distanceFromSun) {
        this.distanceFromSun = distanceFromSun;
    }

    int getGravity() {
        return gravity;
    }

    void setGravity(int gravity) {
        this.gravity = gravity;
    }

    int getDiameter() {
        return diameter;
    }

    void setDiameter(int diameter) {
        this.diameter = diameter;
    }
}
