package me.androidbox.nytimessearch.newslist;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;

import java.util.List;
import java.util.concurrent.Delayed;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.nytimessearch.R;
import me.androidbox.nytimessearch.di.DaggerInjector;
import me.androidbox.nytimessearch.model.NYTimesSearch;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListView extends Fragment implements
        DatePickerDialogFragment.DatePickerDialogHandler,
        NewsListViewContract {

    /* Store a member variable for the listener */
    private EndlessRecyclerViewScrollListener scrollListener;

    @Inject NewsListPresenterImp mNewsListPresenterImp;

    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.rvNewsFeed) RecyclerView mRvNewsFeed;
    @BindView(R.id.tbNYTimes) Toolbar mToobar;
    @BindView(R.id.cbArts) CheckBox mCbArts;
    @BindView(R.id.cbFashionStyle) CheckBox mCbFashion;
    @BindView(R.id.cbSports) CheckBox mCbSports;
    @BindView(R.id.etSearch) EditText mTvSearch;

    private Unbinder mUnbinder;
    private NewsFeedAdapter mNewsFeedAdapter;

    public NewsListView() {
        // Required empty public constructor
    }

    public static NewsListView getNewInstance() {
        return new NewsListView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.news_list_view, container, false);

        mUnbinder = ButterKnife.bind(NewsListView.this, view);

        setupToolbar();
        setupAdapter();

        return view;
    }

    private void setupToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mToobar);
    }

    private void setupAdapter() {

        mNewsFeedAdapter = new NewsFeedAdapter(new NYTimesSearch(), getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRvNewsFeed.setLayoutManager(staggeredGridLayoutManager);
        mRvNewsFeed.setAdapter(mNewsFeedAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(final int page, final int totalItemsCount, RecyclerView view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Timber.d("onLoadMore page: %d totalItemsCount %d", page, totalItemsCount);
                        mNewsListPresenterImp.getSearchRequestQuery();
                    }
                }, 500);
            }
        };

        mRvNewsFeed.addOnScrollListener(scrollListener);
    }

    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        Timber.d("onDialogDataSet");
/*
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
*/
        StringBuilder sb = new StringBuilder();
        sb.append(dayOfMonth);
        sb.append("-");
        sb.append(monthOfYear);
        sb.append("-");
        sb.append(year);

        tvDate.setText(sb.toString());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabSearch)
    public void searchNews() {
        /* Get values from the filter options */
        final String date = tvDate.getText().toString();
        final String query = mTvSearch.getText().toString();

        final String arts;
        final String fashion;
        final String sports;
        boolean hasArts = mCbArts.isChecked();
        boolean hasSports = mCbSports.isChecked();
        boolean hasFashion = mCbFashion.isChecked();

        if(mNewsListPresenterImp != null) {
            Timber.d("mNewsListPresenterImp != null");
            mNewsListPresenterImp.getSearchRequest();
        }
        else {
            Timber.e("mNewsListPresenterImp == null");
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tvDate)
    public void getDate() {
        DatePickerBuilder datePickerDialogFragment = new DatePickerBuilder()
                .setFragmentManager(getChildFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setTargetFragment(NewsListView.this);
        datePickerDialogFragment.show();
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
    public void displayQueryResults(NYTimesSearch nyTimesSearch) {

        if(nyTimesSearch.getResponse().getMeta().getHits() > 0) {
            if(nyTimesSearch.getResponse().getDocs().get(0).getHeadline() != null) {
                Timber.d("displayQueryResults: %s %s",
                        nyTimesSearch.getStatus(),
                        nyTimesSearch.getResponse().getDocs().get(0).getHeadline().getMain());
            }
        }
        else {
            Timber.d("displayQueryResults no hits returned");
            Toast.makeText(getActivity(), "Search returned no results", Toast.LENGTH_SHORT).show();
        }

        mNewsFeedAdapter.updateNewsFeed(nyTimesSearch);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchItem.expandActionView();
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                Toast.makeText(getActivity(), "Query: " + query, Toast.LENGTH_LONG).show();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNewsListPresenterImp.detachView();
        mUnbinder.unbind();
    }
}
