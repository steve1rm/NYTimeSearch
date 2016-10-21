package me.androidbox.nytimessearch.di;

import javax.inject.Singleton;

import dagger.Component;
import me.androidbox.nytimessearch.newslist.NewsListModelImp;
import me.androidbox.nytimessearch.newslist.NewsListPresenterImp;
import me.androidbox.nytimessearch.newslist.NewsListView;

/**
 * Created by steve on 10/20/16.
 */
@Singleton
@Component(modules = {RetrofitModule.class, NewsListModelModule.class, NewListPresenterModule.class})
public interface AppComponent {
    void inject(NewsListModelImp target);
    void inject(NewsListPresenterImp target);
    void inject(NewsListView target);
}
