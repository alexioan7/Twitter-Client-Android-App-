package com.alexandros.mytwitterlogin.Database.Converters;

import com.alexandros.mytwitterlogin.Database.Entities.Follower;
import com.alexandros.mytwitterlogin.RESTApi.response.User;

import java.util.ArrayList;
import java.util.List;

public class FollowerConverter {
    String name;
    List<Follower> followerList = new ArrayList<>();




    public FollowerConverter() {

    }

    public List<Follower> ConvertUserListToFollowerList(List<User> userList){

       for(int i=0; i<userList.size(); i++){
           name = userList.get(i).getName();
           Follower follower = new Follower(name);
           followerList.add(follower);
       }



        return followerList;

    }

}
