package com.inferno.mobile.touragency.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.CartItemBinding;
import com.inferno.mobile.touragency.models.ApprovedType;
import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.TourDetails;
import com.inferno.mobile.touragency.services.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private final ArrayList<Pair<TourDetails, Boolean>> tours;
    private ArrayList<BookingModel> types;
    private AdapterClickItem removeListener, checkListener, cancelListener;

    public CartAdapter(ArrayList<Pair<TourDetails, Boolean>> tours) {

        this.tours = tours;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new CartViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cart_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Pair<TourDetails, Boolean> pair = tours.get(position);
        boolean isChecked = pair.second;
        TourDetails tour = pair.first;
        holder.binding.waitingCard.
                setBackgroundColor(context.getResources()
                        .getColor(R.color.purple_200, context.getTheme()));
        holder.binding.agencyName.setText(tour.getAgency().getName());
        holder.binding.tourPrice.setText(
                context.getString(R.string.tour_price, tour.getCost())
        );
        if (isChecked) {
            holder.binding.checkBtn.setImageResource(R.drawable.ic_baseline_clear_24);
            holder.binding.removeBtn.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.checkBtn.setImageResource(R.drawable.ic_baseline_check_24);
        }
        holder.binding.checkBtn.setOnClickListener(v -> {
            Pair<TourDetails, Boolean> pair1 = tours.get(holder.getAdapterPosition());
            if (!isChecked) {
                if (checkListener != null)
                    checkListener.onClick(pair1.first.getId(), holder.getAdapterPosition());
            } else {
                if (cancelListener != null)
                    cancelListener.onClick(tour.getId(), holder.getAdapterPosition());
            }
        });
        holder.binding.removeBtn.setOnClickListener(v -> {
            if (removeListener != null)
                removeListener.onClick(tour.getId(), holder.getAdapterPosition());
        });
        Glide.with(context)
                .load(API.BASE_IP + tour.getAgency().getImgUrl())
                .into(holder.binding.agencyLogo);
        if (types != null) {
            System.out.println("in try 2, position: " + position);
            for (BookingModel model : types)
                if (model.getTourId() == tour.getId()) {
                    System.out.println(model.getTourId() + " ,isChecked: " + isChecked + " position: " + position);
                    int colorId = R.color.purple_200;

                    switch (model.getApprove()) {
                        case yes:
                            colorId = R.color.green;
                            break;
                        case no:
                            colorId = R.color.red;
                            break;
                        case waiting:
                            colorId = R.color.yellow;
                            break;
                    }
                    if (model.getApprove() == ApprovedType.yes ||
                            model.getApprove() == ApprovedType.no) {
                        holder.binding.removeBtn.setVisibility(View.VISIBLE);
                        holder.binding.checkBtn.setVisibility(View.INVISIBLE);
                    }

                    holder.binding.waitingCard.
                            setBackgroundColor(context.getResources()
                                    .getColor(colorId, context.getTheme()));
                    if (model.getApprove() == ApprovedType.yes) {
                        holder.binding.tourSeatNumber.setVisibility(View.VISIBLE);
                        String seatNumber = "Your seat number is %d";
                        holder.binding.tourSeatNumber.setText(String.format(Locale.getDefault(),
                                seatNumber, model.getSeatNumber()));
                    }
                    break;
                }
        }
    }

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public void setRemoveListener(AdapterClickItem removeListener) {
        this.removeListener = removeListener;
    }

    public void setCheckListener(AdapterClickItem checkListener) {
        this.checkListener = checkListener;
    }

    public void removeItem(int position) {
        tours.remove(position);
        notifyItemRemoved(position);
    }

    public void setCancelListener(AdapterClickItem cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setApprovedTypes(ArrayList<BookingModel> types) {
        this.types = types;
        int[] positions = new int[types.size()];
        for (int i = 0; i < types.size(); i++) {
            for (int j = 0; j < tours.size(); j++) {
                if (types.get(i).getTourId() == tours.get(j).first.getId()) {
                    positions[i] = j;
                    break;
                }
            }
        }
        System.out.println("positions: " + Arrays.toString(positions));
//        notifyItemChanged(0,tours.size());
        notifyDataSetChanged();

    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        CartItemBinding binding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartItemBinding.bind(itemView);
        }
    }
}
