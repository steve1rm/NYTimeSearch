package me.androidbox.nytimessearch.newslist;

import me.androidbox.nytimessearch.model.NYTimesSearch;

/**
 * Created by steve on 10/20/16.
 */

public interface NewsListViewContract {
    String getInputUserQuery();
    void displayErrorMessage(String errMessage);
    void displayQueryResults(NYTimesSearch nyTimesSearch);
}
