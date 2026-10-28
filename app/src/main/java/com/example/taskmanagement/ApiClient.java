package com.example.taskmanagement;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String TAG = "ApiClient";
    private static final String Base_Url = "http://192.168.1.14:8005/api/v1/";

    private static Retrofit retrofit;
    private static ApiService apiService;

    /**
     * Returns the singleton Retrofit instance configured with OkHttpClient,
     * default headers, dynamic Bearer token, and full request/response logging.
     */
    public static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new HeaderAndAuthInterceptor())
                    .addInterceptor(new LoggingInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Convenience method to get ApiService directly.
     */
    public static synchronized ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    /**
     * Resets the Retrofit client instance if needed (e.g. changing base URL).
     */
    public static synchronized void resetClient() {
        retrofit = null;
        apiService = null;
    }

    /**
     * Interceptor to automatically attach standard headers and Bearer token
     * from ApiSesson if not already explicitly provided.
     */
    private static class HeaderAndAuthInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder();

            // Default Accept header
            if (originalRequest.header("Accept") == null) {
                requestBuilder.header("Accept", "application/json");
            }

            // Default Content-Type header if body is present
            if (originalRequest.body() != null && originalRequest.header("Content-Type") == null) {
                requestBuilder.header("Content-Type", "application/json");
            }

            // Automatic Bearer Token injection from ApiSesson
            String existingAuth = originalRequest.header("Authorization");
            if (existingAuth == null || existingAuth.trim().isEmpty()) {
                String token = ApiSesson.getAccess_Token();
                if (token != null && !token.trim().isEmpty()) {
                    requestBuilder.header("Authorization", "Bearer " + token.trim());
                }
            }

            return chain.proceed(requestBuilder.build());
        }
    }

    /**
     * Interceptor that logs request method, URL, headers, request body,
     * status code, execution duration, response headers, and response body.
     */
    private static class LoggingInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();

            // 1. Log Request Info
            String method = request.method();
            String url = request.url().toString();

            Log.d(TAG, "==================== [REQUEST START] ====================");
            Log.d(TAG, "--> " + method + " " + url);

            // Log Request Headers
            Headers requestHeaders = request.headers();
            if (requestHeaders.size() > 0) {
                Log.d(TAG, "--> Headers:");
                for (int i = 0; i < requestHeaders.size(); i++) {
                    Log.d(TAG, "    " + requestHeaders.name(i) + ": " + requestHeaders.value(i));
                }
            }

            // Log Request Body
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                try {
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);

                    Charset charset = StandardCharsets.UTF_8;
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        Charset c = contentType.charset(StandardCharsets.UTF_8);
                        if (c != null) {
                            charset = c;
                        }
                    }

                    String bodyString = buffer.readString(charset);
                    Log.d(TAG, "--> Request Body:");
                    logLarge(TAG, bodyString);
                } catch (Exception e) {
                    Log.e(TAG, "--> Error reading request body: " + e.getMessage());
                }
            } else {
                Log.d(TAG, "--> Request Body: (empty)");
            }

            // 2. Execute Request and Measure Time
            long startTime = System.nanoTime();
            Response response;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                Log.e(TAG, "<-- HTTP FAILED: " + method + " " + url + " | Exception: " + e.getMessage());
                Log.d(TAG, "==================== [REQUEST FAILED] ====================");
                throw e;
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

            // 3. Log Response Info
            int code = response.code();
            String message = response.message();

            Log.d(TAG, "-------------------- [RESPONSE] --------------------");
            Log.d(TAG, "<-- " + code + " " + message + " (" + tookMs + "ms) " + url);

            // Log Response Headers
            Headers responseHeaders = response.headers();
            if (responseHeaders.size() > 0) {
                Log.d(TAG, "<-- Headers:");
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.d(TAG, "    " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
            }

            // Log Response Body without consuming it
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                try {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body
                    Buffer buffer = source.getBuffer();

                    Charset charset = StandardCharsets.UTF_8;
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        Charset c = contentType.charset(StandardCharsets.UTF_8);
                        if (c != null) {
                            charset = c;
                        }
                    }

                    if (responseBody.contentLength() != 0) {
                        // Clone the buffer so the original stream remains intact for Retrofit
                        String responseBodyString = buffer.clone().readString(charset);
                        Log.d(TAG, "<-- Response Body:");
                        logLarge(TAG, responseBodyString);
                    } else {
                        Log.d(TAG, "<-- Response Body: (empty)");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "<-- Error reading response body: " + e.getMessage());
                }
            } else {
                Log.d(TAG, "<-- Response Body: (null)");
            }

            Log.d(TAG, "==================== [REQUEST END] ====================");

            return response;
        }
    }

    /**
     * Splits large log messages to prevent Android Logcat truncation (4000 char limit).
     */
    private static void logLarge(String tag, String message) {
        if (message == null || message.isEmpty()) {
            return;
        }

        String[] lines = message.split("\n");
        for (String line : lines) {
            if (line.length() <= 3000) {
                Log.d(tag, line);
            } else {
                int maxLogSize = 3000;
                for (int i = 0; i <= line.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = Math.min((i + 1) * maxLogSize, line.length());
                    if (start < end) {
                        Log.d(tag, line.substring(start, end));
                    }
                }
            }
        }
    }
}
