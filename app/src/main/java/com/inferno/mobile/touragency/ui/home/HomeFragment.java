package com.inferno.mobile.touragency.ui.home;

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
import com.inferno.mobile.touragency.adapters.LoadMore;
import com.inferno.mobile.touragency.adapters.PaginationListener;
import com.inferno.mobile.touragency.adapters.TourRVAdapter;
import com.inferno.mobile.touragency.databinding.HomeFragmentBinding;
import com.inferno.mobile.touragency.models.Agency;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.Tour;
import com.inferno.mobile.touragency.models.TourPaginate;
import com.inferno.mobile.touragency.utils.Token;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private NavController controller;
    private PaginationListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = HomeFragmentBinding.inflate(inflater, container, false);
        controller = NavHostFragment.findNavController(this);
        HomeViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(HomeViewModel.class);
        viewModel.init();

        final String token = Token.GET_TOKEN(requireContext());
        viewModel.getTours(token, 1)
                .observe(requireActivity(), this::onTours);
        listener = new PaginationListener();
        listener.setCurrentPage(1);
        listener.setLoading(true);
        listener.setLastPage(false);
        listener.setLoadMore(currentPage -> {
            System.out.println("load more... #" + currentPage);
            listener.setLoading(true);
            viewModel.getTours(token, currentPage + 1).observe(requireActivity(), this::onTours);
        });
        binding.agencyRv.addOnScrollListener(listener);
        return binding.getRoot();
    }


    private void onTours(CommonResponse<TourPaginate> response) {
        if (response.getCode() == 200) {
            TourRVAdapter adapter;
            if (binding.agencyRv.getAdapter() == null) {
                adapter = new TourRVAdapter(response.getData().getTours());
                binding.agencyRv.setAdapter(adapter);
                adapter.setOnAdapterClickItemListener((id, pos) -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("tour_id", id);
                    controller.navigate(R.id.action_homeFragment_to_tourDetailsFragment, bundle);
                });
            } else {
                adapter = (TourRVAdapter) binding.agencyRv.getAdapter();
                adapter.addNewTours(response.getData().getTours());
            }
            listener.setLoading(false);
            listener.setLastPage(response.getData().getNextPage() == null);
            listener.setCurrentPage(response.getData().getCurrentPage());


        } else {
            Toast.makeText(requireContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
