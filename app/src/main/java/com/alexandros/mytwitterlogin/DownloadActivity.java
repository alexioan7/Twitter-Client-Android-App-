package com.alexandros.mytwitterlogin;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alexandros.mytwitterlogin.Database.AppDatabase;
import com.alexandros.mytwitterlogin.Database.Entities.Follower;
import com.alexandros.mytwitterlogin.Database.Entities.TwitterUser;
import com.alexandros.mytwitterlogin.RESTclientServices.response.FollowersResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.FriendsResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.LikesResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.TwitterClientService;
import com.alexandros.mytwitterlogin.RESTclientServices.response.ShowUserResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.User;


import java.util.List;
import java.util.Random;


public class DownloadActivity extends AppCompatActivity {
    List<User> followerList;
    List<User> friendList;
    List<HomeTimelineResponse> homeTimelineList;
    List<LikesResponse> listOfLikes;
    String loggedInTwitterUserScreenName;
    Long loggedInTwitterUserId;


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




        //creating the database and clearing all users
        try{

            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                    db.twitterUseDao().deleteAll();

                    db.followerDao().deleteAll();

                }
            }).start();

        }catch(Exception e){
            e.printStackTrace();
        }





        //Methods for displaying in Logcat

        try {
            getLoggedInUser();
        }catch (Exception e){
            e.printStackTrace();
        }
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


    public void insertIntoDatabase(int i, User aUser, User aFollower, User aFriend){

        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                    TwitterUser user = new TwitterUser();
                    Follower follower = new Follower();
                    Follower friend = new Follower();

                    user.setDescription(aUser.getDescription());
                    user.setName(aUser.getName());
                    user.setScreenName(aUser.getScreenName());
                    user.setTwitterUserId(aUser.getId());
                    db.twitterUseDao().insert(user);

                    if(aFollower != null){
                        follower.setTwitterUserId(loggedInTwitterUserId);
                        follower.setFollowerId(aFollower.getId());
                        db.followerDao().insert(follower);
                    }

                    if(aFriend != null){
                        friend.setFollowerId(loggedInTwitterUserId);
                        friend.setTwitterUserId(aFriend.getId());
                        db.followerDao().insert(friend);
                    }





                }
            }).start();
        }catch(Exception e){
            e.printStackTrace();
        }

    }






    // Function for getting the followers of a user
    private void getFollowers(){

        Call<FollowersResponse> call = twitterClientService.getFollowers("false", "true");
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

                //Display on Logcat the followers list
                for (int i = 0; i < followerList.size(); i++) {
                    Log.d(TAG, "onResponse: followerUsername  " + i + "is :" + followerList.get(i).getName() + " has friends :" +
                            + followerList.get(i).getFriendsCount()+ " has followers :" + followerList.get(i).getFollowersCount());
                }


                        for(int i = 0; i < followerList.size(); i++){
                            User aUser = followerList.get(i);
                            User aFollower =  followerList.get(i);
                            User afriend = null;

                            insertIntoDatabase(i, aUser, aFollower, afriend);
                        }



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



                            for(int i = 0; i < friendList.size(); i++){
                                User aUser = friendList.get(i);
                                User afriend = friendList.get(i);
                                User afollower = null;
                                insertIntoDatabase(i, aUser, afollower,  afriend);
                            }








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

    public void getLoggedInUser(){
        Call<ShowUserResponse> call = twitterClientService.getLoggedInUser(loggedInTwitterUserScreenName, "false", "true");
        call.enqueue(new Callback<ShowUserResponse>() {
            @Override
            public void onResponse(Call<ShowUserResponse> call, Response<ShowUserResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code:", String.valueOf(response.code()));
                    return;
                }

                ShowUserResponse jsonShowUserResponse = response.body();
                assert jsonShowUserResponse != null;


                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = AppDatabase.getInstance(getApplicationContext());


                            ShowUserResponse loggedInUser = jsonShowUserResponse;


                            loggedInTwitterUserId = loggedInUser.getId();

                            TwitterUser user = new TwitterUser();

                            user.setDescription(loggedInUser.getDescription());
                            user.setName(loggedInUser.getName());
                            user.setScreenName(loggedInUser.getScreenName());
                            user.setTwitterUserId(loggedInUser.getId());

                            db.twitterUseDao().insert(user);

                            Log.i("comes from loggedin db", db.twitterUseDao().getAllTwitterUsers().toString());
                            Log.i("counted rows from db", String.valueOf(db.twitterUseDao().getCount()));

                        }
                    }).start();

                }catch (Exception e){
                    e.printStackTrace();
                }




            }

            @Override
            public void onFailure(Call<ShowUserResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


}



