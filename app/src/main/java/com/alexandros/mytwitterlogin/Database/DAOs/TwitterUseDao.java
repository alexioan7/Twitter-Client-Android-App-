package com.alexandros.mytwitterlogin.Database.DAOs;

import com.alexandros.mytwitterlogin.Database.Entities.TwitterUser;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TwitterUseDao {

    //insert all TwitterUsers
    @Insert
    void insertAll(TwitterUser... twitterUsers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TwitterUser twitterUser);

    @Update
    void update(TwitterUser twitterUser);

    //delete all TwitterUsers
    @Delete
    void deleteAll(TwitterUser... twitterUsers);

    @Query("delete from TwitterUser")
    void deleteAll();

    @Delete
    void delete(TwitterUser twitterUser);


    // select all users
    @Query("SELECT * FROM TwitterUser ORDER BY name DESC")
    List<TwitterUser> getAllTwitterUsers();


    //count all
    @Query("SELECT  count(*)  FROM TwitterUser")
    int getCount();


}
