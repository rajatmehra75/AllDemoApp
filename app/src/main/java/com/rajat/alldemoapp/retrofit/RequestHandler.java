package com.rajat.alldemoapp.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ist on 8/8/18.
 */

public class RequestHandler {

    private static final String TAG = RequestHandler.class.getSimpleName();
    private static RequestHandler requestHandler;

    public static RequestHandler getInstance(){
        if(requestHandler == null){
            requestHandler = new RequestHandler();
        }
        return requestHandler;
    }

    private RequestHandler(){
    }

    public String getHttpResponseAsync(String url){
        Call<ResponseBody> call = APIClient.getService().get(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"code : "+response.code()+"");
                ResponseBody body = response.body();
                try {
                    Log.d(TAG,"response : " + body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
            }
        });
        return "";
    }

    public String getHttpResponseSync(String url){
        try {
            Call<ResponseBody> call = APIClient.getService().get(url);
            Response<ResponseBody> response = call.execute();
            if (!call.isCanceled()) {
                Log.d(TAG,"code : "+response.code()+"");
                String responseStr = response.body().string();
                Log.d(TAG,"response" + responseStr);
                return responseStr;
            }
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public String postHttpResponseSync(String url,Object obj){
        try {
            Call<ResponseBody> call = APIClient.getService().post(url,obj);
            Response<ResponseBody> response = call.execute();
            if (!call.isCanceled()) {
                Log.d(TAG,"code : "+response.code()+"");
                String responseStr = response.body().string();
                Log.d(TAG,"response : " + responseStr);
                return responseStr;
            }
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "";
    }

}
