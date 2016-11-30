package com.rzagorski.callbackconverterfactory.gson.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Robert Zagórski on 2016-11-29.
 */

public interface SimpleAPI {

    @GET("/")
    Call<SimpleClass> getSimple();
}
