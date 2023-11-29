package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;

public class BilletDetailFragment extends Fragment {
    private TextView billetTittleTv, billetDescriptionTV;
    private Button bookButton;
    private ImageView billetImage;
    private String dateRange, city, desc;
    private int numGuests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billet_detail, container, false);

        billetTittleTv = view.findViewById(R.id.billetTittleTv);
        billetDescriptionTV = view.findViewById(R.id.billeDescriptionTV);
        bookButton = view.findViewById(R.id.bookButton);
        billetImage = view.findViewById(R.id.billetImage);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getArguments();
        dateRange = args.getString("dateRange");
        city = args.getString("city");
        numGuests = args.getInt("numGuests");

        desc = "This spacious and comfortable house is located in a peaceful neighborhood. The house features three bedrooms, two bathrooms, a fully equipped kitchen, and a living room. " +
                "The house is conveniently located close to local attractions and the local ESS station in " + city + ".";
        billetDescriptionTV.setText("This Billet is available in " + city + "\n for the dates " + dateRange +
                "\n for " + numGuests + " Guests.\n" + desc);

        billetTittleTv.setText("Cozy Billet in " + city);

        // On selecting bookButton, we are taken to the personalInformation section.
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_BilletDetail_to_personalInfoFragment);
            }
        });
        return view;
    }
}
