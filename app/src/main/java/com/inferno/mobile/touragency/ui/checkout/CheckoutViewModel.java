package com.inferno.mobile.touragency.ui.checkout;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.PaymentMethodType;
import com.inferno.mobile.touragency.repositories.AgencyRepo;

public class CheckoutViewModel extends ViewModel {
    private AgencyRepo agencyRepo;
    void init(){
        agencyRepo = AgencyRepo.getInstance();
    }
    public LiveData<CommonResponse<BookingModel>> checkout(String token,
                                                           int tourId,
                                                           PaymentMethodType methodType, String paymentCode) {
        return  agencyRepo.checkout(token, tourId, methodType,paymentCode);
    }
    public LiveData<CommonResponse<Boolean>>checkoutFromLocalDatabase(String token, int tourId){
        return agencyRepo.checkoutFromLocalDatabase(token,tourId);
    }
}
