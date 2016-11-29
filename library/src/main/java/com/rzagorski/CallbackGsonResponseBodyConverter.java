package com.rzagorski;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.rzagorski.interfaces.ResponseParser;
import com.rzagorski.utils.Parser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CallbackGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;
    private final TypeAdapter<T> mAdapter;
    private ResponseParser mResponseParser;

    CallbackGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, ResponseParser responseParser) {
        this.mGson = gson;
        this.mAdapter = adapter;
        this.mResponseParser = responseParser;
    }

    @Override
    public T convert(final ResponseBody responseBody) throws IOException {
        if (mResponseParser != null) {
            return mResponseParser.parse(mGson, mAdapter, responseBody);
        }
        return Parser.parseDefault(mGson, mAdapter, responseBody);
    }
}