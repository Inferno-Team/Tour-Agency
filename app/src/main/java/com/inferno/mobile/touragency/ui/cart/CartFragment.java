package com.inferno.mobile.touragency.ui.cart;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.adapters.CartAdapter;
import com.inferno.mobile.touragency.databinding.CartFragmentBinding;
import com.inferno.mobile.touragency.models.ApprovedType;
import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.TourDetails;
import com.inferno.mobile.touragency.utils.Token;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private CartFragmentBinding binding;
    private CartViewModel viewModel;
    private NavController controller;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CartFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        viewModel.init();
        controller = Navigation.findNavController(
                container.getRootView().findViewById(R.id.fragment_main)
        );
        viewModel.getAllToursId(Token.GET_TOKEN(requireActivity()))
                .observe(requireActivity(), this::onResponse);
        binding.settingsOpener.setOnClickListener(v -> {
            controller.navigate(R.id.action_cartFragment_to_settingsFragment);
        });
        return binding.getRoot();
    }

    private void onResponse(CommonResponse<ArrayList<Pair<Integer, Boolean>>> response) {
        if (response.getCode() == 200) {
            ArrayList<Integer> ids = new ArrayList<>();

            ArrayList<Boolean> checked = new ArrayList<>();
            for (Pair<Integer, Boolean> pair : response.getData()) {
                ids.add(pair.first);
                checked.add(pair.second);
            }
            System.out.println("ids: " + ids);
            viewModel.getTours(Token.GET_TOKEN(requireActivity()), ids)
                    .observe(requireActivity(), (tours) -> onResponseTours(tours,
                            response.getData()));

        }
    }

    private void onApprovedResponse(CommonResponse<ArrayList<BookingModel>> response) {
        if (response.getCode() == 200) {
            ArrayList<BookingModel> approvedTypes = response.getData();
            if (adapter != null)
                adapter.setApprovedTypes(approvedTypes);
        }

    }

    private void onResponseTours(CommonResponse<ArrayList<TourDetails>> response,
                                 ArrayList<Pair<Integer, Boolean>> checked) {
        ArrayList<Integer> idsOfChecked = new ArrayList<>();

        ArrayList<Pair<TourDetails,Boolean>>pairs = new ArrayList<>();
        for (int i = 0; i < response.getData().size(); i++) {
            int id = response.getData().get(i).getId();
            boolean isChecked = getCheckById(checked, id);
            if (isChecked) {
                idsOfChecked.add(response.getData().get(i).getId());
            }
            pairs.add(new Pair<>(response.getData().get(i), isChecked));
        }
            if (response.getCode() == 200) {
                System.out.println("checked ids: " + idsOfChecked);
                adapter = new CartAdapter(pairs);

                adapter.setRemoveListener((id, position) -> new AlertDialog.Builder(requireActivity())
                        .setTitle("Remove tour")
                        .setMessage("Are you sure you want to remove this tour from cart?")
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Yes", (dialog, which) ->
                                viewModel.removeTour(Token.GET_TOKEN(requireActivity()), id)
                                        .observe(requireActivity(), isDeleted -> {
                                            if (isDeleted.getCode() == 200) {
                                                adapter.removeItem(position);
                                            }
                                        })).show());
                adapter.setCheckListener((id, position) -> {
                    new AlertDialog.Builder(requireActivity())
                            .setTitle("Checkout")
                            .setMessage("Are you sure you want to checkout?")
                            .setNegativeButton("No", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .setPositiveButton("Yes", (dialog, which) -> {
                                Bundle bundle = new Bundle();
                                bundle.putInt("tour_id", id);
                                controller.navigate(R.id.action_cartFragment_to_checkoutFragment, bundle);
                            }).show();
                });
                adapter.setCancelListener((id, pos) -> {
                    new AlertDialog.Builder(requireActivity())
                            .setTitle("Cancel tour")
                            .setMessage("Are you sure you want to cancel this tour?")
                            .setNegativeButton("No", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .setPositiveButton("Yes", (dialog, which) -> {
                                viewModel.cancelTour(Token.GET_TOKEN(requireActivity()), id)
                                        .observe(requireActivity(), isDeleted -> {
                                            if (isDeleted.getCode() == 200) {
                                                adapter.removeItem(pos);
                                            }
                                        });
                            }).show();
                });
                binding.cartItems.setAdapter(adapter);
                viewModel.getApprovedTypes(Token.GET_TOKEN(requireActivity()), idsOfChecked)
                        .observe(requireActivity(), this::onApprovedResponse);
            }

    }

    private boolean getCheckById(ArrayList<Pair<Integer, Boolean>> checked, int id) {
        for (Pair<Integer, Boolean> pair : checked) {
            if (pair.first == id) {
                return pair.second;
            }
        }
        return false;
    }
}
