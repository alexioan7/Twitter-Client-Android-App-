package com.alexandros.mytwitterlogin.Database.DAOs;


import com.alexandros.mytwitterlogin.Database.Entities.Like;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LikeDao {

    @Insert
    void insert(Like liked_tweet);

    @Update
    void update(Like liked_tweet);

    @Delete
    void delete(Like liked_tweet);


    @Query("DELETE FROM like_table")
    void deleteAllLikes();

    @Query("SELECT* FROM like_table")
    LiveData<List<Like>> getAllLikesFromDB();






}
