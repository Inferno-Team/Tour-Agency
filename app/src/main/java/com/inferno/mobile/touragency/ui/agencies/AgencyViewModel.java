package com.inferno.mobile.touragency.ui.agencies;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.repositories.AgencyRepo;

import java.util.ArrayList;

public class AgencyViewModel extends ViewModel {
    private AgencyRepo agencyRepo;
    void init(){
        agencyRepo = AgencyRepo.getInstance();
    }
    LiveData<CommonResponse<ArrayList<Agency>>> getAgencies(String token){
        return agencyRepo.getAgencies(token);
    }
}
