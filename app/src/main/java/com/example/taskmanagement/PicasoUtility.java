package com.example.taskmanagement;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PicasoUtility {


        private static Picasso picasso;

        private static Picasso getPicasso(Context context) {

            if (picasso == null) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(chain -> {

                            String token = ApiSesson.getAccess_Token();

                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .addHeader("Accept", "application/json")
                                    .build();

                            return chain.proceed(request);
                        })
                        .build();

                picasso = new Picasso.Builder(context.getApplicationContext())
                        .downloader(new OkHttp3Downloader(client))
                        .build();
            }

            return picasso;
        }

        public static void loadImage(Context context, String uri, ImageView imageView) {

            getPicasso(context)
                    .load(uri)
                    .into(imageView);
        }
    }


