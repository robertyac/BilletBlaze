package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billetblaze.R;

public class FindBilletFragment extends Fragment {

    private Button destinationButton,datesButton,guestsButton,findBilletsButton;
    private TextView destinationTv,datesTv,guestsTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_billet, container, false);

        destinationButton = view.findViewById(R.id.destinationButton);
        datesButton = view.findViewById(R.id.datesButton);
        guestsButton = view.findViewById(R.id.guestsButton);
        findBilletsButton = view.findViewById(R.id.findBilletsButton);
        destinationTv = view.findViewById(R.id.destinationTv);
        datesTv = view.findViewById(R.id.datesTv);
        guestsTv = view.findViewById(R.id.guestsTv);

        // Enable the back button
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }
}
