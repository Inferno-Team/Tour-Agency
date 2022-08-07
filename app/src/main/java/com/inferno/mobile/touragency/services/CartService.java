package com.inferno.mobile.touragency.services;

import android.util.Pair;

import com.inferno.mobile.touragency.models.Tour;

import java.util.ArrayList;

public interface CartService {
    int addToCart(String token, int tourId);

    boolean removeFromCart(String token, int tourId);

    boolean isInCart(int tourId);

    Integer getTourFromCart(String token, int tourId);

    ArrayList<Pair<Integer,Boolean>> getCart(String token);

    int checkIfCheckedOut(String token,int tourId);
    boolean checkOut(String token,int tourId);

}
