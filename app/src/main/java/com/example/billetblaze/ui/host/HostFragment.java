package com.example.billetblaze.ui.host;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.billetblaze.MapsActivity;
import com.example.billetblaze.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HostFragment extends Fragment
{
    //Declare class variables
    private Button hostLocationButton, hostDatesButton, hostGuestsButton, hostServicesButton, hostPriceButton, hostBilletButton;
    private TextView hostLocationView, hostDatesView, hostGuestsView, hostServicesView, hostPriceView;
    private String location, startDate, endDate, dateRange;
    private int guests,price;
    public static List<String> allSelectedServices = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_host, container, false);
        // back button back to search fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allSelectedServices.clear();

        //Initializes all button widgets
        hostLocationButton = view.findViewById(R.id.hostLocationButton);
        hostDatesButton = view.findViewById(R.id.hostDatesButton);
        hostGuestsButton = view.findViewById(R.id.hostGuestsButton);
        hostServicesButton = view.findViewById(R.id.hostServicesButton);
        hostPriceButton = view.findViewById(R.id.hostPriceButton);
        hostBilletButton = view.findViewById(R.id.hostBilletButton);
        //Initializes all TextView widgets
        hostLocationView = view.findViewById(R.id.hostLocationView);
        hostDatesView = view.findViewById(R.id.hostDatesView);
        hostGuestsView = view.findViewById(R.id.hostGuestsView);
        hostPriceView = view.findViewById(R.id.hostPriceView);
        hostServicesView = view.findViewById(R.id.hostServicesView);

        //Executes all methods
        getLocation();
        getDates();
        getMaxGuests();
        getServices();
        getPrice();
        validateAndHostBillet();

        return view;
    }

    private void getLocation()
    {
        //Logic to store location String selected from map activity
        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data = result.getData();
                            location = data.getStringExtra("city");
                            Log.d("FindBilletFragment", "City: " + location);
                            hostLocationView.setText(location);
                        }
                    }
                });

        hostLocationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creates an intent to launch the map
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                mStartForResult.launch(intent);
            }
        });
    }

    private void getDates()
    {
        //Sets click functionality for dates button
        hostDatesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creates a calendar dialog
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

                // Get today's date and users time zone for current date
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                long today = calendar.getTimeInMillis();

                //Sets calendar constraints (user can't pick a date range that starts in the past)
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
                constraintsBuilder.setStart(today);
                constraintsBuilder.setValidator(DateValidatorPointForward.now());

                builder.setCalendarConstraints(constraintsBuilder.build());

                MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
                picker.show(getChildFragmentManager(), picker.toString());

                //Gets selected date range
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>()
                {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection)
                    {
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

                        dateRange = startDate + "\n to \n" + endDate;
                        hostDatesView.setText(dateRange);
                    }
                });
            }
        });
    }

    private void getMaxGuests()
    {
        //Sets click functionality for guests button
        hostGuestsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creates an input dialog for maximum guests
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Maximum Number of Allowed Guests");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                //Sets up the OK button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Logic for setting guests textView with error handling
                        String numGuests = input.getText().toString();
                        if (!numGuests.isEmpty())
                        {
                            guests = Integer.parseInt(numGuests);
                            if (guests > 0)
                            {
                                hostGuestsView.setText(numGuests + " Guests");
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Please select a valid amount of guests.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Please select the maximum amount of guests.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Sets up Cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    private void getServices()
    {
        //Sets click functionality for services button
        hostServicesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Inflates the custom layout
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_services, null);

                //Defines the items for the checkbox list
                final String[] inHome = {"Individual Showers", "Washing Machine", "Drying Machine", "Kitchen", "First Aid Supplies", "Child-Friendly", "Pet-Friendly", "Provided Meals", "Cellular Access", "Internet Access"};
                final String[] nearby = {"Emergency Medical Services", "Mental Health Support Services", "Child Care Services", "Communal Washroom", "Gas Station", "Grocery Store", "Pharmacy", "Free Parking Lot", "Public Transportation Route", "Information/Resource Center"};

                //Restores stored checkbox states and update the checkbox list
                for (int i = 0; i < inHome.length; i++)
                {
                    CheckBox checkBox = dialogView.findViewWithTag("inHome" + i);
                    if (checkBox != null)
                    {
                        checkBox.setChecked(allSelectedServices.contains(inHome[i]));
                    }
                }
                for (int i = 0; i < nearby.length; i++)
                {
                    CheckBox checkBox = dialogView.findViewWithTag("nearby" + i);
                    if (checkBox != null)
                    {
                        checkBox.setChecked(allSelectedServices.contains(nearby[i]));
                    }
                }

                //Creates an input dialog for services
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Services");
                builder.setView(dialogView);

                //Sets up the OK button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Clears the list to update it with new services
                        allSelectedServices.clear();

                        //Checks which in-home services are selected and adds them to our list<String>
                        for (int i = 0; i < inHome.length; i++)
                        {
                            CheckBox checkBox = dialogView.findViewWithTag("inHome" + i);
                            if (checkBox != null && checkBox.isChecked())
                            {
                                allSelectedServices.add(inHome[i]);
                            }
                        }

                        //Checks which nearby services are selected and adds them to our list<String>
                        for (int i = 0; i < nearby.length; i++)
                        {
                            CheckBox checkBox = dialogView.findViewWithTag("nearby" + i);
                            if (checkBox != null && checkBox.isChecked())
                            {
                                allSelectedServices.add(nearby[i]);
                            }
                        }

                        //Updates the services text view
                        TextView hostServicesView = getActivity().findViewById(R.id.hostServicesView);
                        String servicesCountText = "Selected Services: " + allSelectedServices.size();
                        hostServicesView.setText(servicesCountText);
                    }
                });

                //Sets up Cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    private void getPrice()
    {
        //Sets click functionality for price button
        hostPriceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creates an input dialog for price
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Nightly Price per Guest (CAD): ");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                //Sets up OK button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Logic to display entered price in the price TextView
                        String numPrice = input.getText().toString();
                        if (!numPrice.isEmpty())
                        {
                            price = Integer.parseInt(numPrice);
                            if (price > 0)
                            {
                                hostPriceView.setText("$" + price);
                            }
                            else if (price == 0)
                            {
                                hostPriceView.setText("Free");
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Please select a positive number for the nightly price.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            hostPriceView.setText("Free");
                        }
                    }
                });

                //Sets up Cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    private void validateAndHostBillet()
    {
        //Sets click functionality for List Billet button
        hostBilletButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Error handling to check that all attributes have been entered.
                if (hostLocationView.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Please set the location.",Toast.LENGTH_SHORT).show();
                }
                if (hostDatesView.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Please set the dates that it is available.",Toast.LENGTH_SHORT).show();
                }
                if (hostGuestsView.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Please set the maximum number of guests.",Toast.LENGTH_SHORT).show();
                }
                if (hostServicesView.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Please select at least one available service.",Toast.LENGTH_SHORT).show();
                }
                else if (!hostLocationView.getText().toString().isEmpty() && !hostDatesView.getText().toString().isEmpty()
                        && !hostGuestsView.getText().toString().isEmpty() && !hostServicesView.getText().toString().isEmpty())
                {
                    if(writeToDevice(location, startDate, endDate, guests, allSelectedServices, price))
                    {
                        //Creates a congratulations dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Congratulations!");
                        builder.setMessage("Your listing was created successfully.\n\nThank you for your generous contribution, \n\nHave a wonderful day!");

                        //Sets up the dismiss button
                        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Dismisses the dialog
                                dialog.dismiss();
                            }
                        });

                        //Sets up the Home Page button
                        builder.setNeutralButton("Home Page", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Navigates back to the home fragment
                                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            }
                        });

                        builder.show();
                    }
                    else
                    {
                        //Creates a failure dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Uh Oh!");
                        builder.setMessage("We failed to post your listing.\n\nWe apologize for the inconvenience,\n\nPlease ensure you have a stable internet connection and try again!");

                        //Sets up the Dismiss button
                        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        });

                        //Sets up the Home Page button
                        builder.setNeutralButton("Home Page", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Navigates back to the home fragment
                                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            }
                        });

                        builder.show();
                    }
                }
            }
        });
    }

    //Write to local internal storage (hostedBillets.txt)
    protected boolean writeToDevice(String location, String startDate, String endDate, int guests, List<String> finalServices, int price)
    {
        String allServices = "";
        for (String service : finalServices)
        {
            allServices += service + ", ";
        }

        // Format the values to save in the text file.
        String content = location + "," + startDate + "," + endDate + "," + guests + "," + "Services: [" + allServices.trim() + "]," + price + "\n";
        //Determine where to save the content.
        String fileName = "hostedBillets.txt";
        File path = getActivity().getFilesDir();

        //Creates the FileOutputStream object and closes it after writing.
        try
        {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName), true);
            writer.write(content.getBytes());
            writer.flush();
            writer.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}