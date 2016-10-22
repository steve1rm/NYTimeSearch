package me.androidbox.nytimessearch.newslist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import me.androidbox.nytimessearch.R;
import me.androidbox.nytimessearch.di.DaggerInjector;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListView extends Fragment implements NewsListViewContract {
    @Inject NewsListPresenterImp mNewsListPresenterImp;

    public NewsListView() {
        // Required empty public constructor
    }

    public static NewsListView getNewInstance() {
        return new NewsListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.news_list_view, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaggerInjector.getAppComponent().inject(NewsListView.this);

        if(mNewsListPresenterImp != null) {
            Timber.d("mNewsListPresenterImp != null");
            mNewsListPresenterImp.attachView(NewsListView.this);
            mNewsListPresenterImp.getSearchRequestQuery();
        }
        else {
            Timber.e("mNewsListPresenterImp == null");
        }
    }

    @Override
    public String getInputUserQuery() {
        /* Get the value from the text field */
        return "Books";
    }

    @Override
    public void displayErrorMessage(String errMessage) {
        Toast.makeText(getActivity(), "Failed to get query results " + errMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayQueryResults() {
        Timber.d("displayQueryResults");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNewsListPresenterImp.detachView();
    }
}
