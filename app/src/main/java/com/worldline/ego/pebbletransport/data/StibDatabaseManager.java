package com.worldline.ego.pebbletransport.data;

import com.worldline.ego.pebbletransport.pojo.Destination;
import com.worldline.ego.pebbletransport.pojo.TranspLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a143210 on 24/11/2016.
 */

public class StibDatabaseManager {
    public static synchronized List<TranspLine> getStoredLines() {
        List<TranspLine> tlines = new ArrayList<TranspLine>();

        return tlines;
    }
}
