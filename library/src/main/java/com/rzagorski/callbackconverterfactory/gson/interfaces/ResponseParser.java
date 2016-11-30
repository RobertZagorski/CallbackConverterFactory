package com.rzagorski.callbackconverterfactory.gson.interfaces;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by Robert Zagórski on 2016-11-28.
 */

public interface ResponseParser<E extends IOException> {

    <T> T parse(Gson gson, TypeAdapter<T> typeAdapter, ResponseBody responseBody) throws E;
}
