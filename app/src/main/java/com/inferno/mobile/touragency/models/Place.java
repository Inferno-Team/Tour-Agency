package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Place implements Serializable {
    private final int id;
    private final String name;
    private final String address;
    private final BigDecimal lat;
    private final BigDecimal lng;
    private final String disc;
    @SerializedName("img_url")
    private final String imgUrl;

    public Place(int id, String name, String address, BigDecimal lat,
                 BigDecimal lng, String disc, String imgUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.disc = disc;
        this.imgUrl = imgUrl;
    }

    public Place() {
        this(-1, "", "", BigDecimal.ZERO, BigDecimal.ZERO, "", "");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public String getDisc() {
        return disc;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
