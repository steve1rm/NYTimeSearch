package me.androidbox.nytimessearch.restfulservice;

import me.androidbox.nytimessearch.model.NYTimesSearch;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by steve on 10/20/16.
 */

public interface NYTimesSearchService {
    @GET("articlesearch.json")
    Observable<NYTimesSearch> getNewsFeed(@Query("api_key") String apiKey);

    @GET("articlesearch.json")
    Observable<NYTimesSearch> getNewsQuery(@Query("api_key") String apiKey, @Query("q") String query);
}
