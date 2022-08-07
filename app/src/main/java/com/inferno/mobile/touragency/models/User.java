package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String name;
    @SerializedName("email")
    private final String email;
    @SerializedName("phone")
    private final String phone;
    @SerializedName("last_name")
    private final String lastName;

    public User(int id, String name, String email, String phone, String lastName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLastName() {
        return lastName;
    }
}
