package com.inferno.mobile.touragency.ui.place;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.Place;
import com.inferno.mobile.touragency.repositories.AgencyRepo;

public class PlaceViewModel extends ViewModel {
    private AgencyRepo agencyRepo;
    void init(){
        agencyRepo = AgencyRepo.getInstance();
    }
    LiveData<Place>getPlaceById(String token,int placeId){
        return agencyRepo.getPlaceById(token, placeId);
    }

}
