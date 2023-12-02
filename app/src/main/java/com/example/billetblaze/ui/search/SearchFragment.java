package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;
import com.example.billetblaze.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private Button findBilletButton, nearbyHelpButton;
    private ImageView fireImage;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // back button
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findBilletButton = root.findViewById(R.id.findBilletButton);
        nearbyHelpButton = root.findViewById(R.id.nearbyHelpButton);

        fireImage = root.findViewById(R.id.imageViewFireSearch);
        fireImage.setImageResource(R.drawable.bigfire);
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
