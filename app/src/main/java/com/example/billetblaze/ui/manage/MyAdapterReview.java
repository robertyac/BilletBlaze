package com.example.billetblaze.ui.manage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.R;

import java.util.List;
public class MyAdapterReview extends RecyclerView.Adapter<MyViewHolderReview>{

    Context context;
    List<Review> items;

    public MyAdapterReview(Context context, List<Review> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolderReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderReview(LayoutInflater.from(context).inflate(R.layout.managereviews_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderReview holder, int position) {
        holder.nameView.setText(items.get(position).getHostName());
        holder.addressView.setText(items.get(position).getHostAddress());
        holder.dateView.setText(items.get(position).getCheckIn() + "-" + items.get(position).getCheckOut());
        holder.reviewView.setText(items.get(position).getReview());
        //holder.imageView.setImageResource(items.get(position).getImage());
        //holder.deleteReviewButton.setText("DELETE REVIEW");
        //holder.editReviewButton.setText("EDIT REVIEW");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
