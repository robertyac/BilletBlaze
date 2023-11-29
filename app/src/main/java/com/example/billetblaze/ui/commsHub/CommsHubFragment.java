package com.example.billetblaze.ui.commsHub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;
import com.example.billetblaze.databinding.FragmentCommshubBinding;

public class CommsHubFragment extends Fragment {

    private FragmentCommshubBinding binding;
    private Button newsButton, messagesButton;
    private ImageView fireImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CommsHubViewModel commsHubViewModel =
                new ViewModelProvider(this).get(CommsHubViewModel.class);

        binding = FragmentCommshubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        newsButton = root.findViewById(R.id.newsUpdatesButton);
        messagesButton = root.findViewById(R.id.messagesButton);

        fireImage = root.findViewById(R.id.imageViewFireComms);
        fireImage.setImageResource(R.drawable.bigfire);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the NavController to navigate to the news
                Navigation.findNavController(v).navigate(R.id.action_navigation_commsHub_to_navigation_News);
            }
        });

        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the NavController to navigate to the messages
                Navigation.findNavController(v).navigate(R.id.action_navigation_commsHub_to_navigation_Messages);
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