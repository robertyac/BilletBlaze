package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;
import com.example.billetblaze.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private Button findBilletButton, nearbyHelpButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        findBilletButton = root.findViewById(R.id.findBilletButton);
        nearbyHelpButton = root.findViewById(R.id.nearbyHelpButton);

        findBilletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the NavController to navigate to the FindBilletFragment
                Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_navigation_findBillet);
            }
        });

        nearbyHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the NavController to navigate to the NewsFragment
                Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_navigation_News);
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
