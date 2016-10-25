package me.androidbox.nytimessearch.newslist;

import me.androidbox.nytimessearch.model.NYTimesSearch;

/**
 * Created by steve on 10/20/16.
 */

public interface NewsListModelContract {
    interface NewsListSearchListener {
        void onSearchFailed(String errMessage);
        void onSearchSuccess(NYTimesSearch nyTimesSearch);
    }

    void getSearchAllResults(NewsListSearchListener newsListSearchListener);
    void getSearchNewsDeskResults(String newsdesk, String query, String date, String sort, NewsListSearchListener newsListSearchListener);
    void releaseResources();
}
