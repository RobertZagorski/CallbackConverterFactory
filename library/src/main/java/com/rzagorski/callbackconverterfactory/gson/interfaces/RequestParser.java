package com.rzagorski.callbackconverterfactory.gson.interfaces;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.RequestBody;

/**
 * Created by Robert Zagórski on 2016-11-28.
 */

public interface RequestParser<E extends IOException> {

    <T> RequestBody parseRequest(Gson gson, TypeAdapter<T> adapter, T value) throws E;
}
