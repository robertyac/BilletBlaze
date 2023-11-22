package com.example.billetblaze.ui.commsHub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.billetblaze.databinding.FragmentCommshubBinding;

public class CommsHubFragment extends Fragment {

    private FragmentCommshubBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CommsHubViewModel commsHubViewModel =
                new ViewModelProvider(this).get(CommsHubViewModel.class);

        binding = FragmentCommshubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}