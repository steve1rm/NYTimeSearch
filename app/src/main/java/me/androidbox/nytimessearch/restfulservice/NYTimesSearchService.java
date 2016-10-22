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

    /* news_desk = fq */
    /* begin_date */
    /* sort oldest newest */

    @GET("articlesearch.json")
    Observable<NYTimesSearch> getNewsdeskQuery(@Query("begin_date") String beginDate,
                                               @Query("sort") String sort,
                                               @Query("fq") String newsdesk,
                                               @Query("q") String query,
                                               @Query("api_key") String apiKey);
}
