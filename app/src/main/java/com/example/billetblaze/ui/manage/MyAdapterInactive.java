package com.example.billetblaze.ui.manage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

import java.util.List;
public class MyAdapterInactive extends RecyclerView.Adapter<MyViewHolderInactive>{
    Context context;
    List<Billet> items;

    public MyAdapterInactive(Context context, List<Billet> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolderInactive onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderInactive(LayoutInflater.from(context).inflate(R.layout.managebookingsinactive_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderInactive holder, int position) {
        holder.nameView.setText(items.get(position).getHostName());
        holder.addressView.setText(items.get(position).getHostAddress());
        holder.dateView.setText(items.get(position).getCheckIn() + "-" + items.get(position).getCheckOut());
        //holder.imageView.setImageResource(items.get(position).getImage());
        holder.reviewButton.setText("ADD REVIEW");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
