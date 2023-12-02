package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.BilletDetail;
import com.example.billetblaze.R;

public class BilletDetailFragment extends Fragment {
    private TextView billetTittleTv, billetDescriptionTV, ppnTv, priceView;
    private Button bookButton;
    private ImageView billetImage;
    private String startDate, endDate, city, desc;
    private int numGuests, price;
    private List<String> amenities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billet_detail, container, false);
        BilletSharedData bsd = new ViewModelProvider(requireActivity()).get(BilletSharedData.class);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get specific billet details
        Bundle args = getArguments();
        BilletDetail clickedBillet = (BilletDetail) args.getSerializable("clicked_billet");
        city = clickedBillet.getLocation();
        startDate = clickedBillet.getStartDate();
        endDate = clickedBillet.getEndDate();
        price = clickedBillet.getPrice();
        amenities = clickedBillet.getAmenities();
        numGuests = clickedBillet.getMaxGuests();

        // Remove the brackets from the amenities list
        String amenitiesStr = amenities.toString();
        amenitiesStr = amenitiesStr.substring(1, amenitiesStr.length() - 1); // This will remove the brackets

        // Remove trailing comma
        amenitiesStr = amenitiesStr.trim();
        if (amenitiesStr.endsWith(",")) {
            amenitiesStr = amenitiesStr.substring(0, amenitiesStr.length() - 1);
        }

        // Initialize views
        billetTittleTv = view.findViewById(R.id.billetTittleTv);
        billetDescriptionTV = view.findViewById(R.id.billeDescriptionTV);
        bookButton = view.findViewById(R.id.bookButton);
        billetImage = view.findViewById(R.id.billetImage);
        ppnTv = view.findViewById(R.id.ppnView);
        priceView = view.findViewById(R.id.priceView);

        // Set text for views
        desc = "This spacious and comfortable house is located in a peaceful neighborhood. The house features three bedrooms, two bathrooms, a fully equipped kitchen, and a living room. " +
                "The house is conveniently located close to local attractions and the local ESS station in " + city + ".";

        billetDescriptionTV.setText("This Billet is available in " + city + "\n\n - From " + startDate + " to " + endDate +
                "\n\n - For up to " + numGuests + " Guests.\n\n" + desc + "\n\n Amenities: " + amenitiesStr);

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

}
