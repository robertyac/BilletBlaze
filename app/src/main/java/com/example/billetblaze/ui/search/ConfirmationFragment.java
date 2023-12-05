package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;

import java.security.SecureRandom;

public class ConfirmationFragment extends Fragment {

    private TextView checkinTv, checkoutTv, priceCView, paymentMView, bookingIdValue, numGuestsView;
    private CheckBox confirmationBox;
    private Button reservationButton;
    private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYQ";
    private static final String numbers = "0123456789";
    private static final int len = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        String bookingId = generateBookingID();
        checkinTv = view.findViewById(R.id.checkinTv);
        checkoutTv = view.findViewById(R.id.checkoutTv);
        priceCView = view.findViewById(R.id.priceCView);
        paymentMView = view.findViewById(R.id.paymentMView);
        bookingIdValue = view.findViewById(R.id.bookingIdValue);
        numGuestsView = view.findViewById(R.id.numGuestsView);
        confirmationBox = view.findViewById(R.id.confirmationBox);
        reservationButton = view.findViewById(R.id.manageBookings);
        bookingIdValue.setText(bookingId);


        BilletSharedData bsd = new ViewModelProvider(requireActivity()).get(BilletSharedData.class);
        bsd.getStartDate().observe(getViewLifecycleOwner(), startDate -> checkinTv.setText(startDate));
        bsd.getEndDate().observe(getViewLifecycleOwner(), endDate -> checkoutTv.setText(endDate));
        bsd.getPriceString().observe(getViewLifecycleOwner(), priceString -> {
            String priceStr = "$" + priceString;
            priceCView.setText(priceStr);
        });
        bsd.getPaymentUsed().observe(getViewLifecycleOwner(), paymentUsed -> paymentMView.setText(paymentUsed));
        bsd.getNumGuests().observe(getViewLifecycleOwner(), numGuests -> numGuestsView.setText(numGuests));
        bsd.setTakenBID(bookingId);

        reservationButton.setOnClickListener(v -> {
            if (confirmationBox.isChecked()) {
                Navigation.findNavController(v).navigate(R.id.action_confirmationFragment_to_reservedFragment);
            } else {
                Toast.makeText(getContext(), "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
            }
        });

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

    private static String stringSection(SecureRandom random){
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < len; i++){
            String each = (i % 2 == 0 ) ? letters : numbers;
            int index = random.nextInt(each.length());
            str.append(each.charAt(index));
        }
        return str.toString();
    }

}
