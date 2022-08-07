package com.inferno.mobile.touragency.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.TourDetailsItemBinding;
import com.inferno.mobile.touragency.models.DaySchedule;

import java.util.ArrayList;

public class TimeLineRVAdapter extends RecyclerView.Adapter<TimeLineRVAdapter.TimeLineViewHolder> {
    private final ArrayList<ArrayList<DaySchedule>> schedules;
    private AdapterClickItem onAdapterClickItemListener;

    public void setOnAdapterClickItemListener(AdapterClickItem adapterClickItemListener) {
        this.onAdapterClickItemListener = adapterClickItemListener;
    }

    public TimeLineRVAdapter(ArrayList<ArrayList<DaySchedule>> schedules) {
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.tour_details_item, parent, false);
        return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        holder.binding.dayName.setText("Day " + schedules.get(position).get(0).getDay());
        InnerTimeLineAdapter adapter = new InnerTimeLineAdapter(schedules.get(position));
        adapter.setOnAdapterClickItemListener((id, pos) -> {
            if (onAdapterClickItemListener != null)
                onAdapterClickItemListener.onClick(id, pos);
        });
        holder.binding.innerRv.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }


    public static class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TourDetailsItemBinding binding;

        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TourDetailsItemBinding.bind(itemView);
            binding.timeline.initLine(0);
        }
    }
}
