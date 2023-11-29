package com.example.billetblaze.ui.manage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.billetblaze.Billet;
import com.example.billetblaze.R;
import com.example.billetblaze.databinding.FragmentSearchBinding;

import java.util.List;


public class ManageFragment extends Fragment {
    private FragmentSearchBinding binding;
    private String selectedItem;
    private List<Billet> billetList;

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                // Do something with the selected item
                // For example, display a Toast message
                Toast.makeText(root.getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
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

        billetList.add(new Billet("Sarah M.", "1889 Mane Rd, Portland, OR", "2023-8-16", "2023-9-14"));

    }



}