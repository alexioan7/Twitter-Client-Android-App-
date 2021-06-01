package com.alexandros.mytwitterlogin.Database;

import android.content.Context;

import com.alexandros.mytwitterlogin.Database.DAOs.FollowerDao;
import com.alexandros.mytwitterlogin.Database.DAOs.FriendDao;
import com.alexandros.mytwitterlogin.Database.DAOs.HomeTimelineDao;
import com.alexandros.mytwitterlogin.Database.DAOs.LikeDao;
import com.alexandros.mytwitterlogin.Database.Entities.Follower;
import com.alexandros.mytwitterlogin.Database.Entities.Friend;
import com.alexandros.mytwitterlogin.Database.Entities.HomeTimeline;
import com.alexandros.mytwitterlogin.Database.Entities.Like;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Friend.class, Follower.class, HomeTimeline.class, Like.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static volatile MyDatabase instance;

    public abstract FriendDao friendDao();
    public abstract FollowerDao followerDao();
    public abstract HomeTimelineDao homeTimelineDao();
    public abstract LikeDao likeDao();

    public static synchronized MyDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "my_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
