/*
 * Dennis Lang
 * Copyright (c) LanDen Labs 2022.
 */
package com.all.recyclercache.scroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.all.recyclercache.R;

/**
 * Scrollable Map item
 */
public class ScrollItemMap extends ScrollItem {

    private final int idx;

    public ScrollItemMap(int idx) {
        this.idx = idx;
    }

    @Override
    public ScrollItemMapHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.scroll_item_map, parent, false);
        return new ScrollItemMapHolder(view, idx);
    }

    @Override
    public int getItemType() {
        return ItemType.TYPE_MAP;
    }
}
