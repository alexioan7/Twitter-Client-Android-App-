package com.alexandros.mytwitterlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alexandros.mytwitterlogin.RESTApi.response.FollowersResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.FriendsResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.LikesResponse;
import com.alexandros.mytwitterlogin.RESTApi.TwitterClientService;
import com.alexandros.mytwitterlogin.RESTApi.response.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DownloadActivity extends AppCompatActivity {
    List<User> followerList;
    List<User> friendList;
    List<HomeTimelineResponse> homeTimelineList;
    List<LikesResponse> listOfLikes;
    String loggedInTwitterUserScreenName;


    //RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    private static final String TAG = "DownloadActivity";


    Oauth1SigningInterceptor.Clock clock = new Oauth1SigningInterceptor.Clock();
    Random random = new Random();

    private TwitterClientService twitterClientService;

    private String accessToken;
    private String accessTokenSecret;
    private String consumerKey;
    private String consumerSecret;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        //Example for the recyclerview
        //ArrayList<CardViewItem> exampleList = new ArrayList<>();

        /*
        exampleList.add(new CardViewItem("line 1"));
        exampleList.add(new CardViewItem("line 2"));
        exampleList.add(new CardViewItem("line 3"));
        */

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);


        /*
        mAdapter = new Adapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

         */



        String consumer = getString(R.string.app_name);
        consumerKey = "X49WPfKJ3lJixxzCVB8KuJK7z";
        consumerSecret = "bqbzunS3mKqNMQCnnlv5e2T5RkLmw9Ckzs2XFWeBODSWXU49yC";
        Intent intent = getIntent();
        accessToken = intent.getExtras().getString("accessToken");
        accessTokenSecret = intent.getExtras().getString("accessTokenSecret");
        loggedInTwitterUserScreenName = intent.getExtras().getString("loggedInTwitterUserScreenName");

        Oauth1SigningInterceptor myInterceptor = new Oauth1SigningInterceptor(consumerKey,consumerSecret,accessToken,accessTokenSecret,random,clock);

        try{
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(myInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.twitter.com/1.1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            twitterClientService = retrofit.create(TwitterClientService.class);

        }catch (Exception e){
            e.printStackTrace();
        }




        //Methods for displaying in Logcat


        try{
            getFollowers();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            getFriends();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            getHomeTimeline();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            getLikes();
        }catch (Exception e){
            e.printStackTrace();
        }


    }




    // Function for getting the followers of a user
    private void getFollowers(){

        Call<FollowersResponse> call = twitterClientService.getFollowers("200","false", "true");
        call.enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(Call<FollowersResponse> call, retrofit2.Response<FollowersResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(response.code()));
                    return;
                }

                FollowersResponse jsonResponse = response.body();
                assert jsonResponse != null;
                followerList = jsonResponse.getUsers();


                // display on recyclerview

                ArrayList<CardViewItem> cardViewList = new ArrayList<>();





                //Display on Logcat the followers list
                for (int i = 0; i < followerList.size(); i++) {
                    Log.d(TAG, "onResponse: followerUsername  " + i + "is :" + followerList.get(i).getName() + " has friends :" +
                            + followerList.get(i).getFriendsCount()+ " has followers :" + followerList.get(i).getFollowersCount());

                    String followerName = followerList.get(i).getName();

                    cardViewList.add(new CardViewItem(followerName));
                }

                mAdapter = new Adapter(cardViewList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);


            }
            @Override
            public void onFailure(Call<FollowersResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });
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




                //Display on Logcat the friends list
                for (int i =0; i < friendList.size(); i++) {
                    Log.d(TAG, "onResponse: friendUsername  " + i + "is :" + friendList.get(i).getName() +
                            " has friends : " + friendList.get(i).getFriendsCount()+ " has followers :" + friendList.get(i).getFollowersCount());
                }

            }

            @Override
            public void onFailure(Call<FriendsResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });

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
                for (int i = 0; i < homeTimelineList.size(); i++) {
                    Log.d(TAG, "onResponse: homeTimeLineText is :" + homeTimelineList.get(i).getText());
                }
            }

            @Override
            public void onFailure(Call<List<HomeTimelineResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

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
                for(int i=0; i < listOfLikes.size(); i++){
                    Log.d(TAG, "onResponse: listOfLikes is" + listOfLikes.get(i).getText());
                }

            }

            @Override
            public void onFailure(Call<List<LikesResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

}



