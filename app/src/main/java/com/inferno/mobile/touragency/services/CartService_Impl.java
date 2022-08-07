package com.inferno.mobile.touragency.services;

import android.util.Pair;

import com.inferno.mobile.touragency.models.Tour;

import java.util.ArrayList;

public class CartService_Impl implements CartService {
    private final LocalDatabase database;

    public CartService_Impl(LocalDatabase database) {
        this.database = database;
    }

    @Override
    public int addToCart(String token, int tourId) {
        return (int) database.addToCart(token, tourId);
    }

    @Override
    public boolean removeFromCart(String token, int tourId) {
        return database.removeFromCart(tourId);
    }

    @Override
    public boolean isInCart(int tourId) {
        return database.checkIfInCart(tourId);
    }

    @Override
    public Integer getTourFromCart(String token, int tourId) {
        return database.findTourInCart( tourId);
    }

    @Override
    public ArrayList<Pair<Integer,Boolean>> getCart(String token) {
        return database.getAllFromCart();
    }

    @Override
    public int checkIfCheckedOut(String token, int tourId) {
        return database.checkIfCheckedOut(tourId);
    }

    @Override
    public boolean checkOut(String token, int tourId) {
        return database.checkOut(tourId);
    }
}
