package me.androidbox.nytimessearch.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.nytimessearch.BuildConfig;
import me.androidbox.nytimessearch.restfulservice.NYTimesSearchService;
import me.androidbox.nytimessearch.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by steve on 10/20/16.
 */
@Module
public class RetrofitModule {
    private OkHttpClient httpLogging() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();

        /* Only use logging for RESTFul when running debug mode only */
        logger.setLevel((BuildConfig.DEBUG) ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient
                .Builder()
                .addInterceptor(logger)
                .build();
    }

    private Retrofit retrofit() {
        return new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpLogging())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public NYTimesSearchService providesNYTimesSearch() {
        return retrofit().create(NYTimesSearchService.class);
    }
}
