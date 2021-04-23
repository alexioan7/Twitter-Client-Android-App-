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
import com.alexandros.mytwitterlogin.RESTApi.response.LikesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class LikesFragment extends Fragment {

    List<LikesResponse> listOfLikes;

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



        View view = inflater.inflate(R.layout.fragment_likes, container, false);


        mRecyclerView = Objects.requireNonNull(view).findViewById(R.id.likes_rv);
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
            getLikes();;
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



    private void getLikes(){
        Call<List<LikesResponse>> call = twitterClientService.getLikes("10", "false");
        call.enqueue(new Callback<List<LikesResponse>>() {
            @Override
            public void onResponse(Call<List<LikesResponse>> call, Response<List<LikesResponse>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code:", String.valueOf(response.code()));
                    return;
                }
                assert response.body() != null;
                listOfLikes = response.body();


                // display on recyclerview
                ArrayList<CardViewItem> cardViewList = new ArrayList<>();



                for(int i=0; i < listOfLikes.size(); i++){

                    String like = listOfLikes.get(i).getText();
                    cardViewList.add(new CardViewItem(like));
                }

                mAdapter = new Adapter(cardViewList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<LikesResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


}