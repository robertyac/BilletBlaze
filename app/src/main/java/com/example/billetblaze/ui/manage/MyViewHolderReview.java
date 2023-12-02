package com.example.billetblaze.ui.manage;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;
public class MyViewHolderReview extends RecyclerView.ViewHolder{


    ImageView imageView;
    TextView nameView, addressView, dateView, reviewView;
    Button deleteReviewButton, editReviewButton;

    public MyViewHolderReview(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.billetImageResults);
        nameView = itemView.findViewById(R.id.billetNameView);
        addressView = itemView.findViewById(R.id.billetAddressView);
        dateView = itemView.findViewById(R.id.billetDateView);
        reviewView = itemView.findViewById(R.id.reviewView);
        deleteReviewButton = itemView.findViewById(R.id.deleteReviewButton);
        editReviewButton = itemView.findViewById(R.id.editReviewButton);
    }
}
