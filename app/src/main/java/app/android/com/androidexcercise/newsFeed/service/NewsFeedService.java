package app.android.com.androidexcercise.newsFeed.service;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface NewsFeedService {
    @GET("public-api/NewsFeeddata")
    Observable<app.android.com.androidexcercise.newsFeed.model.NewsFeed> getData();
}
