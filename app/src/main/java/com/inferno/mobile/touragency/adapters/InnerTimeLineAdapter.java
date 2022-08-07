package com.inferno.mobile.touragency.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.InnerTourDetailsBinding;
import com.inferno.mobile.touragency.models.DaySchedule;

import java.util.ArrayList;

public class InnerTimeLineAdapter extends RecyclerView.Adapter<InnerTimeLineAdapter.InnerViewHolder> {

    private final ArrayList<DaySchedule> schedules;
    private  Context context;
    private AdapterClickItem onAdapterClickItemListener;

    public void setOnAdapterClickItemListener(AdapterClickItem adapterClickItemListener) {
        this.onAdapterClickItemListener = adapterClickItemListener;
    }

    public InnerTimeLineAdapter(ArrayList<DaySchedule> schedules) {
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.inner_tour_details, parent, false);
        return new InnerViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerTimeLineAdapter.InnerViewHolder holder,
                                 int position) {
        DaySchedule day = schedules.get(position);
        holder.binding.dayName.setText(day.getPlace().getName());
        String timeStep = day.getTimeStep().getStart() + " - " + day.getTimeStep().getEnd();
        holder.binding.secondMame.setText(timeStep);
        holder.itemView.setOnClickListener(v -> {
            if (onAdapterClickItemListener != null)
                onAdapterClickItemListener.onClick(day.getId(), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        InnerTourDetailsBinding binding;

        public InnerViewHolder(@NonNull View itemView, int typeView) {
            super(itemView);
            binding = InnerTourDetailsBinding.bind(itemView);
            binding.timeline.initLine(typeView);
        }
    }
}
