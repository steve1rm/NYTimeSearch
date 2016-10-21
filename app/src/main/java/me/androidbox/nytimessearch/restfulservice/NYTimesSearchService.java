package me.androidbox.nytimessearch.restfulservice;

import me.androidbox.nytimessearch.model.NYTimesSearch;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by steve on 10/20/16.
 */

public interface NYTimesSearchService {
    @GET("search/")
    Observable<NYTimesSearch> getNewsSearch(@Query("api_key") String apiKey);
}
