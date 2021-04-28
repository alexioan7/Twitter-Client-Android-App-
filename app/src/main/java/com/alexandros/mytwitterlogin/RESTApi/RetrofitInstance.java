package com.alexandros.mytwitterlogin.RESTApi;

import com.alexandros.mytwitterlogin.utils.Constants;

import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static RetrofitInstance retrofitInstance;
    Oauth1SigningInterceptor.Clock clock = new Oauth1SigningInterceptor.Clock();
    Random random = new Random();

    private RetrofitInstance(String accessToken, String accessTokenSecret) {

        Oauth1SigningInterceptor myInterceptor = new Oauth1SigningInterceptor(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET,accessToken,accessTokenSecret,random,clock);


        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(myInterceptor)
                .build();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.SERVER_BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static synchronized RetrofitInstance getRetrofitInstance(String accessToken, String accessTokenSecret) {
        if (retrofitInstance == null) {
            retrofitInstance = new RetrofitInstance(accessToken, accessTokenSecret);
        }
        return retrofitInstance;
    }

    public final TwitterClientService getTwitterClientService() {
        return retrofit.create(TwitterClientService.class);
    }






}
