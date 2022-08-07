package com.inferno.mobile.touragency.repositories;

import static com.inferno.mobile.touragency.TourApplication.cartService;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.touragency.TourApplication;
import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.PaymentMethodType;
import com.inferno.mobile.touragency.models.Place;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.models.TourDetails;
import com.inferno.mobile.touragency.models.TourPaginate;
import static com.inferno.mobile.touragency.TourApplication.cartService;
import java.util.ArrayList;


public class AgencyRepo {
    private final static String TAG = AgencyRepo.class.getName();

    private static AgencyRepo instance;


    public static AgencyRepo getInstance() {
        if (instance == null)
            instance = new AgencyRepo();
        return instance;
    }

    private AgencyRepo() {
    }

    public MutableLiveData<CommonResponse<ArrayList<Agency>>> getAgencies(String token) {
        MutableLiveData<CommonResponse<ArrayList<Agency>>> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.getAgencies("Bearer " + token)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "getAgencies"));

        return liveData;
    }

    public MutableLiveData<CommonResponse<ArrayList<Tour>>> getAgencyTours(String token, int agencyId) {
        MutableLiveData<CommonResponse<ArrayList<Tour>>> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.getAgencyTours("Bearer " + token, agencyId)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "getAgencyTours"));
        return liveData;
    }

    public MutableLiveData<CommonResponse<TourPaginate>> getTours(String token, int page) {
        MutableLiveData<CommonResponse<TourPaginate>> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.getTours("Bearer " + token, page)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "getTours"));
        return liveData;
    }

    public MutableLiveData<CommonResponse<TourDetails>> getTourDetails(String token, int tourId) {
        MutableLiveData<CommonResponse<TourDetails>> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.getTourDetails("Bearer " + token, tourId)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "getTourDetails"));
        return liveData;
    }

    public MutableLiveData<Place> getPlaceById(String token, int id) {
        MutableLiveData<Place> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.getPlaceById("Bearer " + token, id)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "getPlaceByID"));
        return liveData;
    }


    public MutableLiveData<CommonResponse<Integer>> addToCart(String token, int tourId) {
        MutableLiveData<CommonResponse<Integer>> liveData = new MutableLiveData<>();
        int insertId = cartService.addToCart(token, tourId);
        if (insertId > 0) {
            liveData.setValue(new CommonResponse<>(200, "Tour added to cart", insertId));
        } else {
            liveData.setValue(new CommonResponse<>(300, "Tour already in cart", insertId));
        }
        return liveData;
    }

    public MutableLiveData<CommonResponse<Boolean>> checkIfInCart(int tourId) {
        MutableLiveData<CommonResponse<Boolean>> liveData = new MutableLiveData<>();
        boolean isInCart = cartService.isInCart(tourId);
        liveData.setValue(new CommonResponse<>(isInCart ? 200 : 300, "Tour in cart", isInCart));
        return liveData;
    }

    public MutableLiveData<CommonResponse<Integer>> getTourFromCart(String token, int tourId) {
        MutableLiveData<CommonResponse<Integer>> liveData = new MutableLiveData<>();
        int tourFromCart = cartService.getTourFromCart(token, tourId);
        liveData.setValue(new CommonResponse<>(tourFromCart > 0 ? 200 : 300,
                "Tour in cart", tourFromCart));
        return liveData;
    }

    public MutableLiveData<CommonResponse<Boolean>> removeTourFromCart(String token, int tourId) {
        MutableLiveData<CommonResponse<Boolean>> liveData = new MutableLiveData<>();
        boolean isRemoved = cartService.removeFromCart(token, tourId);
        liveData.setValue(new CommonResponse<>(isRemoved ? 200 : 300,
                "Tour removed from cart", isRemoved));
        return liveData;
    }

    public MutableLiveData<CommonResponse<ArrayList<Pair<Integer, Boolean>>>> getCartTours(String token) {
        MutableLiveData<CommonResponse<ArrayList<Pair<Integer, Boolean>>>> liveData = new MutableLiveData<>();
        ArrayList<Pair<Integer, Boolean>> tours = cartService.getCart(token);
        liveData.setValue(new CommonResponse<>(tours.size() > 0 ? 200 : 300,
                "Tours in cart", tours));
        return liveData;
    }

    public LiveData<CommonResponse<ArrayList<TourDetails>>> getToursById(String token,
                                                                         ArrayList<Integer> ids) {
        MutableLiveData<CommonResponse<ArrayList<TourDetails>>> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.getToursByIds("Bearer " + token, ids)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "getToursById"));
        return liveData;
    }

    public MutableLiveData<CommonResponse<BookingModel>> checkout(String token, int tourId,
                                                                  PaymentMethodType methodType,
                                                                  String paymentCode) {
        MutableLiveData<CommonResponse<BookingModel>> liveData = new MutableLiveData<>();

        TourApplication.retrofitAPI.bookOnTour("Bearer " + token, tourId
                        , paymentCode, methodType.name())
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "checkout"));
        return liveData;
    }

    public MutableLiveData<CommonResponse<Boolean>> checkoutFromLocalDatabase(String token, int tourId) {
        MutableLiveData<CommonResponse<Boolean>> liveData = new MutableLiveData<>();

        boolean isBooked = cartService.checkOut(token, tourId);
        liveData.setValue(new CommonResponse<>(isBooked ? 200 : 300,
                "Tour checked out", isBooked));
        return liveData;
    }

    public MutableLiveData<CommonResponse<Boolean>> checkIfCheckedFromLocalDatabase
            (String token, int tourId) {
        MutableLiveData<CommonResponse<Boolean>> liveData = new MutableLiveData<>();

        int isBooked = cartService.checkIfCheckedOut(token, tourId);
        liveData.setValue(new CommonResponse<>(isBooked == 0 ? 200 : 300,
                "Tour checked out", isBooked == 0));
        return liveData;
    }

    public MutableLiveData<CommonResponse<Boolean>> cancelTour(String token, int tourId) {
        MutableLiveData<CommonResponse<Boolean>> liveData = new MutableLiveData<>();

        boolean isCanceled = cartService.removeFromCart(token, tourId);
        TourApplication.retrofitAPI.cancelTour("Bearer " + token, tourId)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "cancelTour"));
        return liveData;
    }

    public MutableLiveData<CommonResponse<ArrayList<BookingModel>>>
    getApprovedTypes(String token, ArrayList<Integer> ids) {
        MutableLiveData<CommonResponse<ArrayList<BookingModel>>> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.getApprovedTypes("Bearer " + token, ids)
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "getApprovedTypes"));
        return liveData;
    }
}
