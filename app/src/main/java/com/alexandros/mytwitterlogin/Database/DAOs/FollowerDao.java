package com.alexandros.mytwitterlogin.Database.DAOs;

import com.alexandros.mytwitterlogin.Database.Entities.Follower;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FollowerDao {

    @Insert
    void insert(Follower follower);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Follower> followers);

    @Update
    void update(Follower follower);

    @Delete
    void delete(Follower follower);



    @Query("DELETE FROM follower_table")
    void deleteAllFollowers();

    @Query("SELECT* FROM follower_table")
    LiveData<List<Follower>> getAllFollowersFromDB();
}
