package com.alexandros.mytwitterlogin.RESTApi;


import com.alexandros.mytwitterlogin.RESTApi.response.FollowersResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.FriendsResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.LikesResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.LookUpUserResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TwitterClientService {


    @GET("followers/list.json")
    Call<FollowersResponse> getFollowers(@Query("count") String n, @Query("include_user_entities") String s, @Query("skip_status") String status);

    @GET("friends/list.json")
    Call<FriendsResponse> getFriends(@Query("count") String n, @Query("include_user_entities") String s, @Query("skip_status") String status);


    @GET("statuses/home_timeline.json")
    Call<List<HomeTimelineResponse>> getHomeTimeline(@Query("count") String n, @Query("include_entities") String s, @Query("exclude_replies") String replies);


    @GET("favorites/list.json")
    Call<List<LikesResponse>> getLikes(@Query("count") String n, @Query("include_entities") String s);

    @GET("users/lookup.json")
    Call<List<LookUpUserResponse>> lookupUser(@Query("screen_name") String screenName);





}
