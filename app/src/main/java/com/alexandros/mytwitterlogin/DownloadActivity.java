package com.alexandros.mytwitterlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;

import com.alexandros.mytwitterlogin.Fragments.FollowersFragment;
import com.alexandros.mytwitterlogin.Fragments.FriendsFragment;
import com.alexandros.mytwitterlogin.Fragments.HomeTimelineFragment;
import com.alexandros.mytwitterlogin.Fragments.LikesFragment;
import com.alexandros.mytwitterlogin.RESTApi.response.HomeTimelineResponse;
import com.alexandros.mytwitterlogin.RESTApi.response.LikesResponse;
import com.alexandros.mytwitterlogin.RESTApi.TwitterClientService;
import com.alexandros.mytwitterlogin.RESTApi.response.User;
import com.google.android.material.tabs.TabLayoutMediator;


import java.util.ArrayList;
import java.util.List;


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

    }
}



