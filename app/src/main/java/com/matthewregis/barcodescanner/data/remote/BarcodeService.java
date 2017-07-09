package com.matthewregis.barcodescanner.data.remote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matthewregis.barcodescanner.data.model.BarcodeModel;
import com.matthewregis.barcodescanner.util.MyGsonTypeAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.GET;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by reg on 17/01/2017.
 */

public interface BarcodeService {

    // Gets information based off code

    @GET("/prod/trial/lookup")
    Observable<BarcodeModel> getProductInfo(@Query("upc") String barcode);

    /********
     * Helper class that sets up a new services
     *******/
    class Creator {

        public static BarcodeService newBarcodeService() {

            String ENDPOINT = "https://api.upcitemdb.com";

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new ReceivedInterceptor())
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(BarcodeService.class);
        }
    }
}
