package com.inferno.mobile.touragency.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.touragency.models.LoginResponse;
import com.inferno.mobile.touragency.repositories.UserRepo;

public class LoginViewModel extends ViewModel {
    private UserRepo loginRepo;

    void init(){
        loginRepo = UserRepo.getInstance();
    }
    LiveData<LoginResponse>login(String email,String password){
        return loginRepo.login(email, password);
    }
}
