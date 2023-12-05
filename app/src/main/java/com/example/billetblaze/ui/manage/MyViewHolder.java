package com.example.billetblaze.ui.manage;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, addressView, dateView;
    Button exitButton;

    public MyViewHolder(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.billetImageResults);
        nameView = itemView.findViewById(R.id.billetNameView);
        addressView = itemView.findViewById(R.id.billetAddressView);
        dateView = itemView.findViewById(R.id.billetDateView);
        exitButton = itemView.findViewById(R.id.xButton);
    }

}
