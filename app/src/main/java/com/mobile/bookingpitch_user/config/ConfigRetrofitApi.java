package com.mobile.bookingpitch_user.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofitApi {
    private static Gson gson = new GsonBuilder().setLenient().create();

    public static Retrofit getInstance() {
        Retrofit retrofit = null;
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "auth-value");// <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient.readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder().baseUrl(Constant.DOMAIN)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getVersion() {
        Retrofit retrofit = null;
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "auth-value"); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient.readTimeout(120, TimeUnit.SECONDS).connectTimeout(120, TimeUnit.SECONDS).build();
            retrofit = new Retrofit.Builder().baseUrl(Constant.DOMAIN)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getInstance_BooKingPitch() {
        Retrofit retrofit = null;
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "auth-value");// <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient.readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder().baseUrl(Constant.DOMAIN_BookingPitch)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getVersion_BookingPitch() {
        Retrofit retrofit = null;
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "auth-value"); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient.readTimeout(120, TimeUnit.SECONDS).connectTimeout(120, TimeUnit.SECONDS).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.DOMAIN_BookingPitch)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
