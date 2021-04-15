package com.alexandros.mytwitterlogin.Database.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "TwitterUser")
public class TwitterUser {

    @PrimaryKey
    private long twitterUserId;

    private String name;

    private String screenName;

    private String description;


    @Override
    public String toString() {
        return "TwitterUser{" +
                ", twitterUserId=" + twitterUserId +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }



    public void setTwitterUserId(long twitterUserId) {
        this.twitterUserId = twitterUserId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public long getTwitterUserId() {
        return twitterUserId;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getDescription() {
        return description;
    }
}
