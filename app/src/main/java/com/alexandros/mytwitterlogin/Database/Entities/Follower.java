package com.alexandros.mytwitterlogin.Database.Entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "follower_table")
public class Follower {

    @PrimaryKey()
    @NonNull private String name;

    public Follower(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

}
