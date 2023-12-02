package com.example.billetblaze.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billetblaze.BilletDetail;
import com.example.billetblaze.R;

import java.io.Serializable;
import java.util.List;

public class BilletDetailAdapter extends RecyclerView.Adapter<BilletDetailAdapter.BilletDetailViewHolder> {

    private List<BilletDetail> billetDetailList;

    public BilletDetailAdapter(List<BilletDetail> billetDetailList) {
        this.billetDetailList = billetDetailList;
    }

    @NonNull
    @Override
    public BilletDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billet_card, parent, false);
        return new BilletDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BilletDetailViewHolder holder, int position) {
        BilletDetail billetDetail = billetDetailList.get(position);
        holder.location.setText("Location: "+ billetDetail.getLocation());
        holder.startDate.setText("Start Date: "+billetDetail.getStartDate());
        holder.endDate.setText("End Date: "+ billetDetail.getEndDate());
        holder.maxGuests.setText("Max Guests: "+ String.valueOf(billetDetail.getMaxGuests()));
        holder.amenities.setText("Ammeneties: "+ billetDetail.getAmenities().toString());
        //holder.price.setText("Price: $"+ String.valueOf(billetDetail.getPrice()));
    }

    @Override
    public int getItemCount() {
        return billetDetailList.size();
    }

    public class BilletDetailViewHolder extends RecyclerView.ViewHolder {
        TextView location, startDate,endDate, maxGuests, amenities, price;

        public BilletDetailViewHolder(View view) {
            super(view);
            location = view.findViewById(R.id.location);
            startDate = view.findViewById(R.id.start_date);
            endDate = view.findViewById(R.id.end_date);
            maxGuests = view.findViewById(R.id.max_guests);
            amenities = view.findViewById(R.id.amenities);
            //price = view.findViewById(R.id.price);
            
            // Set an OnClickListener
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the clicked item
                    int position = getAdapterPosition();

                    // Check if the position is valid
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the clicked billet
                        BilletDetail clickedBillet = billetDetailList.get(position);

                        // Create a bundle to pass the clicked billet to the detail section
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("clicked_billet",clickedBillet);

                        // Use Navigation to navigate to the detail section
                        Navigation.findNavController(v).navigate(R.id.action_navigation_searchResults_to_navigation_BilletDetail,bundle);
                    }
                }
            });
        }
    }}