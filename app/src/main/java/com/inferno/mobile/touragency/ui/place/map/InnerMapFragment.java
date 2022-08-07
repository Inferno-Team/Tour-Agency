package com.inferno.mobile.touragency.ui.place.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inferno.mobile.touragency.R;
import com.inferno.mobile.touragency.databinding.InnerMapFragmentBinding;
import com.inferno.mobile.touragency.models.Place;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.Annotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.viewannotation.ViewAnnotationManager;

public class InnerMapFragment extends Fragment {
    private InnerMapFragmentBinding binding;
    private final Place place;

    public InnerMapFragment(Place place) {
        this.place = place;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.inner_map_fragment,container,false);

        binding = InnerMapFragmentBinding.inflate(inflater, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.new_mapView);
        mapFragment.getMapAsync(googleMap -> {
            LatLng placePosition = new LatLng(
                    place.getLat().doubleValue(),
                    place.getLng().doubleValue()
            );

            CameraPosition position = new CameraPosition.Builder()
                    .target(placePosition)
                    .zoom(14f).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            googleMap.addMarker(new MarkerOptions()
                    .position(placePosition)
                    .title(place.getName()));

        });
//        return view;
        return binding.getRoot();
    }
}
