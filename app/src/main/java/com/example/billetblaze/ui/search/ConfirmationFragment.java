package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.billetblaze.R;

public class ConfirmationFragment extends Fragment {

    private TextView checkinTv, checkoutTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        checkinTv = view.findViewById(R.id.checkinTv);
        checkoutTv = view.findViewById(R.id.checkoutTv);

        BilletSharedData bsd = new ViewModelProvider(requireActivity()).get(BilletSharedData.class);

        bsd.getStartDate().observe(getViewLifecycleOwner(), startDate -> checkinTv.setText(startDate));

        bsd.getEndDate().observe(getViewLifecycleOwner(), endDate -> checkoutTv.setText(endDate));

        return view;
    }
}
