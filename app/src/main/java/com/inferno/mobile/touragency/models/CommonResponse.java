package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommonResponse <T>{
    @SerializedName("code")
    private final int code;
    @SerializedName("messge")
    private final String msg;
    @SerializedName("data")
    private final T data;

    public CommonResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
