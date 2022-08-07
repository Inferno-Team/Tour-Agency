package com.inferno.mobile.touragency.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.TourPaginate;
import com.inferno.mobile.touragency.repositories.AgencyRepo;
public class HomeViewModel extends ViewModel {
    private AgencyRepo agencyRepo;

    void init() {
        agencyRepo = AgencyRepo.getInstance();
    }


    LiveData<CommonResponse<TourPaginate>> getTours(String token,int page) {
        return agencyRepo.getTours(token, page);
    }
}
