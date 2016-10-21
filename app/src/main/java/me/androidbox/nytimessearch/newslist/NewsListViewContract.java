package me.androidbox.nytimessearch.newslist;

/**
 * Created by steve on 10/20/16.
 */

public interface NewsListViewContract {
    String getInputUserQuery();
    void displayErrorMessage(String errMessage);
    void displayQueryResults();
}
