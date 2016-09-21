package com.example.finance.service;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Retrofit Interface.
 */
public interface ScreenInfoServiceApi {

    @GET("https://api.myjson.com/bins/18vxt")
    Observable<ScreenInfoResponseWeb> getInfo();

}
