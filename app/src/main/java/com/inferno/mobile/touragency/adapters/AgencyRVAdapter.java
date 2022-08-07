package com.inferno.mobile.touragency.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.TourApplication;
import com.inferno.mobile.touragency.databinding.AgencyItemBinding;
import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.services.API;

import java.util.ArrayList;

public class AgencyRVAdapter extends RecyclerView.Adapter<AgencyRVAdapter.AgencyHolder> {

    private  Context context;
    private final ArrayList<Agency> agencies;
    private AdapterClickItem onAdapterClickItemListener;

    public void setOnAdapterClickItemListener(AdapterClickItem onAdapterClickItemListener) {
        this.onAdapterClickItemListener = onAdapterClickItemListener;
    }

    public AgencyRVAdapter(ArrayList<Agency> agencies) {
        this.agencies = agencies;
    }

    @NonNull
    @Override
    public AgencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new AgencyHolder(AgencyItemBinding.inflate(
                LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyHolder holder, int position) {
        Agency agency = agencies.get(position);
        holder.binding.agencyName.setText(agency.getName());
        holder.binding.agencyLocation.setText(agency.getLocation());
        Glide.with(context)
                .load(API.BASE_IP + agency.getImgUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.binding.agencyLogo);
        holder.itemView.setOnClickListener(v->{
            if(onAdapterClickItemListener!=null)
                onAdapterClickItemListener.onClick(agency.getId(), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return agencies.size();
    }

    public static class AgencyHolder extends RecyclerView.ViewHolder {

        public AgencyItemBinding binding;

        public AgencyHolder(AgencyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
