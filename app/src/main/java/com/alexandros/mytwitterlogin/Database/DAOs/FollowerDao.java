package com.alexandros.mytwitterlogin.Database.DAOs;

import com.alexandros.mytwitterlogin.Database.Entities.Follower;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.ForeignKey;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FollowerDao {

    //insert all Followers
    @Insert
    void insertAll(Follower... followers);

    @Insert
    void insert(Follower follower);

    @Update
    void update(Follower follower);

    //delete all Followers
    @Delete
    void deleteAll(Follower... followers);

    @Delete
    void delete(Follower follower);

    @Query("delete from Follower")
    void deleteAll();

    // select all users
    @Query("SELECT * FROM Follower")
    List<Follower> getAll();
}
