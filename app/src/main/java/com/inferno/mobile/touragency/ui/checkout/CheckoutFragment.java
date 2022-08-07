package com.inferno.mobile.touragency.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.CheckoutFragmentBinding;
import com.inferno.mobile.touragency.models.BookingModel;
import com.inferno.mobile.touragency.models.CommonResponse;
import com.inferno.mobile.touragency.models.PaymentMethodType;
import com.inferno.mobile.touragency.utils.Token;

import java.util.ArrayList;

public class CheckoutFragment extends Fragment {
    private CheckoutFragmentBinding binding;
    private CheckoutViewModel viewModel;
    private NavController controller;
    private PaymentMethodType methodType = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CheckoutFragmentBinding.inflate(inflater, container, false);
        final int tourId = requireArguments().getInt("tour_id");
        viewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);
        viewModel.init();
        controller = Navigation.findNavController(
                container.getRootView().findViewById(R.id.fragment_main)
        );
        ArrayList<String> paymentMethods = new ArrayList<>();
        paymentMethods.add(PaymentMethodType.mobile_cash.name());
        paymentMethods.add(PaymentMethodType.transaction.name());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext()
                , R.layout.list_item, paymentMethods);
        binding.paymentMethod.setAdapter(adapter);
        binding.paymentMethod.setOnItemClickListener((parent, view, position, id) ->
                methodType = PaymentMethodType.valueOf(paymentMethods.get(position))
        );
        binding.checkoutButton.setOnClickListener(v -> {
            if (methodType == null) {
                Toast.makeText(requireContext(),
                        "Please select payment method", Toast.LENGTH_SHORT).show();
                return;
            }
            String paymentCode = binding.paymentCode.getEditableText().toString();
            String token = Token.GET_TOKEN(requireActivity());

            viewModel.checkout(token, tourId,
                            methodType, paymentCode)
                    .observe(requireActivity(), this::onResponse);
        });
        return binding.getRoot();
    }

    private void onResponse(CommonResponse<BookingModel> response) {
        if (response.getCode() == 200) {
            viewModel.checkoutFromLocalDatabase(Token.GET_TOKEN(requireActivity()), response.getData().getTourId())
                    .observe(requireActivity(), this::onCheckoutResponse);
        }
        Toast.makeText(requireContext(),
                response.getMsg(), Toast.LENGTH_SHORT).show();
    }

    private void onCheckoutResponse(CommonResponse<Boolean> response) {
        if (response.getCode() == 200) {
            controller.navigateUp();
        }
    }
}
