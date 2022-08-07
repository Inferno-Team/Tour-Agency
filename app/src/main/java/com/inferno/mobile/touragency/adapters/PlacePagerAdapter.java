package com.inferno.mobile.touragency.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.inferno.mobile.touragency.models.Place;
import com.inferno.mobile.touragency.ui.place.description.DescFragment;
import com.inferno.mobile.touragency.ui.place.map.InnerMapFragment;

public class PlacePagerAdapter extends FragmentStateAdapter {
    private final Place place;
    public PlacePagerAdapter(@NonNull FragmentManager fragmentManager,
                             @NonNull Lifecycle lifecycle,
                             Place place) {
        super(fragmentManager, lifecycle);
        this.place = place;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new DescFragment(place);
        else return new InnerMapFragment(place);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
