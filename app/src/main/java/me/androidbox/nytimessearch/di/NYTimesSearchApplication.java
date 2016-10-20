package me.androidbox.nytimessearch.di;


import android.app.Application;

/**
 * Created by steve on 10/20/16.
 */

public class NYTimesSearchApplication extends Application {
    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();


        sApplication = NYTimesSearchApplication.this;
    }
}
