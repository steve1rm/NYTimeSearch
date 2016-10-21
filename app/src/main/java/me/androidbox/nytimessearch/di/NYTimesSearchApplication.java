package me.androidbox.nytimessearch.di;

import android.app.Application;
import timber.log.Timber;

/**
 * Created by steve on 10/20/16.
 */

public class NYTimesSearchApplication extends Application {
    private Application mApplication;

    private AppComponent mAppComponent;

    public void onCreate() {
        super.onCreate();

        /* Logging with timber */
        Timber.plant(new Timber.DebugTree());

        /* Application context */
        mApplication = NYTimesSearchApplication.this;

        /* Setup dependency injection */
    //    createAppComponent();
    }

    private void createAppComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
