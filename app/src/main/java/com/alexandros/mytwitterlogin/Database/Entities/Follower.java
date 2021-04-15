package com.alexandros.mytwitterlogin.Database.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/*
(foreignKeys =  {@ForeignKey(entity = TwitterUser.class, parentColumns = "twitterUserId", childColumns = "twitterUserId", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = TwitterUser.class, parentColumns = "twitterUserId", childColumns = "followerId", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
 */

@Entity

public class Follower {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private long twitterUserId;

    private long followerId;




    public void setId(int id) {
        this.id = id;
    }

    public void setTwitterUserId(long twitterUserId) {
        this.twitterUserId = twitterUserId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public int getId() {
        return id;
    }

    public long getTwitterUserId() {
        return twitterUserId;
    }

    public long getFollowerId() {
        return followerId;
    }
}
