package com.rzagorski.callbackconverterfactory.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.rzagorski.callbackconverterfactory.gson.interfaces.RequestParser;
import com.rzagorski.callbackconverterfactory.gson.interfaces.ResponseParser;
import com.rzagorski.callbackconverterfactory.gson.retrofit.SimpleAPI;
import com.rzagorski.callbackconverterfactory.gson.retrofit.SimpleClass;
import com.rzagorski.callbackconverterfactory.gson.utils.Parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rzagorski.callbackconverterfactory.gson.retrofit.MockWebServerUtils.createRetrofitInstance;
import static com.rzagorski.callbackconverterfactory.gson.retrofit.MockWebServerUtils.dispatchSuccessfulResponse;
import static com.rzagorski.callbackconverterfactory.gson.retrofit.TestUtil.countDownLatch;
import static org.mockito.Matchers.any;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class Tests {

    MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.url("/");
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer = null;
    }

    @Test
    public void testSimpleCase1() throws Exception {
        dispatchSuccessfulResponse(mockWebServer);
        SimpleAPI api = createRetrofitInstance(mockWebServer.url("/").toString(), SimpleAPI.class,
                new CallbackGsonConverterFactory(new Gson(), new RequestParser<IOException>() {
                    @Override
                    public <T> RequestBody parseRequest(Gson gson, TypeAdapter<T> adapter, T value) throws IOException {
                        return Parser.parseDefault(gson, adapter, value, Parser.UTF_8, Parser.MEDIA_TYPE_JSON);
                    }
                }, new ResponseParser<IOException>() {
                    @Override
                    public <T> T parse(Gson gson, TypeAdapter<T> typeAdapter, ResponseBody responseBody) throws IOException {
                        return Parser.parseDefault(gson, typeAdapter, responseBody);
                    }
                }));
        Callback<SimpleClass> mockCallback = Mockito.mock(Callback.class);
        CountDownLatch latch = new CountDownLatch(1);
        countDownLatch(latch).when(mockCallback).onResponse(any(Call.class), any(Response.class));
        api.getSimple().enqueue(mockCallback);
        latch.await();
        Mockito.verify(mockCallback).onResponse(any(Call.class), any(Response.class));
    }

    @Test
    public void testSimpleCase2() throws Exception {
        dispatchSuccessfulResponse(mockWebServer);
        SimpleAPI api = createRetrofitInstance(mockWebServer.url("/").toString(), SimpleAPI.class,
                new CallbackGsonConverterFactory(new Gson(), new RequestParser<IOException>() {
                    @Override
                    public <T> RequestBody parseRequest(Gson gson, TypeAdapter<T> adapter, T value) throws IOException {
                        return Parser.parseDefault(gson, adapter, value, Parser.UTF_8, Parser.MEDIA_TYPE_JSON);
                    }
                }, new ResponseParser<IOException>() {
                    @Override
                    public <T> T parse(Gson gson, TypeAdapter<T> typeAdapter, ResponseBody responseBody) throws IOException {
                        return Parser.parseDefault(gson, typeAdapter, responseBody);
                    }
                }));
        Callback<SimpleClass> mockCallback = Mockito.mock(Callback.class);
        CountDownLatch latch = new CountDownLatch(1);
        countDownLatch(latch).when(mockCallback).onResponse(any(Call.class), any(Response.class));
        api.getSimple().enqueue(mockCallback);
        latch.await();
        Mockito.verify(mockCallback).onResponse(any(Call.class), any(Response.class));
    }
}