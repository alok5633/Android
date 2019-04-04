package com.example.phonebook;

import android.app.Application;

import com.facebook.stetho.Stetho;


class MyAppliaction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}