package com.inferno.mobile.touragency.services;

import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.models.ApprovedType;
import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.LoginResponse;
import com.inferno.mobile.touragency.models.PaymentMethodType;
import com.inferno.mobile.touragency.models.Place;
import com.inferno.mobile.touragency.models.SignupResponse;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.models.TourDetails;
import com.inferno.mobile.touragency.models.TourPaginate;

import java.lang.ref.Reference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    String BASE_IP = "http://192.168.43.114:8000/";
//    String BASE_IP = "http://192.168.1.7:8000/";

    @POST("api/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("api/logout")
    @FormUrlEncoded
    Call<Object> logout(@Header("Authorization") String token);

    @POST("api/signup")
    @FormUrlEncoded
    Call<SignupResponse> signUp(
            @Field("name") String name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("user_type") String userType
    );


    @GET("api/get_all_agancy")
    Call<CommonResponse<ArrayList<Agency>>> getAgencies(@Header("Authorization") String token);

    @GET("api/get_agancy_tours")
    Call<CommonResponse<ArrayList<Tour>>> getAgencyTours(@Header("Authorization") String token,
                                                         @Query("agency_id") int agencyId);

    @GET("api/get_tours")
    Call<CommonResponse<TourPaginate>> getTours(@Header("Authorization") String token,
                                                @Query("page") int pageId);

    @GET("api/get_tour_schedule")
    Call<CommonResponse<TourDetails>> getTourDetails(@Header("Authorization") String token,
                                                     @Query("id") int tourId);

    @GET("api/get_place_by_id/{id}")
    Call<Place> getPlaceById(@Header("Authorization") String token,
                             @Path("id") int placeId);

    @POST("api/book_on_tour/{id}")
    @FormUrlEncoded
    Call<CommonResponse<BookingModel>> bookOnTour(
            @Header("Authorization") String token,
            @Path("id") int tourId,
            @Field("payment_code") String paymentCode,
            @Field("payment_method") String paymentMethod
    );

    @POST("api/get_tours_by_ids/")
    @FormUrlEncoded
    Call<CommonResponse<ArrayList<TourDetails>>> getToursByIds(
            @Header("Authorization") String token,
            @Field("ids[]") ArrayList<Integer> ids);

    @POST("api/cancel_tour/")
    @FormUrlEncoded
    Call<CommonResponse<Boolean>> cancelTour(
            @Header("Authorization") String token,
            @Field("tour_id") int tourId);

    @POST("api/get_ids_approved/")
    @FormUrlEncoded
    Call<CommonResponse<ArrayList<BookingModel>>>
    getApprovedTypes(
            @Header("Authorization") String token,
            @Field("ids[]") ArrayList<Integer> ids
    );
}
