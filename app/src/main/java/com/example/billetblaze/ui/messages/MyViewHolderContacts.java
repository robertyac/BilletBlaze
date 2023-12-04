package com.example.billetblaze.ui.messages;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

public class MyViewHolderContacts extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView nameView;
    Button deleteButton;
    public CardView cardView;

    public MyViewHolderContacts(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.billetImageResults);
        nameView = itemView.findViewById(R.id.billetNameView);
        cardView = itemView.findViewById(R.id.contact_container);
    }



}
