package com.alexandros.mytwitterlogin.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alexandros.mytwitterlogin.R;
import com.alexandros.mytwitterlogin.RESTApi.RetrofitInstance;
import com.alexandros.mytwitterlogin.RESTApi.response.User;
import com.alexandros.mytwitterlogin.ViewModels.UserDataViewModel;
import com.alexandros.mytwitterlogin.ViewModels.ViewModelFactory;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.List;
import java.util.Objects;

public class DegreeOfSeparationFragment extends Fragment {


    protected Python py;
    protected PyObject module;

    private static Context context;

    String separation = " ";
    String userScreenName = "";
    private SharedPreferences sharedPreferences;
    UserDataViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_degree_of_separation, container, false);

        //Button button = (Button) requireActivity().findViewById(R.id.button);

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(getContext().getPackageName(), Activity.MODE_PRIVATE);

        String accessToken = requireActivity().getIntent().getExtras().getString("accessToken");
        String accessTokenSecret = requireActivity().getIntent().getExtras().getString("accessTokenSecret");

        viewModel = new ViewModelProvider(requireActivity(),
                new ViewModelFactory(accessToken, accessTokenSecret)).get(UserDataViewModel.class);


        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context.getApplicationContext()));

        }

        //py = Python.getInstance();


        return view;


    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editText = view.findViewById(R.id.editText);
        TextView resultTextView = view.findViewById(R.id.resultTextView);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = -1;
                userScreenName = editText.getText().toString();
                result = viewModel.getSeparation(Python.getInstance(), sharedPreferences.getString("user", ""), userScreenName);
                resultTextView.setText(String.valueOf(result));
                Log.i("from fragment", String.valueOf(result));
            }
        });




    }
}