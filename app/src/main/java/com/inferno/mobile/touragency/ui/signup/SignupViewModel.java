package com.inferno.mobile.touragency.ui.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.SignupResponse;
import com.inferno.mobile.touragency.repositories.UserRepo;

public class SignupViewModel extends ViewModel {
    private UserRepo userRepo;

    void init() {
        userRepo = UserRepo.getInstance();
    }

    LiveData<SignupResponse> signup(String name, String lastName, String email,
                                    String phone, String password) {
        return userRepo.signup(name, lastName, email, phone, password);
    }

}
