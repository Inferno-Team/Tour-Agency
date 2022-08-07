package com.inferno.mobile.touragency.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallBack<T> implements Callback<T> {
    private final MutableLiveData<T> liveData;
    private final String TAG;
    private final String msg;

    public RetrofitCallBack(MutableLiveData<T> liveData, String TAG, String msg) {
        this.liveData = liveData;
        this.TAG = TAG;
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful() && response.body() != null)
            liveData.postValue(response.body());
        else Log.e(TAG, msg + "@onResponse #" + response.code());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG, msg + "@onFailure", t);
    }
}
