package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TourPaginate {
    @SerializedName("next_page_url")
    private final String nextPage;
    @SerializedName("current_page")
    private final int currentPage;

    @SerializedName("data")
    private final ArrayList<Tour> tours;

    public TourPaginate(String nextPage, int currentPage, ArrayList<Tour> tours) {
        this.nextPage = nextPage;
        this.currentPage = currentPage;
        this.tours = tours;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public ArrayList<Tour> getTours() {
        return tours;
    }
}
