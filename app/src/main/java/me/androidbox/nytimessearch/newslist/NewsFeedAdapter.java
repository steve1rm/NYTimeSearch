package me.androidbox.nytimessearch.newslist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.nytimessearch.R;
import me.androidbox.nytimessearch.model.NYTimesSearch;
import me.androidbox.nytimessearch.utils.Constants;
import me.androidbox.nytimessearch.utils.ImageUtils;
import timber.log.Timber;

/**
 * Created by steve on 10/22/16.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    private NYTimesSearch mNyTimesSearch;
    private WeakReference<Context> mContext;
    private List<NYTimesSearch.Response.Docs> mytimesList = Collections.emptyList();

    public NewsFeedAdapter(NYTimesSearch nyTimesSearch, Context context) {
        mNyTimesSearch = nyTimesSearch;
        mContext = new WeakReference<>(context);
    }

    @Override
    public int getItemCount() {
        if(mNyTimesSearch != null) {
            return (mNyTimesSearch.getResponse() != null)
                    ? mNyTimesSearch.getResponse().getDocs().size() : 0;
        }
        else {
            return 0;
        }
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.news_info_row, parent, false);

        return new NewsFeedViewHolder(view);
    }



    @Override
    public void onBindViewHolder(NewsFeedViewHolder holder, int position) {
        Timber.d("onBindViewHolder");

        if(mNyTimesSearch.getResponse().getDocs().get(position).getHeadline() != null) {
            String headLine = mNyTimesSearch.getResponse().getDocs().get(position).getHeadline().getMain();
            if(!TextUtils.isEmpty(headLine)) {
                Timber.d("onBindViewHolder headline: %s", headLine);
                holder.mTvHeadline.setText(mNyTimesSearch.getResponse().getDocs().get(position).getHeadline().getMain());
            }
            else {
                headLine = mNyTimesSearch.getResponse().getDocs().get(position).getHeadline().getPrint_headline();
                if(!TextUtils.isEmpty(headLine)) {
                    Timber.d("onBindViewHolder print headline: %s", headLine);
                    holder.mTvHeadline.setText(mNyTimesSearch.getResponse().getDocs().get(position).getHeadline().getMain());
                }
            }
        }
        else {
     //       holder.mTvHeadline.setText(mytimesList.get(position).getResponse().getDocs().get(0).getHeadline().getMain());
     //       holder.mTvHeadline.setText(mNyTimesSearch.getResponse().getDocs().get(position).get_abstract());
        }

        if(ImageUtils.isValidImagePath(mNyTimesSearch, position)) {
            String imageUrl = mNyTimesSearch.getResponse().getDocs().get(position).getMultimedia().get(0).getUrl();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Constants.WEB_URL);
            stringBuilder.append(imageUrl);

            Glide.with(mContext.get())
                    .load(stringBuilder.toString())
                    .placeholder(R.drawable.newyorktimes_placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(holder.mIvNewsFeed);
        }
        else {
            Glide.with(mContext.get())
                    .load(R.drawable.newyorktimes_placeholder)
                    .placeholder(R.drawable.newyorktimes_placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(holder.mIvNewsFeed);
        }
    }

    public void updateNewsFeedOnDemand(int page) {

    }

    public void updateNewsFeed(NYTimesSearch nyTimesSearch) {
      //  mytimesList.add(mNyTimesSearch.getResponse().getDocs().get(0));
        mNyTimesSearch = nyTimesSearch;
        notifyDataSetChanged();
    }

    static class NewsFeedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvHeadline) TextView mTvHeadline;
        @BindView(R.id.ivNewsFeed) ImageView mIvNewsFeed;

        private Unbinder mUnbinder;

        public NewsFeedViewHolder(View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(NewsFeedViewHolder.this, itemView);
        }
    }
}
