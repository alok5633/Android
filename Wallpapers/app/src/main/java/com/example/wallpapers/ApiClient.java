package com.example.wallpapers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    private static final String BASE_URL = "http://mapi.trycatchtech.com/v1/wallpaper_one/";
    private static Retrofit retrofit;
    private static NetworkService networkService;

    private static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(300, TimeUnit.SECONDS)
                            .readTimeout(300, TimeUnit.SECONDS)
                            .build();

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(okHttpClient)
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static NetworkService getNetworkService() {
        if (networkService == null) {
            synchronized (ApiClient.class) {
                if (networkService == null) {
                    networkService = getClient().create(NetworkService.class);
                }
            }
        }
        return networkService;
    }

}
