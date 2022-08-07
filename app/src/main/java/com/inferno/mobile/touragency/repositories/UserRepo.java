package com.inferno.mobile.touragency.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.touragency.TourApplication;
import com.inferno.mobile.touragency.models.LoginResponse;
import com.inferno.mobile.touragency.models.SignupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {

    private static UserRepo instance;
    private final static String TAG = UserRepo.class.getName();

    public static UserRepo getInstance() {
        if (instance == null)
            instance = new UserRepo();
        return instance;
    }

    private UserRepo() {
    }

    public MutableLiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.login(email, password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        liveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
        return liveData;
    }


    public MutableLiveData<SignupResponse> signup(String name, String lastName,
                                                  String email, String phone,
                                                  String password) {
        MutableLiveData<SignupResponse> liveData = new MutableLiveData<>();
        TourApplication.retrofitAPI.signUp(name, lastName, email,
                phone, password, "user")
                .enqueue(new RetrofitCallBack<>(liveData, TAG, "signup"));
        return liveData;
    }
}
