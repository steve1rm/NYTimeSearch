package me.androidbox.nytimessearch.newslist;

/**
 * Created by steve on 10/20/16.
 */

public interface NewsListPresenterContract<NewsListView>  {
    /* Presenter <<-- View */
    void attachView(NewsListView view);
    void detachView();
    void getSearchRequest();
    void getSearchRequestQuery(boolean arts, boolean sport, boolean fashion, String date, String query, String sort);
}
