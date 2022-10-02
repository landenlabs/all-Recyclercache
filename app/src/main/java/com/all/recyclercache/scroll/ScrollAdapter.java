/*
 * Dennis Lang
 * Copyright (c) LanDen Labs 2022.
 */
package com.all.recyclercache.scroll;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Recycler Adapter to manage ScrollItemHolder(s) and ScrollItem(s)
 */
public class ScrollAdapter extends RecyclerView.Adapter<ScrollItemHolder> {

    // True to make new holder per position, False reuse holders by item type.
    public final boolean uniqueViewHolder;

    // Collection of view holders by type.
    private final SparseArray<ScrollItem> holderMakersByType = new SparseArray<>();

    private final ArrayList<ScrollItem> items;


    public ScrollAdapter(Context context, ArrayList<ScrollItem> items, boolean uniqueViewHolder) {
        this.items = items;
        this.uniqueViewHolder = uniqueViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (uniqueViewHolder) {
            return position;
        } else   {
            ScrollItem item = items.get(position);
            int viewType = item.getItemType();
            holderMakersByType.append(viewType, item);
            return viewType;
        }
    }

    @NonNull
    @Override
    public ScrollItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (uniqueViewHolder) {
            int position = viewType;
            return items.get(position).onCreateViewHolder(parent, position);
        } else {
            return holderMakersByType.get(viewType).onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollItemHolder scrollItemHolder, int position) {
        ScrollItem item = items.get(position);
        scrollItemHolder.onBindViewHolder(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ScrollItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ScrollItemHolder holder) {
        holder.onViewDetachedFromWindow();
        super.onViewDetachedFromWindow(holder);
    }
}
