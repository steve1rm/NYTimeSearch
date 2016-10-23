package me.androidbox.nytimessearch.newslist;

import javax.inject.Inject;

import me.androidbox.nytimessearch.di.DaggerInjector;
import me.androidbox.nytimessearch.model.NYTimesSearch;
import timber.log.Timber;

/**
 * Created by steve on 10/20/16.
 */

public class NewsListPresenterImp implements
        NewsListPresenterContract<NewsListViewContract>,
        NewsListModelContract.NewsListSearchListener {

    @Inject NewsListModelImp mNewsListModelImp;

    private NewsListViewContract view;

    public NewsListPresenterImp() {
        DaggerInjector.getAppComponent().inject(NewsListPresenterImp.this);

        if(mNewsListModelImp != null) {
            Timber.d("mNewsListModelImp != null");
        }
        else {
            Timber.e("mNewsListModelImp == null");
        }
    }

    @Override
    public void attachView(NewsListViewContract newsListView) {
        view = newsListView;
    }

    @Override
    public void detachView() {
        view = null;
        mNewsListModelImp.releaseResources();
    }

    /* Model <<-- Presenter */
    @Override
    public void getSearchRequest() {
        mNewsListModelImp.getSearchAllResults(NewsListPresenterImp.this);
    }

    @Override
    public void getSearchRequestQuery() {
        String query = view.getInputUserQuery();
        mNewsListModelImp.getSearchNewsDeskResults(query, NewsListPresenterImp.this);
    }

    /* Model -->> Presenter */
    @Override
    public void onSearchFailed(String errMessage) {
        /* Update view */
        view.displayErrorMessage(errMessage);
    }

    @Override
    public void onSearchSuccess(NYTimesSearch nyTimesSearch) {
        /* Update view */
        view.displayQueryResults(nyTimesSearch);
    }
}
