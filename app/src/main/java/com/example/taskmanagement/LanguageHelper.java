
package com.example.taskmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageHelper {

    public static void saveLanguage(Context context, String language) {

        SharedPreferences preferences =
                context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);

        preferences.edit()
                .putString("language", language)
                .apply();
    }

    public static String getLanguage(Context context) {

        SharedPreferences preferences =
                context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);

        return preferences.getString("language", "en");
    }

    public static Context changeLanguage(Context context, String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration =
                new Configuration(context.getResources().getConfiguration());

        configuration.setLocale(locale);

        return context.createConfigurationContext(configuration);
    }
    public static Context applyLanguage(Context context) {

        String language = getLanguage(context);

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration =
                new Configuration(context.getResources().getConfiguration());

        configuration.setLocale(locale);

        // Layout direction hamesha Left to Right
        configuration.setLayoutDirection(new Locale("en"));

        return context.createConfigurationContext(configuration);
    }
}
