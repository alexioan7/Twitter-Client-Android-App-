package com.alexandros.mytwitterlogin.RESTclientServices;


import com.alexandros.mytwitterlogin.RESTclientServices.response.FollowersResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.FriendsResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.LikesResponse;
import com.alexandros.mytwitterlogin.RESTclientServices.response.ShowUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TwitterClientService {


    @GET("followers/list.json")
    Call<FollowersResponse> getFollowers(@Query("include_user_entities") String s, @Query("skip_status") String status);

    @GET("friends/list.json")
    Call<FriendsResponse> getFriends(@Query("count") String n, @Query("include_user_entities") String s, @Query("skip_status") String status);


    @GET("statuses/home_timeline.json")
    Call<List<HomeTimelineResponse>> getHomeTimeline(@Query("count") String n, @Query("include_entities") String s, @Query("exclude_replies") String replies);


    @GET("favorites/list.json")
    Call<List<LikesResponse>> getLikes(@Query("count") String n, @Query("include_entities") String s);

    @GET("users/show.json")
    Call<ShowUserResponse> getLoggedInUser(@Query("screen_name") String screenName, @Query("include_entities") String s, @Query("skip_status") String status);




}
