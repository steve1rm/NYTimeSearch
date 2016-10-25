package me.androidbox.nytimessearch.newslist;

import javax.inject.Inject;

import me.androidbox.nytimessearch.di.DaggerInjector;
import me.androidbox.nytimessearch.model.NYTimesSearch;
import timber.log.Timber;

import static me.androidbox.nytimessearch.R.string.sports;

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
    public void getSearchRequestQuery(boolean arts, boolean sport, boolean fashion, String date, String query, String sort) {
      //  mNewsListModelImp.getSearchNewsDeskResults(query, NewsListPresenterImp.this);
        StringBuilder sbNewsdesk = new StringBuilder();
        sbNewsdesk.append("news_desk:(\"");
        if(arts) {
            sbNewsdesk.append("Arts");
            sbNewsdesk.append(" ");
        }
        if(fashion) {
            sbNewsdesk.append("Fashion");
            sbNewsdesk.append(" ");
        }
        if(sport) {
            sbNewsdesk.append("Sports");
        }
        sbNewsdesk.append("\")");

        mNewsListModelImp.getSearchNewsDeskResults(sbNewsdesk.toString(), query, date, sort, NewsListPresenterImp.this);
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
