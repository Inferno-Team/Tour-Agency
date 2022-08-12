package com.inferno.mobile.touragency.ui.place.map;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.InnerMapFragmentBinding;
import com.inferno.mobile.touragency.models.Place;
import com.inferno.mobile.touragency.utils.Token;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

public class InnerMapFragment extends Fragment {
    private InnerMapFragmentBinding binding;
    private final Place place;
    private MapboxMap mapboxMap;

    public InnerMapFragment(Place place) {
        this.place = place;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Mapbox.getInstance(requireActivity(), Token.MAP_TOKEN);
        binding = InnerMapFragmentBinding.inflate(inflater, container, false);

        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                InnerMapFragment.this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        addMarker();
                    }
                });
            }
        });
        return binding.getRoot();
    }

    private void addMarker() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(place.getLat().doubleValue(),
                        place.getLng().doubleValue()))      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(place.getLat().doubleValue(),
                place.getLng().doubleValue())).setTitle(place.getName()));
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}