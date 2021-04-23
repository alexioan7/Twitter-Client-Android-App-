package com.alexandros.mytwitterlogin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandros.mytwitterlogin.RESTApi.TwitterClientService;
import com.alexandros.mytwitterlogin.RESTApi.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class HomeTimelineFragment extends Fragment {


    List<HomeTimelineResponse> homeTimelineList;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private TwitterClientService twitterClientService;

    private String consumerKey;
    private String consumerSecret;

    Oauth1SigningInterceptor.Clock clock = new Oauth1SigningInterceptor.Clock();
    Random random = new Random();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home_timeline, container, false);



        mRecyclerView = Objects.requireNonNull(view).findViewById(R.id.hometimeline_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        consumerKey = "X49WPfKJ3lJixxzCVB8KuJK7z";
        consumerSecret = "bqbzunS3mKqNMQCnnlv5e2T5RkLmw9Ckzs2XFWeBODSWXU49yC";
        String accessToken = requireActivity().getIntent().getExtras().getString("accessToken");
        String accessTokenSecret = requireActivity().getIntent().getExtras().getString("accessTokenSecret");


        Oauth1SigningInterceptor myInterceptor = new Oauth1SigningInterceptor(consumerKey, consumerSecret, accessToken, accessTokenSecret, random, clock);

        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(myInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.twitter.com/1.1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            twitterClientService = retrofit.create(TwitterClientService.class);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try{
            getHomeTimeline();
        }catch (Exception e){
            e.printStackTrace();
        }


        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }





    private void getHomeTimeline() {
        Call<List<HomeTimelineResponse>> call = twitterClientService.getHomeTimeline("10", "false", "true");
        call.enqueue(new Callback<List<HomeTimelineResponse>>() {
            @Override
            public void onResponse(Call<List<HomeTimelineResponse>> call, Response<List<HomeTimelineResponse>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code:", String.valueOf(response.code()));
                    return;
                }

                assert response.body() != null;
                homeTimelineList = response.body();

                // display on recyclerview
                ArrayList<CardViewItem> cardViewList = new ArrayList<>();

                for (int i = 0; i < homeTimelineList.size(); i++) {

                    String homeTimeline = homeTimelineList.get(i).getText();

                    Log.d("text from log timeline", homeTimeline);

                    cardViewList.add(new CardViewItem(homeTimeline));

                }

                mAdapter = new Adapter(cardViewList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<HomeTimelineResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }
}