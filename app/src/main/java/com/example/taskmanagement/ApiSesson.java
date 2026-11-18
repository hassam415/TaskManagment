package com.example.taskmanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class ApiSesson {

    private static String access_Token;

    private static final String PRE_NAME = "AppSession";
    private static final String TOKEN_KEY = "access_token";

    // Retrofit interceptor ke liye
    public static String getAccess_Token() {
        return access_Token;
    }

    // Login ke waqt token save karne ke liye
    public static void setAccess_Token(Context context, String token) {

        access_Token = token;

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putString(TOKEN_KEY, token)
                .apply();
    }

    // App restart hone par saved token load karne ke liye
    public static String getAccess_Token(Context context) {

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);

        access_Token = sharedPreferences.getString(TOKEN_KEY, null);

        return access_Token;
    }

    // Logout ke waqt
    public static void clearSession(Context context) {

        access_Token = null;

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);

        sharedPreferences.edit().clear().apply();
    }
}