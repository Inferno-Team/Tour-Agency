package com.inferno.mobile.touragency.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inferno.mobile.touragency.MainActivity;
import com.inferno.mobile.touragency.databinding.SettingsFragmentBinding;
import com.inferno.mobile.touragency.utils.Token;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(inflater, container, false);
        binding.logout.setOnClickListener(v -> {
            Token.LOG_OUT(requireContext());
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            requireActivity().finish();
            requireActivity().startActivity(intent);
        });
        return binding.getRoot();
    }
}
