package me.androidbox.nytimessearch.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.nytimessearch.R;
import me.androidbox.nytimessearch.di.DaggerInjector;
import me.androidbox.nytimessearch.model.Article;
import me.androidbox.nytimessearch.model.NYTimesSearch;
import timber.log.Timber;

import static android.R.id.list;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListView extends Fragment implements
        DatePickerDialogFragment.DatePickerDialogHandler,
        NewsListViewContract,
        SettingsFilter.SettingFilterListener {
    private EndlessRecyclerViewScrollListener scollListener;

    @Inject NewsListPresenterImp mNewsListPresenterImp;
    private NewsFeedAdapter mNewsFeedAdapter;

  //  @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.rvNewsFeed) RecyclerView mRvNewsFeed;
    @BindView(R.id.tbNYTimes) Toolbar mToolbar;

    private Unbinder mUnbinder;
    private String mDate;
    private boolean mArts;
    private boolean mSports;
    private boolean mFashion;
    private String mQuery;
    private String mSorted;

    List<NYTimesSearch.Response.Docs> listTimesSearches = new ArrayList<>();

    public NewsListView() {
        // Required empty public constructor
    }

    public static NewsListView getNewInstance() {
        return new NewsListView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        appCompatActivity.setSupportActionBar(mToolbar);
    }

    private void setupAdapter() {
        mNewsFeedAdapter = new NewsFeedAdapter(listTimesSearches, getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRvNewsFeed.setLayoutManager(staggeredGridLayoutManager);
        mRvNewsFeed.setAdapter(mNewsFeedAdapter);

        scollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Timber.d("onLoadMore page %d %d", page, totalItemsCount);
                loadNextDataFromApi(page);
            }
        };

        mRvNewsFeed.addOnScrollListener(scollListener);
    }

    public void loadNextDataFromApi(int offset) {
       mNewsListPresenterImp.getSearchRequest();
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

   //     tvDate.setText(sb.toString());
    }

  /*  @SuppressWarnings("unused")
    @OnClick(tvDate)
    public void getDate() {
        DatePickerBuilder datePickerDialogFragment = new DatePickerBuilder()
                .setFragmentManager(getChildFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setTargetFragment(NewsListView.this);
        datePickerDialogFragment.show();
    }
*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaggerInjector.getAppComponent().inject(NewsListView.this);

        if(mNewsListPresenterImp != null) {
            Timber.d("mNewsListPresenterImp != null");
            mNewsListPresenterImp.attachView(NewsListView.this);
            mNewsListPresenterImp.getSearchRequest();
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
        Timber.d("displayQueryResults: %s %s",
                nyTimesSearch.getStatus(),
                nyTimesSearch.getResponse().getDocs().get(0).getHeadline().getMain());

        listTimesSearches.addAll(nyTimesSearch.getResponse().getDocs());
        mNewsFeedAdapter.updatedNewsFeed(listTimesSearches);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNewsListPresenterImp.detachView();
        mUnbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_settings:
                FragmentManager fragmentManager = getFragmentManager();
                SettingsFilter settingsFilter = SettingsFilter.getNewInstance(NewsListView.this);
                settingsFilter.show(fragmentManager, "settingsfilter");

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSettingsFilter(boolean arts, boolean sport, boolean fashion, String date, String query, String sort) {
        mArts = arts;
        mSports = sport;
        mFashion = fashion;
        mDate = date;
        mQuery = query;
        mSorted = sort;
    }

    @OnClick(R.id.fabSearch)
    public void searchNewsResults() {
        mNewsListPresenterImp.getSearchRequestQuery(mArts, mSports, mFashion, mDate, mQuery, mSorted);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
