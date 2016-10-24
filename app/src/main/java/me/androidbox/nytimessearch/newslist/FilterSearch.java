package me.androidbox.nytimessearch.newslist;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.nytimessearch.R;

/**
 * Created by steve on 10/24/16.
 */

public class FilterSearch extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.news_filter, container);

        getDialog().setTitle("Filter");

        return view;
    }
}
