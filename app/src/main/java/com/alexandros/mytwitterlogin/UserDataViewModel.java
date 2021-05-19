package com.alexandros.mytwitterlogin;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserDataViewModel extends ViewModel {

    private final Repository repository;





    public  UserDataViewModel(String accessToken,String accessTokenSecret) {

        repository = Repository.getInstance(accessToken,accessTokenSecret);
        repository.getFollowers();
        repository.getFriends();
        repository.getHomeTimeline();
        repository.getLikes();


    }


    public LiveData<List<CardViewItem>> getFollowersLive() {
        return repository.getFollowersLive();
    }

    public LiveData<List<CardViewItem>> getFriendsLive() {

        return repository.getFriendsLive();
    }

    public LiveData<List<CardViewItem>> getHomeTimelineLive() {
        return repository.getHomeTimelineLive();
    }

    public LiveData<List<CardViewItem>> getLikesLive() {
        return repository.getLikesLive();
    }




}
