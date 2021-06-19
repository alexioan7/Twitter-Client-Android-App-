package com.alexandros.mytwitterlogin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandros.mytwitterlogin.Adapters.Adapter;
import com.alexandros.mytwitterlogin.Adapters.CardViewItem;
import com.alexandros.mytwitterlogin.R;
import com.alexandros.mytwitterlogin.ViewModels.UserDataViewModel;
import com.alexandros.mytwitterlogin.ViewModels.ViewModelFactory;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FriendsFragment extends Fragment {

    private UserDataViewModel userDataViewModel;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private Adapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_friends, container, false);


        mRecyclerView = Objects.requireNonNull(view).findViewById(R.id.friends_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        String accessToken = requireActivity().getIntent().getExtras().getString("accessToken");
        String accessTokenSecret = requireActivity().getIntent().getExtras().getString("accessTokenSecret");



        userDataViewModel = new ViewModelProvider(requireActivity(),new ViewModelFactory(accessToken, accessTokenSecret)).get(UserDataViewModel.class);




        // Inflate the layout for this fragment
        return view;



    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        userDataViewModel.getFriendsLive().observe(getViewLifecycleOwner(), cardViewItems -> {
            if (cardViewItems != null) {
                mAdapter = new Adapter((ArrayList<CardViewItem>) cardViewItems);
                mRecyclerView.setAdapter(mAdapter);
            }

        });


    }




}