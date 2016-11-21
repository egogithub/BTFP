package com.worldline.ego.pebbletransport.pojo;

import java.util.List;

/**
 * Created by a143210 on 21/11/2016.
 */

public class ItiStop extends Stop {
    public List<Destination> dest;

    public ItiStop(int id, String name, float latitude, float longitude, List<Destination> dest) {
        super(id, name, latitude, longitude);
        this.dest=dest;
    }
}
