package com.rzagorski;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.rzagorski.interfaces.RequestParser;
import com.rzagorski.interfaces.ResponseParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Piotr on 2016-05-30.
 */
public final class CallbackGsonConverterFactory extends Converter.Factory {

    private final Gson gson;
    private RequestParser requestParser;
    private ResponseParser responseParser;

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static CallbackGsonConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static CallbackGsonConverterFactory create(Gson gson) {
        return new CallbackGsonConverterFactory(gson, null, null);
    }

    public static CallbackGsonConverterFactory create(Gson gson, RequestParser requestParser, ResponseParser responseParser) {
        return new CallbackGsonConverterFactory(gson, requestParser, responseParser);
    }

    private CallbackGsonConverterFactory(Gson gson) {
        this(gson, null, null);
    }

    public CallbackGsonConverterFactory(Gson gson, RequestParser requestParser, ResponseParser responseParser) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
        this.requestParser = requestParser;
        this.responseParser = responseParser;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CallbackGsonResponseBodyConverter<>(gson, adapter, responseParser);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CallbackGsonRequestBodyConverter<>(gson, adapter, requestParser);
    }
}
