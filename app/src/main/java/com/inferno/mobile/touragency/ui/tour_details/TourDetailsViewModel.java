package com.inferno.mobile.touragency.ui.tour_details;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.DaySchedule;
import com.inferno.mobile.touragency.models.Place;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.models.TourDetails;
import com.inferno.mobile.touragency.repositories.AgencyRepo;

import java.util.ArrayList;

public class TourDetailsViewModel extends ViewModel {
    private AgencyRepo agencyRepo;

    void init() {
        agencyRepo = AgencyRepo.getInstance();
    }

    LiveData<CommonResponse<TourDetails>> getTourDetails(String token, int tourId) {
        return agencyRepo.getTourDetails(token, tourId);
    }

    ArrayList<ArrayList<DaySchedule>> reshapeData(TourDetails tourDetails) {
        ArrayList<ArrayList<DaySchedule>> daySchedules = new ArrayList<>();
        for (DaySchedule daySchedule : tourDetails.getSchedules()) {
            ArrayList<DaySchedule> list = getDaySchedule(0, daySchedule.getDay(), daySchedules);
            if (list.isEmpty())
                daySchedules.add(list);
            list.add(daySchedule);
        }
        return daySchedules;
    }

    private ArrayList<DaySchedule> getDaySchedule
            (int index, int day, ArrayList<ArrayList<DaySchedule>> daySchedules) {
        if (daySchedules.isEmpty() || index == daySchedules.size())
            return new ArrayList<>();
        if (daySchedules.get(index).get(0).getDay() == day)
            return daySchedules.get(index);
        return getDaySchedule(++index, day, daySchedules);
    }



    public LiveData<CommonResponse<Integer>> addToCart(String token, int tourId) {
        return agencyRepo.addToCart(token, tourId);
    }

    public LiveData<CommonResponse<Boolean>> checkIfInCart(int tourId) {
        return agencyRepo.checkIfInCart(tourId);
    }
}
