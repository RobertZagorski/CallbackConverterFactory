package com.rzagorski;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.rzagorski.interfaces.RequestParser;
import com.rzagorski.utils.Parser;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;

public class CallbackGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private final Gson mGson;
    private final TypeAdapter<T> mAdapter;
    private RequestParser mRequestParser;

    CallbackGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter, RequestParser requestParser) {
        this.mGson = gson;
        this.mAdapter = adapter;
        this.mRequestParser = requestParser;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        if (mRequestParser != null) {
            return mRequestParser.parseRequest(mGson, mAdapter, value);
        }
        return Parser.parseDefault(mGson, mAdapter, value, Parser.UTF_8, Parser.MEDIA_TYPE_JSON);
    }
}