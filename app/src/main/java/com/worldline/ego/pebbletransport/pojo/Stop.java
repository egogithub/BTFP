package com.worldline.ego.pebbletransport.pojo;

/**
 * Created by a143210 on 21/11/2016.
 */

public class Stop {
    public String stopName;
    public int id;
    public float latitude;
    public float longitude;

    Stop (int id, String name, float latitude, float longitude) {
        this.stopName=name;
        this.id=id;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }
}
