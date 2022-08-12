package com.inferno.mobile.touragency.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Token {

    private final static String SHARED_NAME = "tour.shared";
    private final static String TOKEN_FIELD = "token";
    private final static String IS_LOGGED_IN_FIELD = "is_logged_in";
    public  final static String MAP_TOKEN = "sk.eyJ1IjoibW9oYW1tZWRpc3NhIiwiYSI6ImNsNnF2cnlscTB2NGozam9hczYzdno1MHIifQ.Z3pbEDN-q41W_BYkK4htYQ";

    private static SharedPreferences shared(Context context) {
        return context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(SharedPreferences shared) {
        return shared.edit();
    }

    public static void LOG_IN(Context context, String token) {
        getEditor(shared(context))
                .putString(TOKEN_FIELD, token)
                .putBoolean(IS_LOGGED_IN_FIELD, true)
                .commit();
    }

    public static boolean IS_LOGGED_IN(Context context) {
        return shared(context).getBoolean(IS_LOGGED_IN_FIELD, false);
    }

    public static String GET_TOKEN(Context context) {
        return shared(context).getString(TOKEN_FIELD, null);
    }

    public static void LOG_OUT(Context context) {
        getEditor(shared(context))
                .remove(TOKEN_FIELD)
                .remove(IS_LOGGED_IN_FIELD)
                .commit();
    }

}
