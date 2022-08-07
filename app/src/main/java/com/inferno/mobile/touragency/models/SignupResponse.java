package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

public class SignupResponse {
    @SerializedName("code")
    private final int code;
    @SerializedName("message")
    private final String msg;
    @SerializedName("token")
    private final String token;

    public SignupResponse(int code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getToken() {
        return token;
    }
}
