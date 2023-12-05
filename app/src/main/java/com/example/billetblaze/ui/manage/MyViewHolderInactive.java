package com.example.billetblaze.ui.manage;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

public class MyViewHolderInactive extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView nameView, addressView, dateView;
    Button reviewButton;

    public MyViewHolderInactive(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.billetImageResults);
        nameView = itemView.findViewById(R.id.billetNameView);
        addressView = itemView.findViewById(R.id.billetAddressView);
        dateView = itemView.findViewById(R.id.billetDateView);
        reviewButton = itemView.findViewById(R.id.reviewButton);
    }


}
