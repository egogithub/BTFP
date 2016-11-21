package com.worldline.ego.pebbletransport.pojo;

/**
 * Created by a143210 on 21/11/2016.
 */

public class Destination {
    public int line;
    public String endStop;
    public int destCode;
    public String mode; //Transport mode B=Bus, T=Tram, M=Metro

    public Destination(int line, String endStop, int destCode, String mode) {
        this.line=line;
        this.endStop=endStop;
        this.destCode=destCode;
        this.mode=mode;
    }
}
