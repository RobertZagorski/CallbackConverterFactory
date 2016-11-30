package com.rzagorski.callbackconverterfactory.gson.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by Robert Zagórski on 2016-11-28.
 */

public class Parser {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=UTF-8");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static <T> RequestBody parseDefault(Gson gson, TypeAdapter<T> adapter, T value, Charset charset, MediaType mediaType) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), charset);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(mediaType, buffer.readByteString());
    }

    public static <T> T parseDefault(Gson gson, TypeAdapter<T> adapter, ResponseBody responseBody) throws IOException {
        BufferedReader streams = new BufferedReader(responseBody.charStream());
        streams.mark((int) (responseBody.contentLength() + 1));
        JsonReader jsonReader = gson.newJsonReader(streams);
        return adapter.read(jsonReader);
    }
}
