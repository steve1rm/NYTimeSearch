package me.androidbox.nytimessearch.newslist;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.nytimessearch.R;
import me.androidbox.nytimessearch.model.NYTimesSearch;
import me.androidbox.nytimessearch.newsdetail.NewsDetailActivity;
import me.androidbox.nytimessearch.utils.Constants;
import me.androidbox.nytimessearch.utils.ImageUtils;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by steve on 10/22/16.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    private NYTimesSearch mNyTimesSearch;
    private WeakReference<Context> mContext;

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
        holder.mTvHeadline.setText(mNyTimesSearch.getResponse().getDocs().get(position).getHeadline().getMain());

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

    public void updateNewsFeed(NYTimesSearch nyTimesSearch) {
        mNyTimesSearch = nyTimesSearch;
        notifyDataSetChanged();
    }

    class NewsFeedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvHeadline) TextView mTvHeadline;
        @BindView(R.id.ivNewsFeed) ImageView mIvNewsFeed;

        private Unbinder mUnbinder;

        public NewsFeedViewHolder(View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(NewsFeedViewHolder.this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext.get(), NewsDetailActivity.class);
                    final String weburl = mNyTimesSearch.getResponse().getDocs().get(getAdapterPosition()).getWeb_url();
                    intent.putExtra("weburl_key", weburl);
                    mContext.get().startActivity(intent);
                }
            });
        }
    }
}
