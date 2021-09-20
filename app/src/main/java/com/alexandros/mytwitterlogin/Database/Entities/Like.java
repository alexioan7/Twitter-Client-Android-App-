package com.alexandros.mytwitterlogin.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "like_table")
public class Like {
    @PrimaryKey(autoGenerate = true)
    int id;

    String liked_tweet;

    public Like(String liked_tweet) {
        this.liked_tweet = liked_tweet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLiked_tweet() {
        return liked_tweet;
    }
}


