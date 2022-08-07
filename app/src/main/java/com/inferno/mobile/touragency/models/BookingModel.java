package com.inferno.mobile.touragency.models;

import com.google.gson.annotations.SerializedName;

public class BookingModel {
    @SerializedName("payment_code")
    private final String paymentCode;
    @SerializedName("payment_method")
    private final PaymentMethodType paymentMethod;
    @SerializedName("approved")
    private final ApprovedType approve;

    @SerializedName("seat_number")
    private final int seatNumber;
    @SerializedName("user")
    private final User user;

    @SerializedName("tour_id")
    private final int tourId;

    @SerializedName("updated_at")
    private final String updatedAt;

    public BookingModel(String paymentCode, PaymentMethodType paymentMethod,
                        ApprovedType approve, int seatNumber, User user, int tourId, String updatedAt) {
        this.paymentCode = paymentCode;
        this.paymentMethod = paymentMethod;
        this.approve = approve;
        this.seatNumber = seatNumber;
        this.user = user;
        this.tourId = tourId;
        this.updatedAt = updatedAt;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public ApprovedType getApprove() {
        return approve;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public User getUser() {
        return user;
    }

    public int getTourId() {
        return tourId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
