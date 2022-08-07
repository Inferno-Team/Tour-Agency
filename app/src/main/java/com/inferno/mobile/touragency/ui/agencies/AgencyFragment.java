package com.inferno.mobile.touragency.ui.agencies;

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
import androidx.navigation.fragment.NavHostFragment;

import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.adapters.AgencyRVAdapter;
import com.inferno.mobile.touragency.databinding.AgencyFragmentBinding;
import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.utils.Token;

import java.util.ArrayList;

public class AgencyFragment extends Fragment {
    private AgencyFragmentBinding binding;
    private NavController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = AgencyFragmentBinding.inflate(inflater, container, false);
        controller = NavHostFragment.findNavController(this);
        AgencyViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(AgencyViewModel.class);
        viewModel.init();

        viewModel.getAgencies(Token.GET_TOKEN(requireActivity()))
                .observe(requireActivity(), this::onAgencies);
        return binding.getRoot();
    }

    private void onAgencies(CommonResponse<ArrayList<Agency>> response) {
        if (response.getCode() == 200) {
            AgencyRVAdapter adapter = new AgencyRVAdapter( response.getData());
            adapter.setOnAdapterClickItemListener((id, pos) -> {
                Bundle bundle = new Bundle();
                bundle.putInt("agencyId", id);
                controller.navigate(R.id.action_agencyFragment_to_agencyToursFragment, bundle);
            });
            binding.agencyRv.setAdapter(adapter);
        } else {
            Toast.makeText(requireContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
