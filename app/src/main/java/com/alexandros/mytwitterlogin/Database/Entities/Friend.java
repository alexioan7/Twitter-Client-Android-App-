package com.alexandros.mytwitterlogin.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend_table")
public class Friend {
    @PrimaryKey(autoGenerate = true)
    int id;

    String name;

    public void setId(int id) {
        this.id = id;
    }

    public Friend(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
