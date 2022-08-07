package com.inferno.mobile.touragency.ui.cart;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.ApprovedType;
import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.models.TourDetails;
import com.inferno.mobile.touragency.repositories.AgencyRepo;

import java.util.ArrayList;

public class CartViewModel extends ViewModel {
    private AgencyRepo agencyRepo;

    void init(){
        agencyRepo = AgencyRepo.getInstance();
    }
    LiveData<CommonResponse<ArrayList<Pair<Integer,Boolean>>>> getAllToursId(String token){
        return agencyRepo.getCartTours(token);
    }
    LiveData<CommonResponse<ArrayList<TourDetails>>> getTours(String token, ArrayList<Integer>ids){
        return agencyRepo.getToursById(token, ids);
    }

    public LiveData<CommonResponse<Boolean>> removeTour(String token, int id) {
        return agencyRepo.removeTourFromCart(token, id);
    }

    public LiveData<CommonResponse<Boolean>> cancelTour(String token, int tourId) {
        return agencyRepo.cancelTour(token, tourId);
    }
    public LiveData<CommonResponse<ArrayList<BookingModel>>> getApprovedTypes(String token,
                                                                              ArrayList<Integer>ids){
        return agencyRepo.getApprovedTypes(token,ids);
    }
}
