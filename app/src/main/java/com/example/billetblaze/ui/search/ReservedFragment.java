package com.example.billetblaze.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReservedFragment extends Fragment {

    private TextView checkinTv, checkoutTv, priceCView, paymentMView, takenBookingId, numGuestsView, messageView;
    private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYQ";

    private Button homeButton, manageBookings;
    private static final String numbers = "0123456789";
    private static final int len = 3;
    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserved, container, false);
        String bookingId = generateBookingID();
        checkinTv = view.findViewById(R.id.checkinTv);
        checkoutTv = view.findViewById(R.id.checkoutTv);
        priceCView = view.findViewById(R.id.priceCView);
        paymentMView = view.findViewById(R.id.paymentMView);
        takenBookingId = view.findViewById(R.id.takenBookingId);
        numGuestsView = view.findViewById(R.id.numGuestsView);
        messageView = view.findViewById(R.id.messageView);
        homeButton = view.findViewById(R.id.homeButton);
        manageBookings = view.findViewById(R.id.manageBookings);


        // Getting live values...

        BilletSharedData bsd = new ViewModelProvider(requireActivity()).get(BilletSharedData.class);
        bsd.getStartDate().observe(getViewLifecycleOwner(), startDate -> checkinTv.setText(startDate));
        bsd.getEndDate().observe(getViewLifecycleOwner(), endDate -> checkoutTv.setText(endDate));
        bsd.getPaymentUsed().observe(getViewLifecycleOwner(), paymentUsed -> paymentMView.setText(paymentUsed));
        bsd.getNumGuests().observe(getViewLifecycleOwner(), numGuests -> numGuestsView.setText(numGuests));
        bsd.getTakenBID().observe(getViewLifecycleOwner(), takenBID -> takenBookingId.setText(takenBID));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        String checkInStr = bsd.getStartDate().getValue();
        String checkOutStr = bsd.getEndDate().getValue();
        String ppN = bsd.getPriceString().getValue();

        // Getting the TOTAL PRICE based on the users stay-length

        String overallPriceStr = null;
        try {
            Date checkIn = null;
            if (checkInStr != null) {
                checkIn = df.parse(checkInStr);
            }
            Date checkOut = null;
            if (checkOutStr != null) {
                checkOut = df.parse(checkOutStr);
            }

            long stayLength = 0;
            if (checkOut != null) {
                stayLength = TimeUnit.DAYS.convert(checkOut.getTime() - checkIn.getTime(), TimeUnit.MILLISECONDS);
            }
            double pricePerNight = 0;
            if (ppN != null) {
                pricePerNight = Double.parseDouble(ppN.replace("$", ""));
            }
            double overallPrice = stayLength * pricePerNight;
            overallPriceStr = String.format("$%.2f", overallPrice);


        } catch (ParseException exec) {
            exec.printStackTrace();
        }
        String guestName = bsd.getGuestName().getValue();
        String payment = bsd.getPaymentUsed().getValue();

        String message = "Dear " + guestName + "," + " your payment of " + "$" + overallPriceStr + " via " + payment + " was successful.\n\n";

        messageView.setText(message);
        priceCView.setText(overallPriceStr);

        homeButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_reservedFragment_to_navigation_home));

        manageBookings.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_reservedFragment_to_manageFragment));
        return view;
    }

    // Both methods for building the BookingID

    public static String generateBookingID() {
        StringBuilder bookingID = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 3; i++) {
            bookingID.append(stringSection(random));
            if (i < 2) {
                bookingID.append("-");
            }
        }

        return bookingID.toString();

    }

    private static String stringSection(SecureRandom random) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < len; i++) {
            String each = (i % 2 == 0) ? letters : numbers;
            int index = random.nextInt(each.length());
            str.append(each.charAt(index));
        }
        return str.toString();
    }
}