package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billetblaze.R;

public class SearchResultsFragment extends Fragment {

    private String dateRange, city;
    private int numGuests;
    private TextView dateRangeTv, cityTv, numGuestsTv;

    private CardView billetCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        Bundle args = getArguments();

        dateRange = args.getString("dateRange");
        city = args.getString("city");
        numGuests = args.getInt("numGuests");

        dateRangeTv = view.findViewById(R.id.dateRangeTv);
        numGuestsTv = view.findViewById(R.id.numGuestsTv);
        cityTv = view.findViewById(R.id.cityTv);

        dateRangeTv.setText("Dates: " + dateRange);
        numGuestsTv.setText(String.valueOf(numGuests) + " Guests");
        cityTv.setText(city);

        // back button back to search
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        billetCard = view.findViewById(R.id.billet_card);
        billetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("dateRange", dateRange);
                args.putString("city", city);
                args.putInt("numGuests",numGuests);
                //click event
                Navigation.findNavController(v).navigate(R.id.action_navigation_searchResults_to_navigation_BilletDetail,args);
            }
        });



        return view;
    }
}