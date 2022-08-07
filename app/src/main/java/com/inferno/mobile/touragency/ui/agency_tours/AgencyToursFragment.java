package com.inferno.mobile.touragency.ui.agency_tours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.adapters.TourRVAdapter;
import com.inferno.mobile.touragency.databinding.AgencyTourFragmentBinding;
import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.utils.Token;

import java.util.ArrayList;

public class AgencyToursFragment extends Fragment {
    private AgencyTourFragmentBinding binding;
    private NavController controller;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = AgencyTourFragmentBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(
                container.getRootView().findViewById(R.id.fragment_main)
        );
        AgencyTourViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(AgencyTourViewModel.class);
        viewModel.init();
        int agencyId = requireArguments().getInt("agencyId");
        String token = Token.GET_TOKEN(requireContext());
        viewModel.getAgencyTours(token, agencyId).observe(requireActivity(), this::onTours);
        return binding.getRoot();
    }

    private void onTours(CommonResponse<ArrayList<Tour>> response) {
        if (response.getCode() == 200) {
            TourRVAdapter adapter = new TourRVAdapter( response.getData());
            adapter.setOnAdapterClickItemListener((id, pos) -> {
                Bundle bundle = new Bundle();
                bundle.putInt("tour_id", id);
                controller.navigate(R.id.action_agencyToursFragment_to_tourDetailsFragment, bundle);
            });

            binding.agencyRv.setAdapter(adapter);
        } else {
            Toast.makeText(requireContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
