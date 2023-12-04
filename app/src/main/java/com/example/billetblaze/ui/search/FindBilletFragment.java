package com.example.billetblaze.ui.search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.BilletDetail;
import com.example.billetblaze.MapsActivity;
import com.example.billetblaze.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

public class FindBilletFragment extends Fragment {
    private BilletSharedData bsd;
    private BilletDetailAdapter billetDetailAdapter;
    private List<BilletDetail> billetDetailList = new ArrayList<>();
    private Button destinationButton, datesButton, guestsButton, findBilletsButton;
    private TextView destinationTv, datesTv, guestsTv;
    private String startDate, endDate, dateRange, city;
    private int numGuestsInt;

    private Scanner scanner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // New instance for storing the live data (Checkin/Checkout)
        bsd = new ViewModelProvider(requireActivity()).get(BilletSharedData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_billet, container, false);

        // back button back to search fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        destinationButton = view.findViewById(R.id.destinationButton);
        datesButton = view.findViewById(R.id.datesButton);
        guestsButton = view.findViewById(R.id.guestsButton);
        findBilletsButton = view.findViewById(R.id.findBilletsButton);
        destinationTv = view.findViewById(R.id.destinationTv);
        datesTv = view.findViewById(R.id.datesTv);
        guestsTv = view.findViewById(R.id.guestsTv);
        datesButton = view.findViewById(R.id.datesButton);

        getUserDestination();
        getDateRange();
        getGuests();
        validateAndFindAvailableBillets();

        return view;
    }

    private void getUserDestination() {
        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            city = data.getStringExtra("city");
                            Log.d("FindBilletFragment", "City: " + city);
                            destinationTv.setText(city);
                        }
                    }
                });

        destinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to launch the map
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                mStartForResult.launch(intent);
            }
        });
    }

    private void getDateRange() {
        //MaterialDatePicker android devs
        datesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

                // Get today's date
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault()); //get users time zone for current date
                long today = calendar.getTimeInMillis();

                // Set calendar constraints --> user can't pick a date range that starts in the past
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
                constraintsBuilder.setStart(today);
                constraintsBuilder.setValidator(DateValidatorPointForward.now());

                builder.setCalendarConstraints(constraintsBuilder.build());

                MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
                picker.show(getChildFragmentManager(), picker.toString());

                //Get date range
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        // selection.first and selection.second in ms
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        sdf.setTimeZone(TimeZone.getDefault()); //set time zone to user's time zone
                        // Add one day to the selected dates to adjust for the time zone difference
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(selection.first);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        startDate = sdf.format(cal.getTime());

                        cal.setTimeInMillis(selection.second);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        endDate = sdf.format(cal.getTime());

                        dateRange = startDate + " to " + endDate;
                        datesTv.setText(dateRange);

                    }
                });
            }
        });
    }

    private void getGuests() {
        guestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Number of Guests");

                // Set up the input
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String numGuests = input.getText().toString();
                        bsd.setNumGuests(numGuests);
                        if (!numGuests.isEmpty()) {
                            numGuestsInt = Integer.parseInt(numGuests);
                            if (numGuestsInt > 0) {
                                guestsTv.setText(numGuests);
                            } else {
                                Toast.makeText(getActivity(), "Please select a valid amount of guests.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please select the amount of guests.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Show the dialog
                builder.show();
            }
        });
    }

    private void validateAndFindAvailableBillets() {
        findBilletsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (destinationTv.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please select a destination.", Toast.LENGTH_SHORT).show();
                }
                if (datesTv.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please select dates.", Toast.LENGTH_SHORT).show();
                }
                if (guestsTv.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please select the number of guests.", Toast.LENGTH_SHORT).show();
                }

                if (!destinationTv.getText().toString().isEmpty()
                        && !datesTv.getText().toString().isEmpty()
                        && !guestsTv.getText().toString().isEmpty()) {

                    // Setting the checkin/checkout values so we can reference inside of the confirmation page.
                    bsd.setStartDate(startDate);
                    bsd.setEndDate(endDate);

                    File file = new File(getActivity().getFilesDir(), "hostedBillets.txt");
                    if (!file.exists()) {
                        Toast.makeText(getContext(), "No available billets found. No Records.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Read the data from your text file into billetDetailList
                        billetDetailList = new ArrayList<>();
                        try {
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

                        // Check if the user's location, and number of guests match any of the available billets
                        boolean isBilletAvailable = false;
                        for (BilletDetail billetDetail : billetDetailList) {
                            if (city.equals(billetDetail.getLocation())
                                    && numGuestsInt <= billetDetail.getMaxGuests()) {
                                isBilletAvailable = true;
                                break;
                            }
                        }

                        if (!isBilletAvailable) {
                            Toast.makeText(getContext(), "No available billets found.", Toast.LENGTH_SHORT).show();
                        } else {
                            // All fields have been completed, navigate to the next fragment
                            Bundle args = new Bundle();
                            args.putString("dateRange", dateRange);
                            args.putString("city", city);
                            args.putInt("numGuests", numGuestsInt);
                            Navigation.findNavController(v).navigate(R.id.action_navigation_findBillet_to_navigation_searchResults, args);
                        }
                    }
                }
            }
        });
    }



}
