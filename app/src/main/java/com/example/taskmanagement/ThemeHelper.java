package com.example.taskmanagement;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import org.checkerframework.checker.units.qual.C;

public class ThemeHelper {
    private static final  String PRE_NAME="AppSetting";
    private static final String THEME_KEY="app_theme";
    public static void savetheme(Context context,int thememode){
        SharedPreferences preferences=context.getSharedPreferences(PRE_NAME, MODE_PRIVATE);
        preferences.edit().putInt(THEME_KEY,thememode).apply();
    }
    public static int getTheme(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences(PRE_NAME,MODE_PRIVATE);
        return sharedPreferences.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
    public static void applytheme(Context context){

        AppCompatDelegate.setDefaultNightMode(getTheme(context));
    }
}

