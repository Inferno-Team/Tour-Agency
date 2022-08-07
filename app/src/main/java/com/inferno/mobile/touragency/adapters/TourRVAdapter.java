package com.inferno.mobile.touragency.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.TourItemBinding;
import com.inferno.mobile.touragency.models.Tour;

import java.util.ArrayList;
import java.util.Calendar;

public class TourRVAdapter extends RecyclerView.Adapter<TourRVAdapter.TourHolder> {

    private  Context context;
    private final ArrayList<Tour> tours;
    private AdapterClickItem onAdapterClickItemListener;
    private long thisTime;

    public void setOnAdapterClickItemListener(AdapterClickItem onAdapterCLickItemListener) {
        this.onAdapterClickItemListener = onAdapterCLickItemListener;
    }

    public TourRVAdapter( ArrayList<Tour> tours) {
        this.tours = tours;
        this.thisTime = System.currentTimeMillis();
    }

    public void addNewTours(ArrayList<Tour> list) {
        int oldIndex = tours.size();
        this.tours.addAll(list);
        this.notifyItemRangeInserted(oldIndex, 15);
    }

    @NonNull
    @Override
    public TourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new TourHolder(TourItemBinding.
                inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TourHolder holder, int position) {
        Tour tour = tours.get(position);
        holder.binding.cityName.setText(tour.getCity().getName());
        holder.binding.cost.setText(String.valueOf(tour.getCost()));
        holder.binding.seatCount.setText(String.valueOf(tour.getSeatCount()));

        Calendar startTime = Calendar.getInstance();
        startTime.setTime(tour.getStartAt());
        String startTimeString = startTime.get(Calendar.YEAR)
                + " - " + (startTime.get(Calendar.MONTH) + 1)
                + " - " + startTime.get(Calendar.DAY_OF_MONTH);


        Calendar endTime = Calendar.getInstance();
        endTime.setTime(tour.getEndAt());
        String endTimeString = endTime.get(Calendar.YEAR)
                + " - " + (endTime.get(Calendar.MONTH) + 1)
                + " - " + endTime.get(Calendar.DAY_OF_MONTH);
        holder.binding.startTime.setText(startTimeString);
        holder.binding.endTime.setText(endTimeString);

        if (endTime.getTimeInMillis() < thisTime) {
            holder.binding.ribbon.setVisibility(View.VISIBLE);
//            holder.itemView.setEnabled(false);
            holder.binding.cardItem.setCardBackgroundColor(context.getColor(R.color.gray_200));
        } else {

            holder.binding.ribbon.setVisibility(View.GONE);
            holder.itemView.setEnabled(true);
            holder.binding.cardItem.setCardBackgroundColor(context.getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            if (endTime.getTimeInMillis() < thisTime)
                Toast.makeText(context, "this tour was ended.", Toast.LENGTH_SHORT).show();

            else if (onAdapterClickItemListener != null)
                onAdapterClickItemListener.onClick(tour.getId(), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public static class TourHolder extends RecyclerView.ViewHolder {
        final TourItemBinding binding;

        public TourHolder(TourItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
