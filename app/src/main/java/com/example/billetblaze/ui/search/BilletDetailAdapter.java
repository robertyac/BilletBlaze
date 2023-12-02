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
import java.util.ArrayList;
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
        holder.title.setText("Billet Option " + (position + 1));
        holder.location.setText("Location: "+ billetDetail.getLocation());
        holder.startDate.setText("Start Date: "+billetDetail.getStartDate());
        holder.endDate.setText("End Date: "+ billetDetail.getEndDate());
        holder.maxGuests.setText("Max Guests: "+ String.valueOf(billetDetail.getMaxGuests()));
        holder.price.setText("Price: $"+ String.valueOf(billetDetail.getPrice()));

        // Format the amenities list to remove brackets and trailing comma
        String amenitiesStr = billetDetail.getAmenities().toString();
        amenitiesStr = amenitiesStr.substring(1, amenitiesStr.length() - 1); // Remove brackets
        amenitiesStr = amenitiesStr.trim(); // Remove trailing spaces
        if (amenitiesStr.endsWith(",")) {
            amenitiesStr = amenitiesStr.substring(0, amenitiesStr.length() - 1); // Remove trailing comma
        }
        holder.amenities.setText("Ammeneties: " + amenitiesStr);

    }

    @Override
    public int getItemCount() {
        return billetDetailList.size();
    }

    public class BilletDetailViewHolder extends RecyclerView.ViewHolder {
        TextView location, startDate,endDate, maxGuests, amenities, price,title;

        public BilletDetailViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            location = view.findViewById(R.id.location);
            startDate = view.findViewById(R.id.start_date);
            endDate = view.findViewById(R.id.end_date);
            maxGuests = view.findViewById(R.id.max_guests);
            amenities = view.findViewById(R.id.amenities);
            price = view.findViewById(R.id.price);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        BilletDetail clickedBillet = billetDetailList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("clicked_billet", clickedBillet);
                        bundle.putString("city", clickedBillet.getLocation());
                        bundle.putString("startDate", clickedBillet.getStartDate());
                        bundle.putString("endDate", clickedBillet.getEndDate());
                        bundle.putInt("numGuests", clickedBillet.getMaxGuests());
                        bundle.putDouble("price", clickedBillet.getPrice());
                        bundle.putStringArrayList("amenities", new ArrayList<>(clickedBillet.getAmenities()));
                        Navigation.findNavController(v).navigate(R.id.action_navigation_searchResults_to_navigation_BilletDetail, bundle);
                    }
                }
            });
        }
    }}