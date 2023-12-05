package com.example.billetblaze.ui.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

import java.util.List;

public class MyAdapterMessages extends RecyclerView.Adapter<MyViewHolderMessages>{

    Context context;
    List<Message> items;

    public MyAdapterMessages(Context context, List<Message> items){
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public MyViewHolderMessages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderMessages(LayoutInflater.from(context).inflate(R.layout.message_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMessages holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.messageView.setText(items.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
