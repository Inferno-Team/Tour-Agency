package com.inferno.mobile.touragency.ui.agency_tours;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.repositories.AgencyRepo;

import java.util.ArrayList;

public class AgencyTourViewModel extends ViewModel {
    private AgencyRepo agencyRepo;

    void init() {
        agencyRepo = AgencyRepo.getInstance();
    }

    LiveData<CommonResponse<ArrayList<Tour>>> getAgencyTours(String token, int agencyId) {
        return agencyRepo.getAgencyTours(token, agencyId);
    }

}
