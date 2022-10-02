// Dennis Lang
// Copyright LanDen Labs 2022
package com.all.recyclercache;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.all.recyclercache.scroll.ScrollAdapter;
import com.all.recyclercache.scroll.ScrollItem;
import com.all.recyclercache.scroll.ScrollItemMap;
import com.all.recyclercache.scroll.ScrollItemPlanet;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Simple Android app to demonstrate how and when RecyclerView caches ViewHolders.
 *
 * The key is how you handle ViewType
 */
public class MainActivity extends AppCompatActivity {
    private ScrollAdapter adapter;
    private ArrayList<ScrollItem> scrollItems;
    private RecyclerView recyclerView;
    private TextView header;
    private TextView footer;
    private CheckBox uniqueViewTypeCB;

    private static final int UPDATE_MILLI = 1000;
    private UpdateStats updateStats = new UpdateStats();
    private ArrayList<String> memoryStress = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white, getTheme()));
        setSupportActionBar(toolbar);

        header = findViewById(R.id.header);
        footer = findViewById(R.id.footer);
        ((RadioGroup)findViewById(R.id.rb_list_size)).setOnCheckedChangeListener(this::listSizeRb);

        uniqueViewTypeCB = findViewById(R.id.uniqueViewTypeCB);
        uniqueViewTypeCB.setOnCheckedChangeListener((view,checked) -> { refresh(); });
        findViewById(R.id.runGC).setOnClickListener( view -> { runGC(); });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        scrollItems = new ArrayList<>();
        adapter = new ScrollAdapter(this, scrollItems, uniqueViewTypeCB.isChecked());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        createListData(50);

        updateStats.run();
    }

    private void listSizeRb(RadioGroup radioGroup, int i) {
        int listSize = Integer.parseInt(radioGroup.findViewById(i).getTag().toString());
        createListData(listSize);
        refresh();
    }

    private void refresh() {
        // Clear out old view holders
        adapter.notifyItemRangeRemoved(0,adapter.getItemCount());
        recyclerView.getRecycledViewPool().clear();
        recyclerView.setAdapter(null);
        recyclerView.clearDisappearingChildren();
        recyclerView.removeAllViews();

        // Create new adapter
        adapter = new ScrollAdapter(this, scrollItems, uniqueViewTypeCB.isChecked());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        updateStats();
    }

    private void runGC() {
        Runtime.getRuntime().gc();
        updateStats();
    }

    private void updateStats() {
        Runtime.getRuntime().gc();
        memoryStress.add(makeMemoryStressString(2000));

        String statStr = String.format(Locale.US, "Holders=%d Attached=%d",
                MemRefs.activeObjects.get(), //  MemStats.cntViewHolder.get(),
                MemStats.cntViewAttached.get());
        String cacheStr = String.format(Locale.US,"List size=%d Cache Map=%d Planet=%d",
                scrollItems.size(),
                recyclerView.getRecycledViewPool().getRecycledViewCount(ScrollItem.ItemType.TYPE_MAP),
                recyclerView.getRecycledViewPool().getRecycledViewCount(ScrollItem.ItemType.TYPE_PLANET));
        String memStressStr = String.format(Locale.US, "Memory Stress count=%d", memoryStress.size());
        footer.setText(statStr + "\n" + cacheStr + "\n" + memStressStr);
    }

    private String makeMemoryStressString(int strLen) {
        byte[] array = new byte[strLen]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private void createListData(int listSize) {
        scrollItems.clear();
        scrollItems.add(new ScrollItemMap(0));
        scrollItems.add(new ScrollItemPlanet("Earth", 150, 10, 12750));
        scrollItems.add(new ScrollItemMap(1));
        scrollItems.add(new ScrollItemPlanet("Jupiter", 778, 26, 143000));
        scrollItems.add(new ScrollItemMap(2));
        scrollItems.add(new ScrollItemPlanet("Mars", 228, 4, 6800));
        scrollItems.add(new ScrollItemPlanet("Pluto", 5900, 1, 2320));
        scrollItems.add(new ScrollItemMap(3));
        scrollItems.add(new ScrollItemPlanet("Venus", 108, 9, 12750));
        scrollItems.add(new ScrollItemPlanet("Saturn", 1429, 11, 120000));
        scrollItems.add(new ScrollItemMap(4));
        scrollItems.add(new ScrollItemPlanet("Mercury", 58, 4, 4900));
        scrollItems.add(new ScrollItemPlanet("Neptune", 4500, 12, 50500));
        scrollItems.add(new ScrollItemMap(5));
        scrollItems.add(new ScrollItemPlanet("Uranus", 2870, 9, 52400));

        for (int idx = 6; scrollItems.size() < listSize; idx++) {
            scrollItems.add(new ScrollItemMap(idx));
        }
        int idx = scrollItems.size();
        while ( idx > listSize) {
            scrollItems.remove(--idx);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class UpdateStats implements Runnable {
        @Override
        public void run() {
            footer.removeCallbacks(this);   // If called from multiple users, only keep one.
            updateStats();
            footer.postDelayed(this, UPDATE_MILLI);
        }
    }
}
