package com.inferno.mobile.touragency;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.inferno.mobile.touragency.databinding.ActivityMainBinding;
import com.inferno.mobile.touragency.utils.Token;

public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController controller = Navigation.findNavController(this, R.id.fragment_main);
        if (!Token.IS_LOGGED_IN(this)) {
            controller.navigate(R.id.action_homeFragment_to_loginFragment);
            binding.bottomNavView.setVisibility(View.GONE);
        }
        NavigationUI.setupWithNavController(binding.bottomNavView, controller);
        binding.bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavigationUI.onNavDestinationSelected(item, controller,false);
                return true;
            }
        });
    }
}