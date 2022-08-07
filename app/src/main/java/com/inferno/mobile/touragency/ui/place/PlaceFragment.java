package com.inferno.mobile.touragency.ui.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.inferno.mobile.touragency.MainActivity;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.adapters.PlacePagerAdapter;
import com.inferno.mobile.touragency.databinding.PlaceItemBinding;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.Place;
import com.inferno.mobile.touragency.services.API;
import com.inferno.mobile.touragency.utils.Token;

public class PlaceFragment extends Fragment {
    private PlaceItemBinding binding;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).binding.bottomNavView.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) requireActivity()).binding.bottomNavView.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = PlaceItemBinding.inflate(inflater, container, false);
        int placeId = requireArguments().getInt("place_id");
        PlaceViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(PlaceViewModel.class);
        viewModel.init();
        viewModel.getPlaceById(Token.GET_TOKEN(requireContext()), placeId)
                .observe(requireActivity(), this::onPlace);

        return binding.getRoot();
    }

    private void onPlace(Place place) {
        Glide.with(requireContext())
                .load(API.BASE_IP + place.getImgUrl())
                .error(R.drawable.ic_baseline_error_outline_24)
                .placeholder(R.drawable.logo)
                .into(binding.imageContainer);
        binding.placeName.setText(place.getName());
        binding.locationName.setText(place.getAddress());


        PlacePagerAdapter adapter = new PlacePagerAdapter(getParentFragmentManager()
                , getLifecycle(), place);
        binding.viewPager.setAdapter(adapter);
        String[] titles = {"Desc", "Map"};
        new TabLayoutMediator(binding.tabContainer, binding.viewPager
                , (tab, position) -> tab.setText(titles[position])).attach();

    }
}
