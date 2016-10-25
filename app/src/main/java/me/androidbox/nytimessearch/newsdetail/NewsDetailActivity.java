package me.androidbox.nytimessearch.newsdetail;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.androidbox.nytimessearch.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        if(savedInstanceState == null) {

            final String weburl = getIntent().getStringExtra("weburl_key");
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_news_detail_container, NewsDetailView.getNewInstance(weburl), "newsdetailview");
            fragmentTransaction.commit();
        }
    }
}
