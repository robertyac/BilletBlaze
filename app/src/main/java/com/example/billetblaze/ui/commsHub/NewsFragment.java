package com.example.billetblaze.ui.commsHub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.example.billetblaze.R;
import androidx.appcompat.app.AppCompatActivity;


public class NewsFragment extends Fragment {

    private Button localNewsButton,localMapButton,externalMapButton, provincialButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        View webViewLayout = inflater.inflate(R.layout.webview, container, false);


        // back button back to comms hub
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         localNewsButton = view.findViewById(R.id.localNewsButton);
         externalMapButton = view.findViewById(R.id.externalMapButton);
        provincialButton = view.findViewById(R.id.provincialButton);

        localNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = (WebView) webViewLayout.findViewById(R.id.webview);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("https://www.cordemergency.ca");

                // Replace the current layout with the WebView layout
                ((ViewGroup) view).removeAllViews();
                ((ViewGroup) view).addView(webViewLayout);
            }
        });

        provincialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = (WebView) webViewLayout.findViewById(R.id.webview);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("https://blog.gov.bc.ca/bcwildfire/");

                // Replace the current layout with the WebView layout
                ((ViewGroup) view).removeAllViews();
                ((ViewGroup) view).addView(webViewLayout);
            }
        });

        externalMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.cordemergency.ca/map";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return view;
    }
}