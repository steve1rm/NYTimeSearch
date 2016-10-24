package me.androidbox.nytimessearch.newsdetail;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.nytimessearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailView extends Fragment {

    @BindView(R.id.wvNewsDetail) WebView mWvNewsDetail;
    private Unbinder mUnbinder;

    public NewsDetailView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.news_detail_view, container, false);

        mUnbinder = ButterKnife.bind(NewsDetailView.this, view);

        loadNewsDetail();

        return view;
    }

    private void loadNewsDetail() {
        mWvNewsDetail.loadUrl("http://cooking.nytimes.com/recipes/1018357-mackerel-with-olives-almonds-and-mint");
        mWvNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
