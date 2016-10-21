package me.androidbox.nytimessearch.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.nytimessearch.newslist.NewsListPresenterImp;

/**
 * Created by steve on 10/20/16.
 */

@Module
public class NewListPresenterModule {
    @Provides
    @Singleton
    public NewsListPresenterImp providesNewsListPresenter() {
        return new NewsListPresenterImp();
    }
}
