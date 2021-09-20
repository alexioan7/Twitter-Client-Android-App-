package com.alexandros.mytwitterlogin.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hometimeline_table")
public class HomeTimeline {
    @PrimaryKey(autoGenerate = true)
    int id;

    String timeline_tweet;

    public HomeTimeline(String timeline_tweet) {
        this.timeline_tweet = timeline_tweet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTimeline_tweet() {
        return timeline_tweet;
    }
}
