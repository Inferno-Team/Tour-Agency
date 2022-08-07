package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private final int code;
    @SerializedName(value = "message",alternate = "msg")
    private final String msg;
    @SerializedName("token")
    private final String token;
    @SerializedName("type")
    private final String type;

    public LoginResponse(int code, String msg, String token, String type) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.type = type;
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

    public String getType() {
        return type;
    }
}
