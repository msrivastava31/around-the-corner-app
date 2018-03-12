package edu.uw.medhas.aroundthecorner.listener.impl;

import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;

/**
 * Created by medhas on 1/28/18.
 */

public class MapLocationListener extends LocationCallback implements OnSuccessListener<Location> {
    private final MapPresenter mMapPresenter;

    public MapLocationListener(MapPresenter mapPresenter) {
        super();
        mMapPresenter = mapPresenter;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        final Location location = locationResult.getLastLocation();
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMapPresenter.setCurrentLocation(latLng, false);
    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            final LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMapPresenter.setCurrentLocation(currentLatLng, true);
        }
    }
}
