package com.alexandros.mytwitterlogin.Database.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "follower_table")
public class Follower {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public Follower(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
