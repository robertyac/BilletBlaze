package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;

public class BilletDetailFragment extends Fragment {
    private TextView billetTittleTv, billetDescriptionTV, ppnTv, priceView;
    private Button bookButton;
    private ImageView billetImage;
    private String dateRange, city, desc;
    private int numGuests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BilletSharedData bsd = new ViewModelProvider(requireActivity()).get(BilletSharedData.class);
        View view = inflater.inflate(R.layout.fragment_billet_detail, container, false);

        int price = priceGenerate(60, 200);
        billetTittleTv = view.findViewById(R.id.billetTittleTv);
        billetDescriptionTV = view.findViewById(R.id.billeDescriptionTV);
        bookButton = view.findViewById(R.id.bookButton);
        billetImage = view.findViewById(R.id.billetImage);
        ppnTv = view.findViewById(R.id.ppnView);
        priceView = view.findViewById(R.id.priceView);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getArguments();
        dateRange = args.getString("dateRange");
        city = args.getString("city");
        numGuests = args.getInt("numGuests");

        desc = "This spacious and comfortable house is located in a peaceful neighborhood. The house features three bedrooms, two bathrooms, a fully equipped kitchen, and a living room. " +
                "The house is conveniently located close to local attractions and the local ESS station in " + city + ".";
        billetDescriptionTV.setText("This Billet is available in " + city + "\n\n - On the date " + dateRange +
                "\n\n - For " + numGuests + " Guests.\n\n" + desc);

        billetTittleTv.setText("Cozy Billet in " + city);

        priceView.setText("$ " + String.valueOf(price));

        bsd.setPriceString(String.valueOf(price));

        // On selecting bookButton, we are taken to the personalInformation section.
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_BilletDetail_to_personalInfoFragment);
            }
        });
        return view;
    }

    private int priceGenerate(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
