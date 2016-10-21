package me.androidbox.nytimessearch.di;

/**
 * Created by steve on 10/20/16.
 */

public class DaggerInjector {
    private static AppComponent sAppComponent =
            DaggerAppComponent
                    .builder()
                    .retrofitModule(new RetrofitModule())
                    .newsListModelModule(new NewsListModelModule())
                    .newListPresenterModule(new NewListPresenterModule())
                    .build();

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
