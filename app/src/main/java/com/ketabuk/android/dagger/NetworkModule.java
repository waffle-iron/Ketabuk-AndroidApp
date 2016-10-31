package com.ketabuk.android.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Karim Mostafa on 10/11/16.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    okhttp3.Request.Builder provideRequestBuilder(){
        return new okhttp3.Request.Builder();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().create();
    }
}
