package com.inferno.mobile.touragency.ui.place.description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inferno.mobile.touragency.databinding.DescFragmentBinding;
import com.inferno.mobile.touragency.models.Place;

public class DescFragment extends Fragment {
    private DescFragmentBinding binding;
    private final Place place;

    public DescFragment(Place place) {
        this.place = place;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DescFragmentBinding.inflate(inflater, container, false);
        binding.textView.setText(place.getDisc());
        return binding.getRoot();
    }
}
