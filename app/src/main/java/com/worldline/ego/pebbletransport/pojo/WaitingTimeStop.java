package com.worldline.ego.pebbletransport.pojo;

import java.util.List;

/**
 * Created by a143210 on 9/12/2016.
 */

public class WaitingTimeStop {
    public String stopName;
    public double latitude;
    public double longitude;

    public List<WaitingTime> waitingTime;

    public WaitingTimeStop(String stopName, double latitude, double longitude, List<WaitingTime> waitingTime)  {
        this.stopName=stopName;
        this.latitude=latitude;
        this.longitude=longitude;
        this.waitingTime=waitingTime;
    }

    public WaitingTimeStop(String stopName, Position position, List<WaitingTime> waitingTime) {
        this(stopName, position.latitude, position.longitude, waitingTime);
    }

    public List<WaitingTime> getWaitingTimes() {
        return this.waitingTime;
    }
}
