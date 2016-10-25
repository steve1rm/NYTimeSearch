package me.androidbox.nytimessearch.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.nytimessearch.R;

import static me.androidbox.nytimessearch.R.string.arts;

/**
 * Created by steve on 10/25/16.
 */

public class SettingsFilter extends DialogFragment {
    public interface SettingFilterListener {
        void onSettingsFilter(boolean arts, boolean sport, boolean fashion, String date, String query, String sort);
    }
    private SettingFilterListener mSettingsFilterListener;

    @BindView(R.id.cbSports) CheckBox cbSports;
    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbFashionStyle) CheckBox cbFashion;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.etSearch) EditText etSearch;
    @BindView(R.id.spNewsDesk) Spinner spNewsDesk;

    private Unbinder mUnbinder;

    public SettingsFilter() {
    }

    @SuppressWarnings("all")
    public SettingsFilter(SettingFilterListener settingFilterListener) {
        mSettingsFilterListener = settingFilterListener;
    }

    public static SettingsFilter getNewInstance(SettingFilterListener settingFilterListener) {
        return new SettingsFilter(settingFilterListener);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.filter_search, container);

        mUnbinder = ButterKnife.bind(SettingsFilter.this, view);

        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSubmit)
    public void submitQuery() {
        boolean arts = cbArts.isChecked();
        boolean sports = cbSports.isChecked();
        boolean fashion = cbFashion.isChecked();
        String date = tvDate.getText().toString();
        String query = etSearch.getText().toString();
        String sort = spNewsDesk.getSelectedItem().toString();

        mSettingsFilterListener.onSettingsFilter(arts, sports, fashion, date, query, sort);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tvDate)
    public void getDate() {
        DatePickerBuilder datePickerDialogFragment = new DatePickerBuilder()
                .setFragmentManager(getChildFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setTargetFragment(SettingsFilter.this);
        datePickerDialogFragment.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mSettingsFilterListener = null;
    }
}
