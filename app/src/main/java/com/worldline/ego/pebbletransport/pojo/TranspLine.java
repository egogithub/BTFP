package com.worldline.ego.pebbletransport.pojo;

import java.io.Serializable;

/**
 * Created by a143210 on 2/12/2016.
 */

public class TranspLine implements Serializable{
    public String id;
    public String fromdestinationfr;
    public int fromdestinationid;
    public String todestinationfr;
    public int todestinationid;
    public String mode;
    public String fgcolor;
    public String bgcolor;

    public TranspLine(String id, String fromdestination, String todestination, String mode) {
        this.id = id;
        this.fromdestinationfr = fromdestination;
        this.todestinationfr = todestination;
        this.fromdestinationid = 0;
        this.todestinationid = 0;
        this.mode = mode;
        this.fgcolor = "";
        this.bgcolor = "";
    }
}
