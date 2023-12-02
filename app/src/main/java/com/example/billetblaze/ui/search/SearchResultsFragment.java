package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billetblaze.BilletDetail;
import com.example.billetblaze.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SearchResultsFragment extends Fragment {

    private String dateRange, city;
    private int numGuests;
    private TextView dateRangeTv, cityTv, numGuestsTv;
    private CardView billetCard;

    private RecyclerView recyclerView;
    private BilletDetailAdapter billetDetailAdapter;
    private List<BilletDetail> billetDetailList;
    private Scanner scanner;

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
        //user entered values during their search
        dateRangeTv.setText("Dates: " + dateRange);
        numGuestsTv.setText(String.valueOf(numGuests) + " Guests");
        cityTv.setText(city);

        // back button back to search
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize your RecyclerView and set its adapter
        recyclerView = view.findViewById(R.id.recycler_view);

        billetDetailList = new ArrayList<>();
        try {
            File file = new File(getActivity().getFilesDir(), "hostedBillets.txt");
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",", -1);

            String location = parts[0];
            String startDate = parts[1];
            String endDate = parts[2];
            int maxGuests = Integer.parseInt(parts[3]);

// Extract the amenities list by finding the index of the first square bracket and the last square bracket
            int firstBracketIndex = line.indexOf("[");
            int lastBracketIndex = line.lastIndexOf("]");
            String amenitiesString = line.substring(firstBracketIndex + 1, lastBracketIndex);
            List<String> amenities = Arrays.asList(amenitiesString.split(", "));

// Extract the price by getting the part of the line after the last square bracket
            String priceString = line.substring(lastBracketIndex + 2).trim(); // Skip the comma
            int price = Integer.parseInt(priceString);

            billetDetailList.add(new BilletDetail(location, startDate, endDate, maxGuests, amenities, price));
        }
        scanner.close();

        billetDetailAdapter = new BilletDetailAdapter(billetDetailList);
        recyclerView.setAdapter(billetDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}