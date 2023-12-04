package com.example.billetblaze.ui.messages;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;
public class MyViewHolderMessages extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView messageView;
    TextView nameView;


    public MyViewHolderMessages(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.billetImageResults);
        messageView = itemView.findViewById(R.id.messageTextView);
        nameView = itemView.findViewById(R.id.nameTextView);
    }

}
