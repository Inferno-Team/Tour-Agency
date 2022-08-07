package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Tour implements Serializable {

    @SerializedName("id")
    private final int id;
    @SerializedName("city")
    private final City city;
    @SerializedName("cost")
    private final double cost;
    @SerializedName("seat_count")
    private final int seatCount;
    @SerializedName("start_at")
    private final Date startAt;
    @SerializedName("end_at")
    private final Date endAt;
    @SerializedName("created_at")
    private final String createAt;

    public Tour(int id, City city, double cost, int seatCount,
                Date startAt, Date endAt, String createAt) {
        this.id = id;
        this.city = city;
        this.cost = cost;
        this.seatCount = seatCount;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public double getCost() {
        return cost;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public String getCreateAt() {
        return createAt;
    }


}
