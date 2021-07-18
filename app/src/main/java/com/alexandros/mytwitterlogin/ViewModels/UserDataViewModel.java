package com.alexandros.mytwitterlogin.ViewModels;

import com.alexandros.mytwitterlogin.Adapters.CardViewItem;
import com.alexandros.mytwitterlogin.Database.Entities.Follower;
import com.alexandros.mytwitterlogin.Activities.MainActivity;
import com.alexandros.mytwitterlogin.Repositories.Repository;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserDataViewModel extends ViewModel {

    private final Repository repository;
    private final String accessToken,accessTokenSecret;



    public  UserDataViewModel(String accessToken, String accessTokenSecret) {
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;

        repository = Repository.getInstance( MainActivity.getAppContext(), accessToken,accessTokenSecret);
        repository.getFollowers();
        repository.getFriends();
        repository.getHomeTimeline();
        repository.getLikes();




    }


    /*
    public LiveData<List<CardViewItem>> getFollowersLive() {
        return repository.getFollowersLive();
    }
    */
    public LiveData<List<Follower>> getFollowersFromDBLive(){
        return repository.getFollowersFromDB();
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

    public  int getSeparation(Python python,String uName, String tName){

      PyObject module =  python.getModule("digsep").callAttr("main", uName, tName,accessToken,accessTokenSecret);
      return  module.toInt();

    }




}
