package com.worldline.ego.pebbletransport.dummy;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        Log.d("DummyContent", "Adding lines ");
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.lineid, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "First Stop  " + position, "Last Stop" + position);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String lineid;
        public final String destfrom;
        public final String destto;

        public DummyItem(String lineid, String destfrom, String destto) {
            this.lineid = lineid;
            this.destfrom = destfrom;
            this.destto = destto;
        }



        @Override
        public String toString() {
            return lineid;
        }

    }
}
