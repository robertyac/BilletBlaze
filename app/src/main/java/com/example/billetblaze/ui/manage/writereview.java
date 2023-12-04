package com.example.billetblaze.ui.manage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.billetblaze.R;


public class writereview extends Fragment {


    private String review;
    private Button submitButton;
    private Button cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_writereview, container, false);
        submitButton = root.findViewById(R.id.button);
        cancelButton = root.findViewById(R.id.button2);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_writereview, container, false);
    }
}