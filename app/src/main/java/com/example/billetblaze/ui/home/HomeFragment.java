package com.example.billetblaze.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;
import com.example.billetblaze.databinding.FragmentHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button hostButton, manageButton, commsButton, mapsButton, searchButton, viewProfileButton;
    private TextView userLocationTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize your buttons and TextView
        hostButton = root.findViewById(R.id.hostButton);
        manageButton = root.findViewById(R.id.manageButton);
        commsButton = root.findViewById(R.id.commsButton);
        mapsButton = root.findViewById(R.id.mapsButton);
        searchButton = root.findViewById(R.id.searchButton);
        viewProfileButton = root.findViewById(R.id.profileButton);
        userLocationTv = root.findViewById(R.id.userLocationTv);

        // Set up OnClickListener for your buttons
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Define what happens when hostButton is clicked
            }
        });

        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Define what happens when manageButton is clicked
            }
        });

        commsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Define what happens when commsButton is clicked
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_commsHub);
            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Define what happens when mapsButton is clicked
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Define what happens when searchButton is clicked

                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Define what happens when viewProfileButton is clicked
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
