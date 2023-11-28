package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.billetblaze.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class PersonalInfoFragment extends Fragment {

    boolean assistanceChecked;
    boolean evacChecked;
    String name;
    String city;
    String age;
    String address;
    String phone;
    int genderId;

    private CheckBox assistanceCheck;
    private CheckBox evacCheck;
    private TextInputEditText nameInput;
    private TextInputEditText cityInput;
    private TextInputEditText ageInput;
    private TextInputEditText addressInput;
    private TextInputEditText phoneInput;
    private Button checkinButton;
    private RadioGroup genderGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        assistanceCheck = view.findViewById(R.id.assistanceCheck);
        evacCheck = view.findViewById(R.id.evacCheck);
        nameInput = view.findViewById(R.id.nameInput);
        cityInput = view.findViewById(R.id.cityInput);
        ageInput = view.findViewById(R.id.ageInput);
        addressInput = view.findViewById(R.id.addressInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        checkinButton = view.findViewById(R.id.checkInButton);
        genderGroup = view.findViewById(R.id.genderGroup);

        checkinButton.setOnClickListener(v -> {

            // Storing all the user inputs

            assistanceChecked = assistanceCheck.isChecked();
            evacChecked = evacCheck.isChecked();

            name = Objects.requireNonNull(nameInput.getText()).toString();
            city = Objects.requireNonNull(cityInput.getText()).toString();
            age = Objects.requireNonNull(ageInput.getText()).toString();
            address = Objects.requireNonNull(addressInput.getText()).toString();
            phone = Objects.requireNonNull(phoneInput.getText()).toString();
            genderId = genderGroup.getCheckedRadioButtonId();

            // Some statements to check the validity of the users input

            if (name.trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
            } else if (city.trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid city", Toast.LENGTH_SHORT).show();
            } else if (age.trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid  age", Toast.LENGTH_SHORT).show();
            } else if (address.trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid address", Toast.LENGTH_SHORT).show();
            } else if (phone.trim().isEmpty() && phone.length() > 9) {
                Toast.makeText(getContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            } else if (genderId == -1) {
                Toast.makeText(getContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
