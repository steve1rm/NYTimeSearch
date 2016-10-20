package me.androidbox.nytimessearch.newslist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.nytimessearch.debug.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListView extends Fragment {


    public NewsListView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.news_list_view, container, false);
    }

}
