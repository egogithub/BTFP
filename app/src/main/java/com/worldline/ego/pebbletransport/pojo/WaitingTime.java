package com.worldline.ego.pebbletransport.pojo;

import java.util.List;

/**
 * Created by a143210 on 9/12/2016.
 */

public class WaitingTime {
    public String line;
    public String lineMode;
    public int minutes;
    public String destination;
    public String message;

    public WaitingTime(String line, String lineMode, int minutes, String dest, String msg) {
        this.line=line;
        this.lineMode=lineMode;
        this.minutes=minutes;
        this.destination=dest;
        this.message=msg;
    }
}
