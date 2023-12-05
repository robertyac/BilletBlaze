package com.example.billetblaze.ui.manage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{


    Context context;
    List<Billet> items;

    public MyAdapter(Context context, List<Billet> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.managebookings_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getHostName());
        holder.addressView.setText(items.get(position).getHostAddress());
        holder.dateView.setText(items.get(position).getCheckIn() + "-" + items.get(position).getCheckOut());
        //holder.imageView.setImageResource(items.get(position).getImage());
        holder.exitButton.setText("X");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
