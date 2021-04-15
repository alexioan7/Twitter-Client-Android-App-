package com.alexandros.mytwitterlogin.Database;

import android.content.Context;

import com.alexandros.mytwitterlogin.Database.DAOs.FollowerDao;
import com.alexandros.mytwitterlogin.Database.DAOs.TwitterUseDao;
import com.alexandros.mytwitterlogin.Database.Entities.Follower;
import com.alexandros.mytwitterlogin.Database.Entities.TwitterUser;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {TwitterUser.class, Follower.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase instance;

    public abstract TwitterUseDao twitterUseDao();
    public abstract FollowerDao followerDao();

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "twitter_app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
