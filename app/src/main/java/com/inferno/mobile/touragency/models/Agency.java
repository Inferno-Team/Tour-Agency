package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

public class Agency {
    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("city")
    private final City city;
    @SerializedName("img_url")
    private final String imgUrl;

    @SerializedName("location")
    private final String location;

    public Agency(int id, String name, City city, String imgUrl, String location) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.imgUrl = imgUrl;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getLocation() {
        return location;
    }
}
