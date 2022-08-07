package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DaySchedule implements Serializable {
    @SerializedName("id")
    private final int id;
    @SerializedName("day")
    private final int day;
    @SerializedName("time_step")
    private final TimeStep timeStep;
    @SerializedName("place")
    private final Place place;

    public DaySchedule(int id, int day, TimeStep timeStep, Place place) {
        this.id = id;
        this.day = day;
        this.timeStep = timeStep;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public TimeStep getTimeStep() {
        return timeStep;
    }

    public Place getPlace() {
        return place;
    }
}
