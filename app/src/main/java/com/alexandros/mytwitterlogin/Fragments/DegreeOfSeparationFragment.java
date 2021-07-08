package com.alexandros.mytwitterlogin.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandros.mytwitterlogin.R;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class DegreeOfSeparationFragment extends Fragment {

    protected Python py ;
    protected PyObject module;
    private static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_degree_of_separation, container, false);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(context.getApplicationContext()));

        }
        py = Python.getInstance();
        module = py.getModule("digsep").callAttr("main","alexandrosioan","ChatziSoti");

        return view;


    }

}