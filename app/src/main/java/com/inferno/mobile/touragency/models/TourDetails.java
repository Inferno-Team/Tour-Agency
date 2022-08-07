package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class TourDetails extends Tour {

    @SerializedName("tour_place_time")
    private final ArrayList<DaySchedule> schedules;
    @SerializedName("agancy")
    private final Agency agency;

    public TourDetails(int id, City city, double cost, int seatCount,
                       Date startAt, Date endAt, String createAt,
                       ArrayList<DaySchedule> schedules, Agency agency) {
        super(id, city, cost, seatCount, startAt, endAt, createAt);
        this.schedules = schedules;
        this.agency = agency;
    }

    public ArrayList<DaySchedule> getSchedules() {
        return schedules;
    }

    public Agency getAgency() {
        return agency;
    }
}
