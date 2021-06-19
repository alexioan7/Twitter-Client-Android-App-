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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Friend.class, Follower.class, HomeTimeline.class, Like.class}, version = 2,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static volatile MyDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract FriendDao friendDao();
    public abstract FollowerDao followerDao();
    public abstract HomeTimelineDao homeTimelineDao();
    public abstract LikeDao likeDao();

    public static synchronized MyDatabase getDataBase(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "my_database")
                    .fallbackToDestructiveMigration()
                    .setQueryExecutor(databaseWriteExecutor)
                    .build();
        }
        return instance;
    }




}
