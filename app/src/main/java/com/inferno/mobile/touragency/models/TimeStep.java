package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeStep implements Serializable {
    @SerializedName("start")
    private final String start;
    @SerializedName("end")
    private final String end;

    public TimeStep(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
