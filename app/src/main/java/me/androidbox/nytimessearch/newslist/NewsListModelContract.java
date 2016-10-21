package me.androidbox.nytimessearch.newslist;

/**
 * Created by steve on 10/20/16.
 */

public interface NewsListModelContract {
    interface NewsListSearchListener {
        void onSearchFailed(String errMessage);
        void onSearchSuccess();
    }

    void getSearchResults(String query, NewsListSearchListener newsListSearchListener);

    void releaseResources();
}
