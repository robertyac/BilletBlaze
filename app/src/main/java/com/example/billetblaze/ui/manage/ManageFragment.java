package com.example.billetblaze.ui.manage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.billetblaze.R;
import com.example.billetblaze.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;


public class ManageFragment extends Fragment {
    private FragmentSearchBinding binding;
    private String selectedItem;
    private List<Billet> billets;
    private List<Billet> billetsInactive;
    private List<Review> reviews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        //binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_manage, container, false);



        Spinner spinner = root.findViewById(R.id.spinner);
        String[] spinner_items = {"active", "inactive", "reviews"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, spinner_items);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(root.getContext(), R.array.bookings_spinner, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);



        //recycler view stuff
        RecyclerView recyclerViewActive = root.findViewById(R.id.recyclerview);
        RecyclerView recyclerViewInactive = root.findViewById(R.id.recyclerview2);
        RecyclerView recyclerViewReview = root.findViewById(R.id.recyclerview3);



        billets = new ArrayList<Billet>();
        billets.add(new Billet("Sarah Johnsen", "1890 Garage Rd", "2023/7/11", "2023/7/23", 1));
        billets.add(new Billet("Joe Mann", "1995 Cold St", "2023/6/15", "2023/8/12", 1));
        billets.add(new Billet("Bruce Wayne", "1111 Cave Rd", "2023/7/13", "2023/7/15", 1));
        billets.add(new Billet("Bruce Wayne", "1111 Cave Rd", "2023/7/13", "2023/7/15", 1));
        billets.add(new Billet("Bruce Wayne", "1111 Cave Rd", "2023/7/13", "2023/7/15", 1));

        billetsInactive = new ArrayList<Billet>();
        billetsInactive.add(new Billet("Sarah Johnsen", "1890 Garage Rd", "2023/7/11", "2023/7/23", 1));
        billetsInactive.add(new Billet("Joe Mann", "1995 Cold St", "2023/6/15", "2023/8/12", 1));
        billetsInactive.add(new Billet("Bruce Wayne", "1111 Cave Rd", "2023/7/13", "2023/7/15", 1));
        billetsInactive.add(new Billet("Bruce Wayne", "1111 Cave Rd", "2023/7/13", "2023/7/15", 1));

        reviews = new ArrayList<Review>();
        reviews.add(new Review("Sarah Johnsen", "1890 Garage Rd", "2023/7/11", "2023/7/23", 1, "The stay was amazing!"));




        recyclerViewActive.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewActive.setAdapter(new MyAdapter(root.getContext().getApplicationContext(), billets));

        recyclerViewInactive.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewInactive.setAdapter(new MyAdapterInactive(root.getContext().getApplicationContext(), billetsInactive));

        recyclerViewReview.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewReview.setAdapter(new MyAdapterReview(root.getContext().getApplicationContext(), reviews));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                // Do something with the selected item
                // For example, display a Toast message
                Toast.makeText(root.getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                if(selectedItem.equals("active")){
                    recyclerViewActive.setVisibility(View.VISIBLE);
                    recyclerViewInactive.setVisibility(View.GONE);
                    recyclerViewReview.setVisibility(View.GONE);
                }if(selectedItem.equals("inactive")){
                    recyclerViewInactive.setVisibility(View.VISIBLE);
                    recyclerViewActive.setVisibility(View.GONE);
                    recyclerViewReview.setVisibility(View.GONE);
                }if(selectedItem.equals("reviews")){
                    recyclerViewReview.setVisibility(View.VISIBLE);
                    recyclerViewActive.setVisibility(View.GONE);
                    recyclerViewInactive.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });


        // Inflate the layout for this fragment
        return root;
    }

    private void populateBilletList(){

        billets.add(new Billet("Sarah M.", "1889 Mane Rd, Portland, OR", "2023-8-16", "2023-9-14", 1));

    }



}