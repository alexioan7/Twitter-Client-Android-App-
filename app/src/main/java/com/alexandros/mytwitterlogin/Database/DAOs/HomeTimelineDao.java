package com.alexandros.mytwitterlogin.Database.DAOs;

import com.alexandros.mytwitterlogin.RESTApi.response.HomeTimelineResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface HomeTimelineDao {

    @Insert
    void insert(HomeTimelineResponse home_tl_tweet);

    @Update
    void update(HomeTimelineResponse home_tl_tweet);

    @Delete
    void delete(HomeTimelineResponse home_tl_tweet);

    @Query("DELETE FROM hometimeline_table")
    void deleteAllHomeTimelineTweets();

    @Query("SELECT* FROM hometimeline_table")
    LiveData<List<HomeTimelineResponse>> getHomeTimelineFromDB();
}
