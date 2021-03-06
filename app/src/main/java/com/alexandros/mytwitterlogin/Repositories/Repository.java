package com.alexandros.mytwitterlogin.Repositories;
import android.content.Context;
import android.util.Log;

import com.alexandros.mytwitterlogin.Adapters.CardViewItem;
import com.alexandros.mytwitterlogin.Database.Converters.FollowerConverter;
import com.alexandros.mytwitterlogin.Database.DAOs.FollowerDao;
import com.alexandros.mytwitterlogin.Database.DAOs.FriendDao;
import com.alexandros.mytwitterlogin.Database.DAOs.HomeTimelineDao;
import com.alexandros.mytwitterlogin.Database.DAOs.LikeDao;
import com.alexandros.mytwitterlogin.Database.Entities.Follower;
import com.alexandros.mytwitterlogin.Database.MyDatabase;

import com.alexandros.mytwitterlogin.RESTApi.RetrofitInstance;
import com.alexandros.mytwitterlogin.RESTApi.TwitterClientService;
import com.alexandros.mytwitterlogin.RESTApi.response.FollowersResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.FriendsResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.LikesResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.LookUpUserResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.User;
import com.google.api.Http;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class Repository {
    List<User> followerList;
    List<User> friendList;
    List<HomeTimelineResponse> homeTimelineList;
    List<LikesResponse> listOfLikes;


    private LikeDao likeDao;
    private HomeTimelineDao homeTimelineDao;
    private FollowerDao followerDao;
    private FriendDao friendDao;

    MyDatabase db;
    List<Follower> followerListForDB;
    LiveData<List<Follower>> followersFromDB;



    FollowerConverter followerConverter = new FollowerConverter();

    TwitterClientService twitterClientService;
    private static volatile Repository repositoryInstance;

    MutableLiveData<List<CardViewItem>> mutableLiveDataFollowers = new MutableLiveData<>();
    MutableLiveData<List<CardViewItem>> mutableLiveDataFriends = new MutableLiveData<>();
    MutableLiveData<List<CardViewItem>> mutableLiveDataHomeTimeline = new MutableLiveData<>();
    MutableLiveData<List<CardViewItem>> mutableLiveDataLikes = new MutableLiveData<>();
    MutableLiveData <String> user = new MutableLiveData<>();

    private Repository(Context context, String accessToken, String accessTokenSecret){
        RetrofitInstance retrofitInstance = RetrofitInstance.getRetrofitInstance(accessToken,accessTokenSecret);
        twitterClientService = retrofitInstance.getTwitterClientService();
        db = MyDatabase.getDataBase(context);
        followerDao = db.followerDao();

    }

    public static Repository getInstance(Context context, String accessToken, String accessTokenSecret){
        if(repositoryInstance == null){
            repositoryInstance = new Repository(context, accessToken, accessTokenSecret);
        }
        return repositoryInstance;
    }


    public LiveData<String> getUser() {
        return user;
    }

    public LiveData<List<CardViewItem>> getFollowersLive() {
        return mutableLiveDataFollowers;
    }

    public LiveData<List<CardViewItem>> getFriendsLive() {
        return mutableLiveDataFriends;
    }

    public LiveData<List<CardViewItem>> getHomeTimelineLive() {
        return mutableLiveDataHomeTimeline;
    }

    public LiveData<List<CardViewItem>> getLikesLive() {
        return mutableLiveDataLikes;
    }


    public LiveData<List<Follower>> getFollowersFromDB(){
        return db.followerDao().getAllFollowersFromDB();
    }

        // Function for getting the followers of a user
    public void getFollowers () {

        Call<FollowersResponse> call = twitterClientService.getFollowers("200", "false", "true");
        call.enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(@NonNull Call<FollowersResponse> call, @NonNull retrofit2.Response<FollowersResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code:", String.valueOf(response.code()));
                    return;
                }

                FollowersResponse jsonResponse = response.body();
                assert jsonResponse != null;
                followerList = jsonResponse.getUsers();


                followerListForDB = followerConverter.ConvertUserListToFollowerList(followerList);

                ArrayList<CardViewItem> cardViewList = new ArrayList<>();
                db.getQueryExecutor().execute(()-> db.followerDao().insertAll(followerListForDB));

                for (int i = 0; i < followerList.size(); i++) {

                    String followerName = followerList.get(i).getName();
                    cardViewList.add(new CardViewItem(followerName));
                }

                mutableLiveDataFollowers.postValue(cardViewList);
            }

            @Override
            public void onFailure(@NonNull Call<FollowersResponse> call,@NonNull Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });
    }



    public void getFriends(){

        Call<FriendsResponse> call = twitterClientService.getFriends("200", "false", "true");
        call.enqueue(new Callback<FriendsResponse>() {
            @Override
            public void onResponse(@NonNull Call<FriendsResponse> call, @NonNull Response<FriendsResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }

                FriendsResponse jsonFriendsResponse = response.body();
                assert jsonFriendsResponse != null;
                friendList = jsonFriendsResponse.getUsers();

                // display on recyclerview
                ArrayList<CardViewItem> cardViewList = new ArrayList<>();


                for (int i =0; i < friendList.size(); i++) {

                    String friendName = friendList.get(i).getName();

                    cardViewList.add(new CardViewItem(friendName));
                }

                mutableLiveDataFriends.postValue(cardViewList);
            }

            @Override
            public void onFailure(@NonNull Call<FriendsResponse> call,@NonNull Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });

    }

    public void getHomeTimeline() {
        Call<List<HomeTimelineResponse>> call = twitterClientService.getHomeTimeline("10", "false", "true");
        call.enqueue(new Callback<List<HomeTimelineResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<HomeTimelineResponse>> call, @NonNull Response<List<HomeTimelineResponse>> response) {
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
                    cardViewList.add(new CardViewItem(homeTimeline));

                }

                mutableLiveDataHomeTimeline.postValue(cardViewList);
            }

            @Override
            public void onFailure(@NonNull Call<List<HomeTimelineResponse>> call, @NonNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    public void getLikes(){
        Call<List<LikesResponse>> call = twitterClientService.getLikes("10", "false");
        call.enqueue(new Callback<List<LikesResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LikesResponse>> call, @NonNull Response<List<LikesResponse>> response) {
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

              mutableLiveDataLikes.postValue(cardViewList);

            }

            @Override
            public void onFailure(@NonNull Call<List<LikesResponse>> call, @NonNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void lookIfSomeoneIsTwitterUser(String name){
        Call<List<LookUpUserResponse>> call = twitterClientService.lookupUser(name);
        call.enqueue(new Callback<List<LookUpUserResponse>>() {
            @Override
            public void onResponse(Call<List<LookUpUserResponse>>call, Response<List<LookUpUserResponse>> response) {
                if (!response.isSuccessful() && response.code() == 404){
                    user.postValue("Invalid User");
                }else {
                    Log.d("user", response.body().get(0).getScreenName());
                    user.postValue("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LookUpUserResponse>> call,@NonNull Throwable t) {
                Log.d("Error", t.getMessage() );
            }
        });
    }
}
