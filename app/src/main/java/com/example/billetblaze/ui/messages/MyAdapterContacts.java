package com.example.billetblaze.ui.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

import java.util.List;

public class MyAdapterContacts extends RecyclerView.Adapter<MyViewHolderContacts>{

    Context context;
    List<Contact> items;
    private SelectListener listener;


    public MyAdapterContacts(Context context, List<Contact> items, SelectListener listener){
        this.context = context;
        this.items = items;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolderContacts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderContacts(LayoutInflater.from(context).inflate(R.layout.messagecontact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderContacts holder, int position) {


        holder.nameView.setText(items.get(position).getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
