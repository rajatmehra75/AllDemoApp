package com.rajat.alldemoapp.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by rajat on 8/8/18.
 */

public class APIClient {

    private static Retrofit retrofit;
    private static APIInterface apiInterface;
    private static String baseUrl = "https://jsonplaceholder.typicode.com/";// http://date.jsontest.com/

    private static Retrofit getClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        return retrofit;
    }

    public static APIInterface getService() {
        if (apiInterface != null) {
            return apiInterface;
        }
        if (retrofit == null) {
            retrofit = getClient();
        }
        apiInterface = retrofit.create(APIInterface.class);
        return apiInterface;
    }

    public interface APIInterface {
        @GET
        Call<ResponseBody> get(@Url String url);

        @POST
        Call<ResponseBody> post(@Url String url, @Body Object obj);
    }
}
