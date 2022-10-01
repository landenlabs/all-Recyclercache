// Dennis Lang
// Copyright LanDen Labs 2022
package com.all.recyclercache.scroll;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.all.recyclercache.MemStats;

/**
 * Abstract Recycler View Holder for items
 */
public abstract class ScrollItemHolder extends RecyclerView.ViewHolder {

    ScrollItemHolder(@NonNull View itemView) {
        super(itemView);

        // Count active instance of object.
        // MemStats.cntViewHolder.incrementAndGet();

        // Alternate way to count active instances of object.
        MemStats.addViewHolder(this);
    }

    abstract public void onBindViewHolder(ScrollItem item);

    @CallSuper
    public void onViewAttachedToWindow() {
        MemStats.cntViewAttached.incrementAndGet();
    }

    @CallSuper
    public void onViewDetachedFromWindow() {
        MemStats.cntViewAttached.decrementAndGet();

    }
}
