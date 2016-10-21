package me.androidbox.nytimessearch.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.nytimessearch.newslist.NewsListModelImp;

/**
 * Created by steve on 10/20/16.
 */

@Module
public class NewsListModelModule {
    @Singleton
    @Provides
    public NewsListModelImp providesNewsListModel() {
        return new NewsListModelImp();
    }
}
