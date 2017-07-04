package com.matthewregis.barcodescanner.data.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by reg on 08/02/2017.
 */


public final class ReceivedInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        long t1 = System.nanoTime();
        Timber.d(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        long t2 = System.nanoTime();
        String bodyString = response.body().string();
        Timber.d(String.format("Received response for %s in %.1fms %s%n%s", response.request().url(), (t2 - t1) / 1e6d, response.code(), bodyString));
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();

    }
}
