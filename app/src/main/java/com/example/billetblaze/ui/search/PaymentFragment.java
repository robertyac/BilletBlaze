package com.example.billetblaze.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.billetblaze.R;
import com.google.android.material.textfield.TextInputEditText;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PayPalService;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentFragment extends Fragment {

    private static final int PAYPAL_REQUEST_CODE = 123;
    private static final String TAG = "PaymentFragment";
    private Spinner spin;
    private PayPalConfiguration config;
    private TextInputEditText cardNameInput, cardInput, cryptoInput, dateInput, cvvInput;
    private Button confirmButton;
    private RadioButton creditRadio, cryptoRadio;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        BilletSharedData bsd = new ViewModelProvider(requireActivity()).get(BilletSharedData.class);

        cardNameInput = view.findViewById(R.id.cardNameInput);
        cardInput = view.findViewById(R.id.cardInput);
        cryptoInput = view.findViewById(R.id.cryptoInput);
        dateInput = view.findViewById(R.id.dateInput);
        cvvInput = view.findViewById(R.id.cvvInput);
        confirmButton = view.findViewById(R.id.confirmButton);
        creditRadio = view.findViewById(R.id.creditRadio);
        cryptoRadio = view.findViewById(R.id.cryptoRadio);

        // Setup for PayPal API
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId("AUer8EUljB9yzFXqFcwjO2Ghll3U6yQsQUtmIMdtpgs_EqhYY7ZgWPEXDLFzDv-nWFnKKPU-YcH4Wv61");

        Button payPalButton = view.findViewById(R.id.payPalButton);
        payPalButton.setOnClickListener(v -> makePayPalPayment());

        spin = view.findViewById(R.id.cryptoSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.cryptos,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);


        // SECTION: Credit Card Selected
        creditRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bsd.setPaymentUsed(creditRadio.getText().toString());
                if (Objects.requireNonNull(cvvInput.getText()).length() != 3) {
                    cvvInput.setError("CVV must be 3 digits");
                } else {
                    cvvInput.setError(null);
                }

                String cardNumber = Objects.requireNonNull(cardInput.getText()).toString().replaceAll("\\s", "");
                if (cardNumber.length() != 16) {
                    cardInput.setError(getString(R.string.card_number_must_be_16_digits));
                } else {
                    cardInput.setError(null);
                }

                String date = Objects.requireNonNull(dateInput.getText()).toString();
                if (!isValidDate(date)) {
                    dateInput.setError("Invalid date format (MM/YYYY)");
                } else {
                    dateInput.setError(null);
                }

                String cardName = Objects.requireNonNull(cardNameInput.getText()).toString();
                if (cardName.trim().isEmpty()) {
                    cardNameInput.setError("Card name cannot be empty");
                } else {
                    cardNameInput.setError(null);
                }
            } else {
                // If the user selects another payment method, we just ignore the values
                cvvInput.setError(null);
                cardInput.setError(null);
                dateInput.setError(null);
                cardNameInput.setError(null);
            }
        });

        // SECTION: Crypto Selected
        cryptoRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bsd.setPaymentUsed(cryptoRadio.getText().toString());
                cryptoInput.setError(null);
                validateCryptoInput();
            } else {
                cryptoInput.setError(null);
            }
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (cryptoRadio.isChecked()) {
                    validateCryptoInput();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle nothing selected if needed
            }
        });

        confirmButton.setOnClickListener(v -> {
            boolean error = false;

            if (creditRadio.isChecked()) {
                if (Objects.requireNonNull(cvvInput.getText()).length() != 3) {
                    cvvInput.setError("CVV must be 3 digits");
                    error = true;
                }

                String cardNumber = Objects.requireNonNull(cardInput.getText()).toString().replaceAll("\\s", "");
                if (cardNumber.length() != 16) {
                    cardInput.setError(getString(R.string.card_number_must_be_16_digits));
                    Toast.makeText(requireContext(), "Enter the credit card number in the format NNNN NNNN NNNN NNNN", Toast.LENGTH_SHORT).show();
                    error = true;
                }

                String date = Objects.requireNonNull(dateInput.getText()).toString();
                if (!isValidDate(date)) {
                    dateInput.setError("Invalid date format (MM/YYYY)");
                    Toast.makeText(requireContext(), "Enter the date in the format MM/YYYY", Toast.LENGTH_SHORT).show();
                    error = true;
                }

                String cardName = Objects.requireNonNull(cardNameInput.getText()).toString();
                if (cardName.trim().isEmpty()) {
                    cardNameInput.setError("Card name cannot be empty");
                    error = true;
                }
            } else if (cryptoRadio.isChecked()) {
                String selectedCrypto = spin.getSelectedItem().toString();
                String cryptoAddress = Objects.requireNonNull(cryptoInput.getText()).toString();

                if ((selectedCrypto.equals("ETH") && !isValidEthAddress(cryptoAddress)) ||
                        (selectedCrypto.equals("BTC") && !isValidBtcAddress(cryptoAddress))) {
                    cryptoInput.setError("Invalid crypto address");
                    error = true;
                }
            }

            if (!error) {
                Navigation.findNavController(v).navigate(R.id.action_paymentFragment_to_confirmationFragment);
            }
        });

        return view;
    }

    // Checking for a valid address ETH/BTC
    private void validateCryptoInput() {
        String selectedCrypto = spin.getSelectedItem().toString();
        String cryptoAddress = Objects.requireNonNull(cryptoInput.getText()).toString();

        if (selectedCrypto.equals("ETH") && !isValidEthAddress(cryptoAddress)) {
            cryptoInput.setError("Invalid ETH address");
        } else if (selectedCrypto.equals("BTC") && !isValidBtcAddress(cryptoAddress)) {
            cryptoInput.setError("Invalid BTC address");
        } else {
            cryptoInput.setError(null);
        }
    }

    // Paypal API Implementations
    private void makePayPalPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("500.00"), "USD", "Billet Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    // Unfortunately this PayPal API is currently deprecated and not updated as of recently. This is
    // why the payments are unable to be confirmed. Payments will not be confirmed if they are filtered through the
    // PayPal Sandbox rather than ACTUAL physical payments.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
        } else if (resultCode == getActivity().RESULT_CANCELED) {
            Log.d(TAG, "Payment was canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.e(TAG, "Payment was invalid.");
        }
    }

    // Checking the date
    private boolean isValidDate(String date) {
        return date.matches("\\d{2}/\\d{4}");
    }

    // Checking valid ETH address
    private boolean isValidEthAddress(String address) {
        return address.startsWith("0x") && address.length() == 42;
    }

    // Checking valid BTC address
    private boolean isValidBtcAddress(String address) {
        return address.length() == 34;
    }

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

}
