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
import com.alexandros.mytwitterlogin.RESTApi.response.FriendsResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class FriendsFragment extends Fragment {
    List<User> friendList;

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


        View view = inflater.inflate(R.layout.fragment_friends, container, false);


        mRecyclerView = Objects.requireNonNull(view).findViewById(R.id.friends_rv);
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
            getFriends();
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






    private void getFriends(){

        Call<FriendsResponse> call = twitterClientService.getFriends("200", "false", "true");
        call.enqueue(new Callback<FriendsResponse>() {
            @Override
            public void onResponse(Call<FriendsResponse> call, Response<FriendsResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }

                FriendsResponse jsonFriendsResponse = response.body();
                assert jsonFriendsResponse != null;
                friendList = jsonFriendsResponse.getUsers();

                // display on recyclerview

                ArrayList<CardViewItem> cardViewList = new ArrayList<>();


                //Display on Logcat the friends list
                for (int i =0; i < friendList.size(); i++) {

                    String friendName = friendList.get(i).getName();

                    cardViewList.add(new CardViewItem(friendName));
                }

                mAdapter = new Adapter(cardViewList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<FriendsResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });

    }

}