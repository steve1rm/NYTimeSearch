package me.androidbox.nytimessearch.newslist;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.nytimessearch.R;
import me.androidbox.nytimessearch.di.DaggerInjector;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListView extends Fragment implements
        DatePickerDialogFragment.DatePickerDialogHandler,
        NewsListViewContract {

    @Inject NewsListPresenterImp mNewsListPresenterImp;

    @BindView(R.id.tvDate) TextView tvDate;
    private Unbinder mUnbinder;

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

        ButterKnife.bind(NewsListView.this, view);

        return view;
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

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append("-");
        sb.append(month + 1);
        sb.append("-");
        sb.append(year);
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
