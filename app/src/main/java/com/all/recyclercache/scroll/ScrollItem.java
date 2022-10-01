// Dennis Lang
// Copyright LanDen Labs 2022
package com.all.recyclercache.scroll;

import android.view.ViewGroup;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Abstract scrollable item
 */
public abstract class ScrollItem {

    abstract public ScrollItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position);

    @ItemType
    abstract public int getItemType();

    // Possible item types
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ItemType.TYPE_PLANET, ItemType.TYPE_MAP})
    public @interface ItemType {
        int TYPE_PLANET = 10;
        int TYPE_MAP = 20;
    }
}
