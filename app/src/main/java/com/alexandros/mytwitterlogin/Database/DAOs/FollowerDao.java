package com.alexandros.mytwitterlogin.Database.DAOs;

import com.alexandros.mytwitterlogin.RESTApi.response.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FollowerDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM follower_table")
    void deleteAllFollowers();

    @Query("SELECT* FROM follower_table")
    LiveData<List<User>> getAllFollowersFromDB();
}
