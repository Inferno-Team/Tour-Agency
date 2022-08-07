package com.inferno.mobile.touragency.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.inferno.mobile.touragency.MainActivity;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.LoginFragmentBinding;
import com.inferno.mobile.touragency.models.LoginResponse;
import com.inferno.mobile.touragency.utils.Token;

public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        LoginViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(LoginViewModel.class);
        viewModel.init();
        NavController controller = NavHostFragment.findNavController(this);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finishAffinity();
                    }
                });

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.email.getEditableText().toString();
            String password = binding.password.getEditableText().toString();
            binding.loginProgressbar.setVisibility(View.VISIBLE);
            viewModel.login(email, password)
                    .observe(requireActivity(), this::onLogin);

        });

        binding.signUpButton.setOnClickListener(v -> {
            controller.navigate(R.id.action_loginFragment_to_signupFragment);
        });
        return binding.getRoot();
    }

    private void onLogin(LoginResponse response) {
        binding.loginProgressbar.setVisibility(View.GONE);

        if (response.getCode() == 200) {
            if (!response.getType().equals("user")) {
                Toast.makeText(requireContext(),
                        "you logged in as manager please use a user account and try again.",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Token.LOG_IN(requireContext(), response.getToken());
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        }
        Toast.makeText(requireContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
