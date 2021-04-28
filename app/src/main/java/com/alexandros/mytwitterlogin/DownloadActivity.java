package com.alexandros.mytwitterlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alexandros.mytwitterlogin.RESTApi.response.FollowersResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.FriendsResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.LikesResponse;
import com.alexandros.mytwitterlogin.RESTApi.TwitterClientService;
import com.alexandros.mytwitterlogin.RESTApi.response.User;
import com.google.android.material.tabs.TabLayoutMediator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DownloadActivity extends AppCompatActivity {
    List<User> followerList;
    List<User> friendList;
    List<HomeTimelineResponse> homeTimelineList;
    List<LikesResponse> listOfLikes;
    String loggedInTwitterUserScreenName;
    ArrayList<Fragment> fragments = new ArrayList<>();


    //RecyclerView
    private ViewPager2 mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    private static final String TAG = "DownloadActivity";


    Oauth1SigningInterceptor.Clock clock = new Oauth1SigningInterceptor.Clock();
    Random random = new Random();

    private TwitterClientService twitterClientService;

    private String accessToken;
    private String accessTokenSecret;
    private String consumerKey;
    private String consumerSecret;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        mRecyclerView = findViewById(R.id.view_pager);

        fragments.add(new FollowersFragment());

        fragments.add(new FriendsFragment());

        fragments.add(new HomeTimelineFragment());

        fragments.add(new LikesFragment());

        FragmentStateAdapter stateAdapter = new FragmentsHostAdapter(this.getSupportFragmentManager(),getLifecycle(),fragments);
        mRecyclerView.setAdapter(stateAdapter);
        new TabLayoutMediator(findViewById(R.id.tab_layout), findViewById(R.id.view_pager), true, (((tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("followers");
                    break;
                case 1:
                    tab.setText("friends");
                    break;
                case 2:
                    tab.setText("home timeline");
                    break;
                case 3:
                    tab.setText("likes");
                    break;
            }
        }))).attach();






        Intent intent = getIntent();
        accessToken = intent.getExtras().getString("accessToken");
        accessTokenSecret = intent.getExtras().getString("accessTokenSecret");
        loggedInTwitterUserScreenName = intent.getExtras().getString("loggedInTwitterUserScreenName");
    }
}



