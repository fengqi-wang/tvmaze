package com.fengqi.tvmaze.model.net;

import com.fengqi.tvmaze.model.Episode;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by fengqi on 2017-08-16.
 */

public interface Api {
    String BASE_URL = "http://api.tvmaze.com";

    @GET("/shows/82/episodes")
    Observable<List<Episode>> getEpisodes();
}
