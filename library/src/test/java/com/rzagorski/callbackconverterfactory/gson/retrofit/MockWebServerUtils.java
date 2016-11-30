/*
 * Copyright (C) 2016 Robert Zagórski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rzagorski.callbackconverterfactory.gson.retrofit;

import com.google.gson.Gson;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Robert Zagórski on 2016-10-03.
 */

public class MockWebServerUtils {

    public static final Long ONE_SEC = 1000L;

    public static <T> T createRetrofitInstance(String url, Class<T> outputClass, Converter.Factory factory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(factory)
                .build();
        return retrofit.create(outputClass);
    }

    public static MockResponse getSuccessfulResponse() {
        String body = new Gson().toJson(new SimpleClass());
        return new MockResponse()
                .setResponseCode(200)
                .setBody(body);
    }

    public static void dispatchSuccessfulResponse(MockWebServer mockWebServer) {
        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                return getSuccessfulResponse();
            }
        });
    }
}
