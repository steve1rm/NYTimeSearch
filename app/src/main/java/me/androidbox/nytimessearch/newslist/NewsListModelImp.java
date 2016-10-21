package me.androidbox.nytimessearch.newslist;

import javax.inject.Inject;

import me.androidbox.nytimessearch.di.DaggerInjector;
import me.androidbox.nytimessearch.model.NYTimesSearch;
import me.androidbox.nytimessearch.restfulservice.NYTimesSearchService;
import me.androidbox.nytimessearch.utils.Constants;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by steve on 10/20/16.
 */

public class NewsListModelImp implements NewsListModelContract {
    @Inject NYTimesSearchService mNYTimesSearchService;
    private Subscription mSubscription;

    public NewsListModelImp() {
        DaggerInjector.getAppComponent().inject(NewsListModelImp.this);

        if(mNYTimesSearchService != null) {
            Timber.d("mNYTimesSearchService != null");
        }
    }

    @Override
    public void getSearchResults(final NewsListSearchListener newsListSearchListener) {
        mSubscription = mNYTimesSearchService.getNewsFeed(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NYTimesSearch>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onError");
                        newsListSearchListener.onSearchFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(NYTimesSearch nyTimesSearch) {
                        Timber.d("onNext: %s", nyTimesSearch.getStatus());
                        newsListSearchListener.onSearchSuccess(nyTimesSearch);
                    }
                });
    }

    @Override
    public void getSearchResultsQuery(String query, final NewsListSearchListener newsListSearchListener) {
        mSubscription = mNYTimesSearchService.getNewsQuery(Constants.API_KEY, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NYTimesSearch>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onError");
                        newsListSearchListener.onSearchFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(NYTimesSearch nyTimesSearch) {
                        Timber.d("onNext: %s" + nyTimesSearch.getStatus());
                        newsListSearchListener.onSearchSuccess(nyTimesSearch);
                    }
                });
    }

    @Override
    public void releaseResources() {
        if(mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
