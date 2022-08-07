package com.inferno.mobile.touragency.ui.signup;

import android.content.Intent;
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

import com.inferno.mobile.touragency.MainActivity;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.SignupFragmentBinding;
import com.inferno.mobile.touragency.models.SignupResponse;
import com.inferno.mobile.touragency.utils.Token;

public class SignupFragment extends Fragment {
    private SignupFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SignupFragmentBinding.inflate(inflater, container, false);
        SignupViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(SignupViewModel.class);
        viewModel.init();
        NavController controller = NavHostFragment.findNavController(this);
        binding.backButton.setOnClickListener(v -> controller.navigateUp());
        binding.signUpButton.setOnClickListener(v -> {
            String firstName = binding.firstName.getEditableText().toString();
            String lastName = binding.lastName.getEditableText().toString();
            String email = binding.email.getEditableText().toString();
            String password = binding.password.getEditableText().toString();
            String phone = binding.phone.getEditableText().toString();
            viewModel.signup(firstName, lastName, email, phone, password)
                    .observe(requireActivity(), this::onSignup);
        });
        return binding.getRoot();
    }

    private void onSignup(SignupResponse response) {
        Toast.makeText(requireContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
        if (response.getCode() == 200) {
            Token.LOG_IN(requireContext(), response.getToken());
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        }
    }
}