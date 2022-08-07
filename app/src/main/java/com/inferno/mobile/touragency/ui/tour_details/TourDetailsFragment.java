package com.inferno.mobile.touragency.ui.tour_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.adapters.TimeLineRVAdapter;
import com.inferno.mobile.touragency.databinding.TourDetailsFragmentBinding;
import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.DaySchedule;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.models.TourDetails;
import com.inferno.mobile.touragency.utils.Token;

import java.util.ArrayList;

public class TourDetailsFragment extends Fragment {
    private TourDetailsFragmentBinding binding;
    private TourDetailsViewModel viewModel;
    private NavController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TourDetailsFragmentBinding.inflate(inflater, container, false);
        final int tourId = requireArguments().getInt("tour_id");
        viewModel = new ViewModelProvider(requireActivity())
                .get(TourDetailsViewModel.class);
        final String token = Token.GET_TOKEN(requireActivity());
        controller = NavHostFragment.findNavController(this);
        viewModel.init();
        viewModel.checkIfInCart(tourId).observe(requireActivity(),this::checkIfInCart);
        viewModel.getTourDetails(Token.GET_TOKEN(requireActivity()), tourId)
                .observe(requireActivity(), this::onTourDetails);
        binding.bookBtn.setOnClickListener(v -> {
            // added to cart
            viewModel.addToCart(token, tourId).observe(requireActivity(), this::onAddToCart);
        });
        return binding.getRoot();
    }

    private void checkIfInCart(CommonResponse<Boolean> response) {
        if (response.getData()) {
            binding.bookBtn.setText("DONE");
            binding.bookBtn.setEnabled(false);
            binding.bookBtn.setIcon(null);
        }
    }

    private void onAddToCart(CommonResponse<Integer> response) {
        Toast.makeText(requireContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
        if (response.getCode() == 200) {
            binding.bookBtn.setText("DONE");
            binding.bookBtn.setEnabled(false);
            binding.bookBtn.setIcon(null);
        }
    }

    private void onBookingDone(CommonResponse<BookingModel> response) {
        Toast.makeText(requireContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
        if (response.getCode() == 200) {
            controller.navigateUp();
        }

    }

    private void onTourDetails(CommonResponse<TourDetails> response) {
        ArrayList<ArrayList<DaySchedule>> schedules = viewModel.reshapeData(response.getData());
        TimeLineRVAdapter adapter = new TimeLineRVAdapter(schedules);
        String title = response.getData().getAgency().getName()
                + " #" + response.getData().getCity().getName();
        binding.title.setText(title);
        adapter.setOnAdapterClickItemListener((id, pos) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("place_id", id);
            controller.navigate(R.id.action_tourDetailsFragment_to_placeFragment, bundle);
        });
        binding.rvTimeLine.setAdapter(adapter);
    }
}
